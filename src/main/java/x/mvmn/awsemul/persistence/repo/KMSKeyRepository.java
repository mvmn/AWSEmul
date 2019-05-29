package x.mvmn.awsemul.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import x.mvmn.awsemul.persistence.model.KMSKey;

public interface KMSKeyRepository extends JpaRepository<KMSKey, Long> {
	KMSKey findByKeyId(String keyId);
}
