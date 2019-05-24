package x.mvmn.cweemul.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import x.mvmn.cweemul.persistence.model.CWERule;
import x.mvmn.cweemul.persistence.model.CWERuleTarget;

public interface CWERuleTargetRepository extends JpaRepository<CWERuleTarget, Long> {

	@Query("SELECT rt FROM CWERuleTarget rt WHERE rt.rule.name = :ruleName")
	List<CWERuleTarget> findByRuleName(@Param("ruleName") String ruleName);

	@Transactional
	@Modifying
	int deleteByRule(CWERule rule);
}
