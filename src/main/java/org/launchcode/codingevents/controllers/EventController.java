package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static com.sun.beans.introspect.PropertyInfo.Name.required;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    // EventRepository makes available via CrudRepository: findAll, save, findById

//    private static List<Event> events = new ArrayList<>(); // that's how we stored data before we had EventData class

    // adds typed in events to the list of events:
    @GetMapping
    public String displayEvents(@RequestParam(required = false) Integer categoryId, Model model) {
//        List<String> events = new ArrayList<>();
//        events.add("Code with Pride");
//        events.add("Another Fun Event");
//        events.add("One More Awesome Event");
//        events.add("Ohaha");
        if (categoryId == null) {
            model.addAttribute("title", "All Events");
//        model.addAttribute("events", EventData.getAll());
            model.addAttribute("events", eventRepository.findAll());
        } else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isEmpty()){
                model.addAttribute("title", "Invalid Category ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                model.addAttribute("title", "Event in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
//
        }
        return "events/index"; // with model addition, file "events/index" needed to change (go look at it!)
    }

    // lives at /events/create
    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories",eventCategoryRepository.findAll());
        return "events/create";
    }

//    // also lives at /events/create which is ok, since they handle different events // >>>refactored into Model Binding below<<<
//    @PostMapping("create")
//    public String processCreateEventForm(@RequestParam String eventName,
//                                         @RequestParam String eventDescription) {
//        EventData.add(new Event(eventName, eventDescription));
//        return "redirect:/events";  // sends back to root "events" and can therefore also be written like "redirect:" only
//    }

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

    // This showed up in code for Chapter 18.5.1. when tags were added to code:

    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventId, Model model) {

        Optional<Event> result = eventRepository.findById(eventId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            // Added in 18.5.5. video about minute 25:
            model.addAttribute("tags", event.getTags());
        }

        return "events/detail";
    }

    // responds to requests /events/add-tag?eventID=13
    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title","Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
//        model.addAttribute("event", event);               //        model.addAttribute("event", event);
//        model.addAttribute("eventTag",new EventTagDTO()); // needed to be replaced with below so th:field="@{eventTag.event} isn't null
        EventTagDTO eventTag = new EventTagDTO();  // needed to be added so in add-tag.html th:field="@{eventTag.event}" isn't empty (instead of passing "new EventTagDTO() in directly)
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);
        return "events/add-tag.html";
    }

    //handler for above:
    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Errors errors,
                                    Model model) {
        if (!errors.hasErrors()){
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);  // We need to save it so hibernate can update MySQL table: just because we save it in Java doesn't mean it saves automatically in table. Also: hibernate is smart enough to not save same twice, if change didn't make difference for table
            }
            return "redirect:detail?eventId=" + event.getId();
        }
        return "redirect:add-tag";  //we're not dealing here with error handling for time's sake, just sending back to add-tag form
    }

///>>>>******* THIS NEEDS FIXING: Exercise 12.5.: How to feed 'int id'  in here from 'events/index' ????******<<<<<:
//    @GetMapping("edit/{eventId}")
//    @GetMapping("edit/{eventId=event.id}")
//    @GetMapping("edit/{event.id}")
    @GetMapping("edit/{id}{name}")
//    public String displayEditForm(Model model, @PathVariable int eventId) {
//    public String displayEditForm(Model model, @RequestParam int id) {
    public String displayEditForm(Model model, @PathVariable int id, @PathVariable String name) {
//    public String displayEditForm(Model model, @RequestParam int id, @RequestParam String name) {
        // controller code will go here //
        model.addAttribute("title", "Edit " + name + " (id=" + id +")");
//        model.addAttribute("events", EventData.getById(id));
//        model.addAttribute("events", eventRepository.findById(id));
        model.addAttribute("events", eventRepository.findAll());
        System.out.println("Checking in at events/edit/{id}");
        return "events/edit";
    }

//    @PostMapping("edit/{eventId=event.id}")
@PostMapping("edit/{id}")
    public String processEditForm(@ModelAttribute Event eventById, @RequestParam int id, Model model) {
        //controller code will go here:
//        EventData.getById(id);
        eventRepository.findById(id);
//        model.addAttribute("events", EventData.getById(id));
        /* 12.9.b Update the name and description of the event with the appropriate model setter methods*/
        return "redirect:/events";
    }

}
