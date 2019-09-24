package x.mvmn.awsemul.web.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

import x.mvmn.awsemul.persistence.repo.KMSKeyRepository;
import x.mvmn.awsemul.web.dto.mapping.KMSKeyMapper;

@RestController
@RequestMapping(value = "/sns", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class SNSController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SNSController.class);

	@Autowired
	KMSKeyRepository kmsKeyRepository;

	@Autowired
	KMSKeyMapper kmsKeyMapper;

	@RequestMapping("*")
	public @ResponseBody Map<String, String> ok(HttpServletRequest req, HttpServletResponse resp, @RequestParam Map<String, String> body) {
		Map<String, String> result = new HashMap<String, String>();
		String action = body.get("Action");
		LOGGER.info("SNS action " + action);

		try {
			Method mehod = this.getClass().getDeclaredMethod("snsDo" + action, Map.class, Map.class);
			mehod.invoke(this, body, result);
		} catch (NoSuchMethodException nsme) {
			resp.setStatus(404);
		} catch (Exception e) {
			LOGGER.error("Failed to handle request " + body, e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public void snsDoCreateTopic(Map<String, String> body, Map<String, String> result) {
		result.put("TopicArn", "arn:aws:sns:us-east-1:000000000000:" + body.get("Name"));
	}

	public void snsDoSubscribe(Map<String, String> body, Map<String, String> result) {}
}
