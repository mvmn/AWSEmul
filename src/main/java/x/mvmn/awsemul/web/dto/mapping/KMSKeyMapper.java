package x.mvmn.awsemul.web.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import x.mvmn.awsemul.persistence.model.KMSKey;
import x.mvmn.awsemul.web.dto.model.KMSKeyListEntryDto;

@Mapper(componentModel = "spring")
public interface KMSKeyMapper {
	@Mapping(target = "keyArn", source = "arn")
	KMSKeyListEntryDto toListEntryDto(KMSKey key);
}
