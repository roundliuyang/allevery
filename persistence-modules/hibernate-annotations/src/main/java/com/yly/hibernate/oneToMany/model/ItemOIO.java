package com.yly.hibernate.oneToMany.model;

import javax.persistence.*;

@Entity
@Table(name = "ITEMSOIO")
public class ItemOIO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private CartOIO cart;

    // Hibernate requires no-args constructor
    public ItemOIO() {
    }

    public ItemOIO(CartOIO c) {
        this.cart = c;
    }

    public CartOIO getCartOIO() {
        return cart;
    }

    public void setCartOIO(CartOIO cart) {
        this.cart = cart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
