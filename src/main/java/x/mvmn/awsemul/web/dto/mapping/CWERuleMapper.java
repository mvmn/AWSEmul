package x.mvmn.awsemul.web.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import x.mvmn.awsemul.persistence.model.CWERule;
import x.mvmn.awsemul.persistence.model.CWERuleTarget;
import x.mvmn.awsemul.web.dto.model.CWERuleDto;
import x.mvmn.awsemul.web.dto.model.CWERuleTargetDto;

@Mapper(componentModel = "spring")
public interface CWERuleMapper {

	CWERuleDto toDto(CWERule entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "targets", ignore = true)
	CWERule fromDto(CWERuleDto entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "rule", ignore = true)
	@Mapping(target = "targetId", source = "id")
	@Mapping(target = "targetArn", source = "arn")
	CWERuleTarget fromDto(CWERuleTargetDto dto);

	@Mapping(source = "targetId", target = "id")
	@Mapping(source = "targetArn", target = "arn")
	CWERuleTargetDto toDto(CWERuleTarget dto);
}
