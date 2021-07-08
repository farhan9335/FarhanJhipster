package com.hcl.mypack.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProjectActivityMaster.
 */
@Entity
@Table(name = "project_activity_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectActivityMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "project_activity_id")
    private Integer projectActivityId;

    @NotNull
    @Column(name = "project_activity_code", nullable = false)
    private String projectActivityCode;

    @NotNull
    @Size(max = 50)
    @Column(name = "description", length = 50, nullable = false)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectActivityMaster id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getProjectActivityId() {
        return this.projectActivityId;
    }

    public ProjectActivityMaster projectActivityId(Integer projectActivityId) {
        this.projectActivityId = projectActivityId;
        return this;
    }

    public void setProjectActivityId(Integer projectActivityId) {
        this.projectActivityId = projectActivityId;
    }

    public String getProjectActivityCode() {
        return this.projectActivityCode;
    }

    public ProjectActivityMaster projectActivityCode(String projectActivityCode) {
        this.projectActivityCode = projectActivityCode;
        return this;
    }

    public void setProjectActivityCode(String projectActivityCode) {
        this.projectActivityCode = projectActivityCode;
    }

    public String getDescription() {
        return this.description;
    }

    public ProjectActivityMaster description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectActivityMaster)) {
            return false;
        }
        return id != null && id.equals(((ProjectActivityMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectActivityMaster{" +
            "id=" + getId() +
            ", projectActivityId=" + getProjectActivityId() +
            ", projectActivityCode='" + getProjectActivityCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
