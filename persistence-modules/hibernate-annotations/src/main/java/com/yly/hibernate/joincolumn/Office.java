package com.yly.hibernate.joincolumn;

import javax.persistence.*;

@Entity
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="ADDR_ID", referencedColumnName="ID"),
            @JoinColumn(name="ADDR_ZIP", referencedColumnName="ZIP")
    })
    private OfficeAddress address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfficeAddress getAddress() {
        return address;
    }

    public void setAddress(OfficeAddress address) {
        this.address = address;
    }
}