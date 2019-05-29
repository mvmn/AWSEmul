package x.mvmn.awsemul.web.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleKmsKeyDeletionResponseDto {

	@JsonProperty("DeletionDate")
	protected Long deletionDate = System.currentTimeMillis() / 1000;

	@JsonProperty("KeyId")
	protected String keyId;

	public ScheduleKmsKeyDeletionResponseDto(String keyId) {
		this.keyId = keyId;
	}
}
