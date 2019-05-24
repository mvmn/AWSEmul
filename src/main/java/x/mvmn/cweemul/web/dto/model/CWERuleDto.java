package x.mvmn.cweemul.web.dto.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CWERuleDto {
	@JsonProperty("Arn")
	protected String arn;
	@JsonProperty("Description")
	protected String description;
	@JsonProperty("Name")
	@NotBlank
	protected String name;
	@JsonProperty("ScheduleExpression")
	@NotBlank
	protected String scheduleExpression;
	@JsonProperty("State")
	protected String state;
}
