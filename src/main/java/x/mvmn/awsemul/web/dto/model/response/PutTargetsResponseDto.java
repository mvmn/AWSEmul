package x.mvmn.awsemul.web.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PutTargetsResponseDto {

	@JsonProperty("FailedEntries")
	protected Object[] failedEntries = new Object[0];
	@JsonProperty("FailedEntryCount")
	protected long failedEntryCount;
}
