package com.hcl.mypack.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Manager.
 */
@Entity
@Table(name = "manager")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "manager_id")
    private Integer managerId;

    @NotNull
    @Size(max = 50)
    @Column(name = "manager_name", length = 50, nullable = false)
    private String managerName;

    @NotNull
    @Size(max = 10)
    @Column(name = "level", length = 10, nullable = false)
    private String level;

    @OneToMany(mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "manager" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manager id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getManagerId() {
        return this.managerId;
    }

    public Manager managerId(Integer managerId) {
        this.managerId = managerId;
        return this;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public Manager managerName(String managerName) {
        this.managerName = managerName;
        return this;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getLevel() {
        return this.level;
    }

    public Manager level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public Manager employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Manager addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setManager(this);
        return this;
    }

    public Manager removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setManager(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setManager(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setManager(this));
        }
        this.employees = employees;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        return id != null && id.equals(((Manager) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manager{" +
            "id=" + getId() +
            ", managerId=" + getManagerId() +
            ", managerName='" + getManagerName() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
