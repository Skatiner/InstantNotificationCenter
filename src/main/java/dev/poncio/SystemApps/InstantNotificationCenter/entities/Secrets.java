package dev.poncio.SystemApps.InstantNotificationCenter.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "SECRETS")
public class Secrets {
    
    @Id
    @JsonIgnore
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secrets_id_seq")
	@SequenceGenerator(name = "secrets_id_seq", sequenceName = "secrets_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "SECRET", nullable = false)
    private String secret;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_START", nullable = false)
	private Date dateStart = new Date();

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_END", nullable = true)
	private Date dateEnd;

    @JsonIgnore
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @JsonIgnore
    @JoinColumn(name = "`user`", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User user;

    @OneToMany(mappedBy = "secrets")
    @BatchSize(size = 10)
    private List<SecretUse> uses;

}
