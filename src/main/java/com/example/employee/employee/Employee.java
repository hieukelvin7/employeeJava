package com.example.employee.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name="\"employee\"")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@SQLDelete(sql = "UPDATE \"employee\" SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
        generator = "employee_sequence"
    )
    private  Long id;
    private String name;
    private String email;
    private String address;
    private Integer age;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Employee() {
    }

    public Employee(Long id, String name, String email, String address, Integer age, boolean deleted, Company company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.deleted = deleted;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", deleted=" + deleted +
                ", company=" + company +
                '}';
    }
}
