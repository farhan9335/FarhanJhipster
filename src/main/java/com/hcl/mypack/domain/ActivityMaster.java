package com.hcl.mypack.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ActivityMaster.
 */
@Entity
@Table(name = "activity_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "activity_master_id")
    private Integer activityMasterId;

    @NotNull
    @Size(max = 250)
    @Column(name = "activity", length = 250, nullable = false)
    private String activity;

    @NotNull
    @Size(max = 50)
    @Column(name = "change_request_code", length = 50, nullable = false)
    private String changeRequestCode;

    @NotNull
    @Column(name = "project_activity_code", nullable = false)
    private String projectActivityCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityMaster id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getActivityMasterId() {
        return this.activityMasterId;
    }

    public ActivityMaster activityMasterId(Integer activityMasterId) {
        this.activityMasterId = activityMasterId;
        return this;
    }

    public void setActivityMasterId(Integer activityMasterId) {
        this.activityMasterId = activityMasterId;
    }

    public String getActivity() {
        return this.activity;
    }

    public ActivityMaster activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getChangeRequestCode() {
        return this.changeRequestCode;
    }

    public ActivityMaster changeRequestCode(String changeRequestCode) {
        this.changeRequestCode = changeRequestCode;
        return this;
    }

    public void setChangeRequestCode(String changeRequestCode) {
        this.changeRequestCode = changeRequestCode;
    }

    public String getProjectActivityCode() {
        return this.projectActivityCode;
    }

    public ActivityMaster projectActivityCode(String projectActivityCode) {
        this.projectActivityCode = projectActivityCode;
        return this;
    }

    public void setProjectActivityCode(String projectActivityCode) {
        this.projectActivityCode = projectActivityCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityMaster)) {
            return false;
        }
        return id != null && id.equals(((ActivityMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityMaster{" +
            "id=" + getId() +
            ", activityMasterId=" + getActivityMasterId() +
            ", activity='" + getActivity() + "'" +
            ", changeRequestCode='" + getChangeRequestCode() + "'" +
            ", projectActivityCode='" + getProjectActivityCode() + "'" +
            "}";
    }
}
