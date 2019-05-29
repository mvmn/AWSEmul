package x.mvmn.awsemul.web.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import x.mvmn.awsemul.persistence.model.KMSKey;

@Data
@NoArgsConstructor
public class KMSKeyDto {
	@JsonProperty("Arn")
	protected String arn;
	@JsonProperty("KeyId")
	protected String keyId;
	@JsonProperty("Description")
	protected String description;
	@JsonProperty("AWSAccountId")
	protected String awsAccountId = "0";
	@JsonProperty("CreationDate")
	protected Long creationDate = System.currentTimeMillis() / 1000;
	@JsonProperty("Enabled")
	protected Boolean enabled = true;
	@JsonProperty("KeyManager")
	protected String keyManager = "CUSTOMER";
	@JsonProperty("KeySpec")
	protected String keySpec = "SYMMETRIC_DEFAULT";
	@JsonProperty("KeyState")
	protected String keyState = "Enabled";
	@JsonProperty("KeyUsage")
	protected String keyUsage = "ENCRYPT_DECRYPT";
	@JsonProperty("Origin")
	protected String origin = "AWS_KMS";

	public KMSKeyDto(KMSKey key) {
		this.arn = key.getArn();
		this.keyId = key.getKeyId();
		this.description = key.getDescription();
	}
}
