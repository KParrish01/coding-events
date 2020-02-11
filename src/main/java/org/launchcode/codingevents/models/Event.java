package org.launchcode.codingevents.models;

import java.util.Objects;

public class Event {   // Class added in lesson 12.2.1.

    private int id;
    private static int nextId = 1;

    private String name;
    private String description;

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = nextId;
        nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;        // <<<< KSP question: Why do we only need "name" in toString and not "description"?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);   // From video:: ..."we won't use them for now, but as a best practice, equals and hashCode method should always be there"
    }
}

