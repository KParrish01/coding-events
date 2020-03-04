package org.launchcode.codingevents.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
//        AbstractEntity entity = (AbstractEntity) o;     // Hank's version
        return id == that.id;
//        return id == entity.id;                         // Hank's version
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
