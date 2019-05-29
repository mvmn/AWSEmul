package x.mvmn.awsemul.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import x.mvmn.awsemul.persistence.model.CWERule;
import x.mvmn.awsemul.persistence.model.CWERuleTarget;

public interface CWERuleTargetRepository extends JpaRepository<CWERuleTarget, Long> {

	@Query("SELECT rt FROM CWERuleTarget rt WHERE rt.rule.name = :ruleName")
	List<CWERuleTarget> findByRuleName(@Param("ruleName") String ruleName);

	@Transactional
	@Modifying
	int deleteByRule(CWERule rule);
}
