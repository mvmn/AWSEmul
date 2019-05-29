package x.mvmn.awsemul.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "key_id", name = "kmskey_keyid_uniq"))
public class KMSKey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Column(nullable = false, name = "key_id")
	protected String keyId;
	protected String description;

	@Transient
	public String getArn() {
		return "arn:aws:kms:us-east-1:0:key/" + keyId;
	}
}
