package com.hcl.mypack.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "employee_name", length = 50, nullable = false)
    private String employeeName;

    @Column(name = "employee_specialization")
    private String employeeSpecialization;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Manager manager;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getEmployeeId() {
        return this.employeeId;
    }

    public Employee employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public Employee employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSpecialization() {
        return this.employeeSpecialization;
    }

    public Employee employeeSpecialization(String employeeSpecialization) {
        this.employeeSpecialization = employeeSpecialization;
        return this;
    }

    public void setEmployeeSpecialization(String employeeSpecialization) {
        this.employeeSpecialization = employeeSpecialization;
    }

    public Manager getManager() {
        return this.manager;
    }

    public Employee manager(Manager manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", employeeName='" + getEmployeeName() + "'" +
            ", employeeSpecialization='" + getEmployeeSpecialization() + "'" +
            "}";
    }
}
