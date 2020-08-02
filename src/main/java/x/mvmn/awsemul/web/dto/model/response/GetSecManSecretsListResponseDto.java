package x.mvmn.awsemul.web.dto.model.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSecManSecretsListResponseDto {

	@JsonProperty("SecretList")
	private List<SecretEntry> secretList;

	@Data
	@Builder
	public static class SecretEntry {
		@JsonProperty("Name")
		private String name;
		@JsonProperty("ARN")
		private String arn;
		@JsonProperty("Description")
		private String description;
		@JsonProperty("LastChangedDate")
		private Long lastChangedDate;
		@JsonProperty("SecretVersionsToStages")
		private Map<String, List<String>> secretVersionsToStages;
		@JsonProperty("KmsKeyId")
		private String kmskeyId;
	}
}
