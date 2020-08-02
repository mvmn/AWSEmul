package x.mvmn.awsemul.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "secmansecret_name_uniq"))
public class SecManSecret {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Column(nullable = false, name = "name")
	protected String name;
	protected String description;
	protected String secretString;
	protected byte[] secretBinary;
	protected String kmsKeyId;
	@Column(nullable = false)
	protected Long lastChangeDate;

	@Transient
	public String getArn() {
		return "arn:aws:secretsmanager:us-east-1:123456789012:secret:" + name + "-123abc";
	}
}
