package x.mvmn.awsemul.web.dto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KMSKeyListDto {

	@JsonProperty("Keys")
	protected List<KMSKeyListEntryDto> keys;

	@JsonProperty("KeyCount")
	protected Integer keyCount;

	@JsonProperty("Truncated")
	protected Boolean truncated;
}
