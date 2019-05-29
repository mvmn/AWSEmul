package x.mvmn.awsemul.web.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KMSKeyListEntryDto {
	@JsonProperty("KeyId")
	protected String keyId;
	@JsonProperty("KeyArn")
	protected String keyArn;
}
