package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class EventDetails extends AbstractEntity {

    @Size( max = 500, message = "Description too long 500 character limit")
    private String description;

    @NotBlank(message ="Email is required!")
    @Email(message = "Invalid email: Try again!")
    private String contactEmail;

//    // Not used here, but could be set up for two-way relationship:
//    @OneToOne(mappedBy = "eventDetails") // Only needed if we want Event and EventDetail to be mapped both ways - Won't create new foreign key, but go look into Event if there is one set up already
//    private Event event;

    public EventDetails(@Size(max = 500, message = "Description too long 500 character limit") String description, @NotBlank(message = "Email is required!") @Email(message = "Invalid email: Try again!") String contactEmail) {
        this.description = description;
        this.contactEmail = contactEmail;
    }

    public EventDetails() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}

