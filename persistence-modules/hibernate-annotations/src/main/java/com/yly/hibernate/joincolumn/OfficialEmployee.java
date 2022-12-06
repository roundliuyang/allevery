package com.yly.hibernate.joincolumn;

import javax.persistence.*;
import java.util.List;

@Entity
public class OfficialEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 官方文档描述
     *    FetchType fetch() default LAZY;
     *
     *    If the relationship is bidirectional, the mappedBy element must be used to specify the relationship field or property of the entity that is the owner of the relationship.
     *
     *    Example 3: Unidirectional One-to-Many association using a foreign key （单向）
     *       // In Customer class:
     *       @OneToMany(orphanRemoval=true)
     *       @JoinColumn(name="CUST_ID") // join column is in table for Order
     *       public Set<Order> getOrders() {return orders;}
     *
     *
     *       Example 1: One-to-Many association using generics(双向)
     *       // In Customer class:
     *       @OneToMany(cascade=ALL, mappedBy="customer")
     *       public Set<Order> getOrders() { return orders; }
     *
     *       In Order class:
     *       @ManyToOne
     *       @JoinColumn(name="CUST_ID", nullable=false)
     *       public Customer getCustomer() { return customer; }
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Email> emails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}