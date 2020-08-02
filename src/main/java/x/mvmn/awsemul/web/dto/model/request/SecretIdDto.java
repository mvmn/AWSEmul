package x.mvmn.awsemul.web.dto.model.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SecretIdDto {
	@JsonProperty("SecretId")
	@NotEmpty
	private String secretId;
}
