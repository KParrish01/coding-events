package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // EventRepository makes available via CrudRepository: findAll, save, findById

//    private static List<Event> events = new ArrayList<>(); // that's how we stored data before we had EventData class

    // adds typed in events to the list of events:
    @GetMapping
    public String displayAllEvents(Model model) {
//        List<String> events = new ArrayList<>();
//        events.add("Code with Pride");
//        events.add("Another Fun Event");
//        events.add("One More Awesome Event");
//        events.add("Ohaha");
        model.addAttribute("title", "All Events");
//        model.addAttribute("events", EventData.getAll());
        model.addAttribute("events", eventRepository.findAll());
        return "events/index"; // with model addition, file "events/index" needed to change (go look at it!)
    }

    // lives at /events/create
    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        return "events/create";
    }

//    // also lives at /events/create which is ok, since they handle different events // >>>refactored into Model Binding below<<<
//    @PostMapping("create")
//    public String processCreateEventForm(@RequestParam String eventName,
//                                         @RequestParam String eventDescription) {
//        EventData.add(new Event(eventName, eventDescription));
//        return "redirect:/events";  // sends back to root "events" and can therefore also be written like "redirect:" only
//    }
//

    // also lives at /events/create which is ok, since they handle different events
    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
//            model.addAttribute("errorMsg", "Bad Data!");
            return "events/create";
        }
//        EventData.add(newEvent);
        eventRepository.save(newEvent);
        return "redirect:/events";  // sends back to root "events" and can therefore also be written like "redirect:" only
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
//        model.addAttribute("events", EventData.getAll());
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds !=null) {
            for (int id : eventIds) {
//                EventData.remove(id);
                eventRepository.deleteById(id);
            }
        }
        return "redirect:/events";
    }


///>>>>******* THIS NEEDS FIXING: Exercise 12.5.: How to feed 'int id'  in here from 'events/index' ????******<<<<<:
//    @GetMapping("edit/{eventId}")
//    @GetMapping("edit/{eventId=event.id}")
//    @GetMapping("edit/{event.id}")
    @GetMapping("edit/{id}")
//    public String displayEditForm(Model model, @PathVariable int eventId) {
//    public String displayEditForm(Model model, @RequestParam int id) {
    public String displayEditForm(Model model, @PathVariable int id) {
        // controller code will go here //
        model.addAttribute("title", "Edit Event " + "NAME needs to feed in here :| " + " (id=" + id + " ).");
//        model.addAttribute("events", EventData.getById(id));
        model.addAttribute("events", eventRepository.findById(id));
        System.out.println("Checking in at events/edit/{id}");
        return "events/edit";
    }

    @PostMapping("edit/{eventId=event.id}")
    public String processEditForm(@ModelAttribute Event eventById, @RequestParam int id, Model model) {
        //controller code will go here:
//        EventData.getById(id);
        eventRepository.findById(id);
//        model.addAttribute("events", EventData.getById(id));
        return "redirect:/events";
    }

}
