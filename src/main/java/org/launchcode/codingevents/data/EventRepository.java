package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Injects CrudRepository interface via @Autowired into controller (here EventController) and does 'magic' CRUD to MySQL

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}

// Will, on th fly, create (in memory) a public class just like:
// public class MyRepository implements EventRepository {}