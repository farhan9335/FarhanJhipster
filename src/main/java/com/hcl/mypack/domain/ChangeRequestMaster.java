package com.hcl.mypack.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChangeRequestMaster.
 */
@Entity
@Table(name = "change_request_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChangeRequestMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "change_request_id")
    private Integer changeRequestId;

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

    public ChangeRequestMaster id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getChangeRequestId() {
        return this.changeRequestId;
    }

    public ChangeRequestMaster changeRequestId(Integer changeRequestId) {
        this.changeRequestId = changeRequestId;
        return this;
    }

    public void setChangeRequestId(Integer changeRequestId) {
        this.changeRequestId = changeRequestId;
    }

    public String getChangeRequestCode() {
        return this.changeRequestCode;
    }

    public ChangeRequestMaster changeRequestCode(String changeRequestCode) {
        this.changeRequestCode = changeRequestCode;
        return this;
    }

    public void setChangeRequestCode(String changeRequestCode) {
        this.changeRequestCode = changeRequestCode;
    }

    public String getProjectActivityCode() {
        return this.projectActivityCode;
    }

    public ChangeRequestMaster projectActivityCode(String projectActivityCode) {
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
        if (!(o instanceof ChangeRequestMaster)) {
            return false;
        }
        return id != null && id.equals(((ChangeRequestMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangeRequestMaster{" +
            "id=" + getId() +
            ", changeRequestId=" + getChangeRequestId() +
            ", changeRequestCode='" + getChangeRequestCode() + "'" +
            ", projectActivityCode='" + getProjectActivityCode() + "'" +
            "}";
    }
}
