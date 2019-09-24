package com.lucass.spring.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "customers")
public class Customer extends Person {
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Column(name = "count")
//    @NotEmpty
    private String count;
}
