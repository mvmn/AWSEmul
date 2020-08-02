package x.mvmn.awsemul.persistence.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import x.mvmn.awsemul.persistence.model.SecManSecret;

public interface SecManSecretRepository extends JpaRepository<SecManSecret, Long> {

	Optional<SecManSecret> findByName(String secretId);

	@Transactional
	@Modifying
	int deleteByName(String secretId);
}
