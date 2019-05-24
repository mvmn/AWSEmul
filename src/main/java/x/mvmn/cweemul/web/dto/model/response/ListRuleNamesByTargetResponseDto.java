package x.mvmn.cweemul.web.dto.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListRuleNamesByTargetResponseDto {
	@JsonProperty("RuleNames")
	protected List<String> ruleNames;
}
