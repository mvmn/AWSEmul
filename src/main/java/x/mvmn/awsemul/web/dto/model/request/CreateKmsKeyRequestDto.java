package x.mvmn.awsemul.web.dto.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateKmsKeyRequestDto {
	@JsonProperty("Description")
	protected String description;
}
