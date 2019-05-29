package x.mvmn.awsemul.web.dto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CWERulesListDto {
	@JsonProperty("Rules")
	protected List<CWERuleDto> rules;
}
