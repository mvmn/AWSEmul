package x.mvmn.awsemul.web.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SecManSecretShortDto {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("ARN")
	private String arn;
	@JsonProperty("VersionId")
	private String versionId;
}
