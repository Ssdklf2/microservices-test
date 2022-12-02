package com.example.jobservice.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class Address {
    @Id
    @Column(name = "address_id")
    private UUID id;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String streetName;
    @NotNull
    private int houseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return id != null && Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
