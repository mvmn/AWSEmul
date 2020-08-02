package x.mvmn.awsemul.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import x.mvmn.awsemul.persistence.model.SecManSecret;

public interface SecManSecretRepository extends JpaRepository<SecManSecret, Long> {

	Optional<SecManSecret> findByName(String secretId);

}
