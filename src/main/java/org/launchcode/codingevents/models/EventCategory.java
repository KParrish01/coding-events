package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class EventCategory extends AbstractEntity {

//    @Id
//    @GeneratedValue
//    private int id;

    @Size(min=2, message="Name must be at least 2 characters long.")
    private String name;

    @OneToMany(mappedBy = "eventCategory")
    private final List<Event> events = new ArrayList<>();

    public EventCategory(int id, String name) {
//        this.id = id;
        this.name = name;
    }

    public EventCategory() {
    }

//    public int getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return name;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        EventCategory that = (EventCategory) o;
//        return id == that.id &&
//                Objects.equals(name, that.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), id, name);
//    }
}
