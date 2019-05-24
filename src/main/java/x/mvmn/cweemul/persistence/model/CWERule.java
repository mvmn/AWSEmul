package x.mvmn.cweemul.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
