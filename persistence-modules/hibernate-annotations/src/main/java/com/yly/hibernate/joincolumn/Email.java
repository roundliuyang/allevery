package com.yly.hibernate.joincolumn;

import javax.persistence.*;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    /**
     *  FetchType fetch() default EAGER;
     *  If the relationship is bidirectional, the non-owning OneToMany entity side must used the mappedBy element
     *  to specify the relationship field or property of the entity that is the owner of the relationship.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private OfficialEmployee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OfficialEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(OfficialEmployee employee) {
        this.employee = employee;
    }
}