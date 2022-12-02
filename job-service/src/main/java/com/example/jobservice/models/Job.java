package com.example.jobservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "job")
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class Job extends RepresentationModel<Job> {

    @Id
    @Column(name = "job_id")
    private UUID id;
    private String title;
    private String description;
    private BigDecimal salary;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @Fetch(FetchMode.SELECT)
    @JsonProperty("address")
    private Address address;

    @JsonCreator
    public Job(String title, String description, BigDecimal salary, Address address) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Job job = (Job) o;
        return id != null && Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
