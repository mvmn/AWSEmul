package x.mvmn.awsemul.web.dto.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListRuleNamesByTargetRequestDto {
	@JsonProperty("TargetArn")
	@NotBlank
	protected String targetArn;
}
