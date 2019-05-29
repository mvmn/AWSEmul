package x.mvmn.awsemul.web.dto.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CWERuleTargetDto {
	@JsonProperty("Id")
	@NotBlank
	protected String id;
	@JsonProperty("Arn")
	@NotBlank
	protected String arn;
	@JsonProperty("Input")
	protected String input;
}
