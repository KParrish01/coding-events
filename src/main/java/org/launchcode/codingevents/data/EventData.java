package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventData {  // it's a static class, really, because all methods below are static (but we're leaving class not static (KSP assumes to be flexible for alter use?)

    // need a place to put events (data structure of sorts)
    private static final Map<Integer, Event> events = new HashMap<>(); // "make sure Map doesn't change (data in it can)... to make it final for additional security so we can't change it with something funny, once it's set"

    // We need four behaviors:
    // 1. get all events:
    public static Collection<Event> getAll() {
        return events.values();
    }

    // 2. get single event:
    public static Event getById(int id) {
        return events.get(id);
    }

    // 3. add event:
    public static void add(Event event) {
        events.put(event.getId(), event);
    }

    // 4. remove an event:
    public static void remove(int id) {
        events.remove(id);
    }

}
