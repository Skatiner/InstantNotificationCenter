package dev.poncio.SystemApps.InstantNotificationCenter.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "JOB")
public class Job implements Serializable {
    
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_id_seq")
	@SequenceGenerator(name = "job_id_seq", sequenceName = "job_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false)
	private Date startDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", nullable = true)
	private Date endDate;

    @Column(name = "FINISHED", nullable = false)
    private Boolean finished = Boolean.FALSE;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "RESULT_MESSAGE", nullable = true)
    private String resultMessage;

    @JoinColumn(name = "`group`", referencedColumnName = "id")
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Group group;

    @JsonIgnore
    @JoinColumn(name = "`user`", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobUpdate> updates;

    public static enum JobStatus {
        SUCCESS, ERROR, EXPIRED, RUNNING
    };

    public JobStatus getStatus() {
        return JobStatus.valueOf(this.status);
    }

    public void setStatus(JobStatus jobStatus) {
        this.status = jobStatus.name();
    }

}
