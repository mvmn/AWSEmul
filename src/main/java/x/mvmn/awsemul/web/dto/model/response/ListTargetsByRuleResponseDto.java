package x.mvmn.awsemul.web.dto.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import x.mvmn.awsemul.web.dto.model.CWERuleTargetDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTargetsByRuleResponseDto {
	@JsonProperty("Targets")
	protected List<CWERuleTargetDto> targets;
}
