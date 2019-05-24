package x.mvmn.cweemul.web.dto.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import x.mvmn.cweemul.web.dto.model.CWERuleTargetDto;

@Data
public class PutRuleTargetsRequestDto {
	@JsonProperty("Rule")
	@NotBlank
	protected String rule;

	@JsonProperty("Targets")
	@NotEmpty
	protected List<CWERuleTargetDto> targets;
}
