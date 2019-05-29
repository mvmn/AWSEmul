package x.mvmn.awsemul.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "cwe_rule_id", "targetId" }, name = "cweruletarget_id_uniq"))
public class CWERuleTarget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Column(nullable = false)
	protected String targetId;
	@Column(nullable = false)
	protected String targetArn;
	protected String input;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "cwe_rule_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cwerule_rule_id"))
	protected CWERule rule;
}
