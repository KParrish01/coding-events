package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EventCategory {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    public EventCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public EventCategory() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
