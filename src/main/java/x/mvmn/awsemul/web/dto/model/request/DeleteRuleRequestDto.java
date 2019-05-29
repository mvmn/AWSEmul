package x.mvmn.awsemul.web.dto.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeleteRuleRequestDto {
	@JsonProperty("Name")
	@NotBlank
	protected String name;
}
