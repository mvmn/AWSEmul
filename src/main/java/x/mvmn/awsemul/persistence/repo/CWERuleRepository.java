package x.mvmn.awsemul.persistence.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import x.mvmn.awsemul.persistence.model.CWERule;

public interface CWERuleRepository extends JpaRepository<CWERule, Long> {

	List<CWERule> findByNameStartsWith(String trim);

	@Transactional
	@Modifying
	int deleteByName(String name);

	Optional<CWERule> findByName(String name);

	@Query("SELECT DISTINCT rt.rule FROM CWERuleTarget rt WHERE rt.targetArn = :arn")
	List<CWERule> findRulesByTargetArn(@Param("arn") String targetArn);
}
