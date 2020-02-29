package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Objects;


@Entity
public class Event extends AbstractEntity {                               // Class added in lesson 12.2.1.

    // Moved to AbstractEntity in 17.5.2.
//    @Id
//    @GeneratedValue
//    private int id;
////    private static int nextId = 1;

    @NotBlank(message="Name is required!")
    @Size(min = 3, max = 50, message = " Name must be 3 to 50 characters.")
    private String name;

    @Size( max = 500, message = "Description too long 500 character limit")
    private String description;

    @NotBlank(message ="Email is required!")
    @Email(message = "Invalid email: Try again!")
    private String contactEmail;

    //Below field from exercise 13.5. (1 of 4)
    @NotBlank(message="Location must be entered!")
    @Size( max = 40, message="Location field too long, limit to 40 characters")
    private String location;

    //Below field from exercise 13.5. (2 of 4)
    @AssertTrue(message="Registration is needed.")
    private Boolean registrationNeeded;

    //Below field from exercise 13.5. (3 of 4)
//    @NotBlank(message="Can't be blank: At least one attendee needed to register.")
    @Min(1)
    private int numberAttendees;

    //Below field from exercise 13.5. (3 of 4)
    @AssertFalse(message="You cannot register if you are not funny!")
    private Boolean notFunny;

    public Event(String name, String description, String contactEmail, String location, Boolean registrationNeeded, int numberAttendees, Boolean notFunny) {
//        this();
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
//        this.id = nextId;  // was replaced by "public Event(){id...}" that is fed in by "this()"
//        nextId++;
//        this.id=id;
        this.location=location;         // Variables fro Exercise 13
        this.registrationNeeded=registrationNeeded;
        this.numberAttendees=numberAttendees;
        this.notFunny=notFunny;
    }

    // Counter turns into persistent constructor:
    public Event(){
//        this.id = nextId;
//        nextId++;
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


//    public int getId() {
//        return id;
//    }

    //Getters and Setters for 4 variables in Exercise 13:
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Boolean getRegistrationNeeded() {
        return registrationNeeded;
    }

    public void setRegistrationNeeded(Boolean registrationNeeded) {
        this.registrationNeeded = registrationNeeded;
    }

    public int getNumberAttendees() {
        return numberAttendees;
    }

    public void setNumberAttendees(int numberAttendees) {
        this.numberAttendees = numberAttendees;
    }

    public Boolean getNotFunny() {
        return notFunny;
    }

    public void setNotFunny(Boolean notFunny) {
        this.notFunny = notFunny;
    }


    @Override
    public String toString() {
        return name;        // <<<< KSP question: Why do we only need "name" in toString and not "description"?
    }

    //Moved to AbstractClass in 17.5.2.2.
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Event event = (Event) o;
//        return id == event.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);   // From video:: ..."we won't use them for now, but as a best practice, equals and hashCode method should always be there"
//    }
}

