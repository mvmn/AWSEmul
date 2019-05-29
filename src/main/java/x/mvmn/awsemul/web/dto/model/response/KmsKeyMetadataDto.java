package x.mvmn.awsemul.web.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import x.mvmn.awsemul.web.dto.model.KMSKeyDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KmsKeyMetadataDto {
	@JsonProperty("KeyMetadata")
	protected KMSKeyDto keyMetadata;
}
