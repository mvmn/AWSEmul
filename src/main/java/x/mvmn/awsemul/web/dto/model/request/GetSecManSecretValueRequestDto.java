package x.mvmn.awsemul.web.dto.model.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetSecManSecretValueRequestDto {
	@JsonProperty("SecretId")
	@NotEmpty
	private String secretId;
}
