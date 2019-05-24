package x.mvmn.cweemul.web.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import x.mvmn.cweemul.persistence.model.CWERule;
import x.mvmn.cweemul.web.dto.model.CWERuleDto;

@Mapper(componentModel = "spring")
public interface CWERuleMapper {

	CWERuleDto toDto(CWERule entity);

	@Mapping(target = "id", ignore = true)
	CWERule fromDto(CWERuleDto entity);
}
