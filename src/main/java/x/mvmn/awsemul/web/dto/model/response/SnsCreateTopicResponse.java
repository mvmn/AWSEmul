package x.mvmn.awsemul.web.dto.model.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "CreateTopicResponse")
public class SnsCreateTopicResponse {
	@JacksonXmlProperty(isAttribute = true)
	protected final String xmlns = "https://sns.amazonaws.com/doc/2010-03-31/";
	@JacksonXmlProperty(localName = "CreateTopicResult")
	protected SnsCreateTopicResponse.CreateTopicResult result = new CreateTopicResult();

	@Data
	public static class CreateTopicResult {
		@JacksonXmlProperty(localName = "TopicArn")
		protected String value;
	}
}