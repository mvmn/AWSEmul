package x.mvmn.awsemul.web.dto.model.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UpdateSecManSecretRequestDto {
	@NotEmpty
	@JsonProperty("SecreId")
	private String secretId;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("SecretString")
	private String secretString;
	@JsonProperty("SecretBinary")
	private String secretBinary;
	@JsonProperty("KmsKeyId")
	private String kmsKeyId;
}
