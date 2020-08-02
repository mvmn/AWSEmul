package x.mvmn.awsemul.web.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecManDeleteSecretResponseDto {

	@JsonProperty("DeletionDate")
	protected Long deletionDate;

	@JsonProperty("ARN")
	protected String arn;

	@JsonProperty("Name")
	protected String name;
}
