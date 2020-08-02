package x.mvmn.awsemul.web.dto.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SecManSecretExtDto extends SecManSecretShortDto {
	@JsonProperty("CreatedDate")
	private Long createdDate;
	@JsonProperty("VersionStages")
	private List<String> versionStages;
	@JsonProperty("SecretString")
	private String secretString;
	@JsonProperty("SecretBinary")
	private String secretBinary;
	@JsonProperty("KmsKeyId")
	private String kmskeyId;
}
