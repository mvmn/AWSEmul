package x.mvmn.awsemul.web.controller;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import x.mvmn.awsemul.web.dto.model.response.SnsCreateTopicResponse;

@RestController
@RequestMapping(value = "/sns", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class SNSController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SNSController.class);

	@Autowired
	protected AmazonSQS sqs;

	@Autowired
	protected ObjectMapper objectMapper;

	protected static Map<String, Set<String>> SUBSCRIPTIONS = new HashMap<>();

	@RequestMapping("*")
	public @ResponseBody Object ok(HttpServletRequest req, HttpServletResponse resp, @RequestParam Map<String, String> body) {
		Object result = null;
		String action = body.get("Action");
		LOGGER.info("SNS action " + action + ": " + body);

		try {
			Method mehod = this.getClass().getDeclaredMethod("snsDo" + action, Map.class);
			System.out.println(req.getRequestURL().toString() + "?" + req.getQueryString() + "# "
					+ Collections.list(req.getHeaderNames()).stream()
							.map(hn -> hn + "=" + Collections.list(req.getHeaders(hn)).stream().collect(Collectors.joining("; ")))
							.collect(Collectors.joining(", ")));
			result = mehod.invoke(this, body);
		} catch (NoSuchMethodException nsme) {
			resp.setStatus(404);
		} catch (Exception e) {
			LOGGER.error("Failed to handle request " + body, e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public SnsCreateTopicResponse snsDoCreateTopic(Map<String, String> body) {
		SnsCreateTopicResponse response = new SnsCreateTopicResponse();
		response.getResult().setValue("arn:aws:sns:us-east-1:000000000000:" + body.get("Name"));

		return response;
	}

	public Object snsDoSubscribe(Map<String, String> body) {
		System.out.println(body);
		String topicArn = body.get("TopicArn");
		String target = body.get("Endpoint");
		if (target != null && target.startsWith("arn:aws:sqs:elasticmq")) {
			Set<String> subsForTopic;
			synchronized (SUBSCRIPTIONS) {
				subsForTopic = SUBSCRIPTIONS.get(topicArn);
				if (subsForTopic == null) {
					subsForTopic = Collections.synchronizedSet(new HashSet<>());
					SUBSCRIPTIONS.put(topicArn, subsForTopic);
				}
			}
			subsForTopic.add(target);
		}
		return Collections.emptyMap();
	}

	public Object snsDoPublish(Map<String, String> body) throws JsonProcessingException {
		System.out.println(body);
		String topicArn = body.get("TopicArn");
		String message = body.get("Message");
		Map<String, Object> payload = new HashMap<>();
		payload.put("Message", message);
		payload.put("MessageId", UUID.randomUUID().toString());
		payload.put("Type", "Notification");
		payload.put("TopicArn", topicArn);
		Set<String> subsForTopic = SUBSCRIPTIONS.get(topicArn);
		if (subsForTopic != null) {
			for (String target : new HashSet<>(subsForTopic)) {
				sqs.sendMessage(sqs.getQueueUrl(target.substring(target.lastIndexOf(":") + 1)).getQueueUrl(),
						objectMapper.writeValueAsString(payload));
			}
		}
		return Collections.emptyMap();
	}
}
