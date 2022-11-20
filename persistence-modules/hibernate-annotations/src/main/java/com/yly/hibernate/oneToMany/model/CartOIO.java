package com.yly.hibernate.oneToMany.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CARTOIO")
public class  CartOIO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "cart_id") // we need to duplicate the physical information
    private Set<ItemOIO> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<ItemOIO> getItems() {
        return items;
    }

    public void setItems(Set<ItemOIO> items) {
        this.items = items;
    }

}
