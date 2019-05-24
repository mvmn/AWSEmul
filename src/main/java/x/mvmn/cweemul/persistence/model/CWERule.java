package x.mvmn.cweemul.persistence.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "cwerule_name_uniq"))
public class CWERule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Column(nullable = false)
	protected String name;
	protected String description;
	@Column(nullable = false)
	protected String scheduleExpression;
	@Column(nullable = false)
	protected String arn;
	@Column(nullable = false)
	protected String state = "ENABLED";

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rule", cascade = CascadeType.ALL)
	protected Set<CWERuleTarget> targets;
}
