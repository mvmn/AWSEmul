package x.mvmn.cweemul.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import x.mvmn.cweemul.persistence.model.CWERule;

public interface CWERuleRepository extends JpaRepository<CWERule, Long> {

	List<CWERule> findByNameStartsWith(String trim);

	@Transactional
	@Modifying
	int deleteByName(String name);
}
