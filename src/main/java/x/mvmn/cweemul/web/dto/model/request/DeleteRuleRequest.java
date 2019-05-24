package x.mvmn.cweemul.web.dto.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeleteRuleRequest {
	@JsonProperty("Name")
	protected String name;
}
