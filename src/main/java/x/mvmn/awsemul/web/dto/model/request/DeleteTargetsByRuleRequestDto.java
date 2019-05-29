package x.mvmn.awsemul.web.dto.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeleteTargetsByRuleRequestDto {
	@JsonProperty("Rule")
	@NotBlank
	protected String rule;
	@JsonProperty("Ids")
	@NotEmpty
	protected List<String> ids;
}
