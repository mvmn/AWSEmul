package x.mvmn.cweemul.web.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CWERuleDto {
	@JsonProperty("Arn")
	protected String arn;
	@JsonProperty("Description")
	protected String description;
	@JsonProperty("Name")
	protected String name;
	@JsonProperty("ScheduleExpression")
	protected String scheduleExpression;
	@JsonProperty("State")
	protected String state;
}
