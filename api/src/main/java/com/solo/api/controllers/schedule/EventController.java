package com.solo.api.controllers.schedule;

import com.solo.api.models.schedule.Event;
import com.solo.api.models.stopSmoking.StopSmoking;
import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.schedule.EventRepository;
import com.solo.api.repositories.user.SoloUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
public class EventController {
    @Autowired
    EventRepository repo;

    @Autowired
    SoloUserRepository soloRepo;


    @GetMapping("/{idUser}")
    public ResponseEntity<List<Event>> getEventsByIdUser(@PathVariable Integer idUser){
        List<Event> events = repo.getEventsByIdUser(idUser);

        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @GetMapping("/{idUser}/{idEvent}")
    public ResponseEntity<Event> getEvent(@PathVariable Integer idUser, @PathVariable Integer idEvent){
        return repo.findById(idEvent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/{idUser}/add")
    public ResponseEntity<?> addEvent (@PathVariable SoloUser idUser, @RequestBody Map<String, String> body){

        Event newEvent = new Event();

        String title = body.get("title");
        String category = body.get("category");
        String eventDateStr = body.get("eventDate");
        String startTimeStr = body.get("startTime");
        String endTimeStr = body.get("endTime");
        String location = body.get("location");
        String description = body.get("description");

        Date eventDate;
        Time startTime;
        Time endTime;

        try{
            eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(eventDateStr);
            startTime = Time.valueOf(startTimeStr + ":00");
            endTime = Time.valueOf(endTimeStr + ":00");
        } catch (Exception e){
            return new ResponseEntity<>("Erro na conversão dos dados", HttpStatus.BAD_REQUEST);
        }

        newEvent.setIdUser(idUser);
        newEvent.setTitle(title);
        newEvent.setCategory(category);
        newEvent.setEventDate(eventDate);
        newEvent.setStartTime(startTime);
        newEvent.setEndTime(endTime);
        newEvent.setLocation(location);
        newEvent.setDescription(description);

        repo.save(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @DeleteMapping("/{idUser}/{idEvent}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer idUser, @PathVariable Integer idEvent) {
        if (!soloRepo.existsById(idUser)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!repo.existsById(idEvent)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }

        repo.deleteById(idEvent);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idUser}/{idEvent}")
    public ResponseEntity<?> updateEvent(@PathVariable Integer idUser, @PathVariable Integer idEvent, @RequestBody Map<String, String> body) {
        // Busca o usuário pelo idUser para evitar criar uma nova instância
        Optional<SoloUser> userOptional = soloRepo.findById(idUser);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        SoloUser user = userOptional.get();

        return repo.findById(idEvent)
                .map(existingEvent -> {
                    try {
                        // Conversão de dados
                        String eventDateStr = body.get("eventDate");
                        String startTimeStr = body.get("startTime");
                        String endTimeStr = body.get("endTime");

                        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(eventDateStr);
                        Time startTime = Time.valueOf(startTimeStr + ":00"); // Adiciona ":00" para o formato HH:mm:ss
                        Time endTime = Time.valueOf(endTimeStr + ":00");

                        // Atualiza os dados do evento existente
                        existingEvent.setIdUser(user);
                        existingEvent.setEventDate(eventDate);
                        existingEvent.setCategory(body.get("category"));
                        existingEvent.setLocation(body.get("location"));
                        existingEvent.setTitle(body.get("title"));
                        existingEvent.setStartTime(startTime);
                        existingEvent.setEndTime(endTime);

                        return ResponseEntity.ok(repo.save(existingEvent));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na conversão dos dados: " + e.getMessage());
                    }
                })
                .orElseGet(() -> {
                    try {
                        // Cria um novo evento caso ele não exista
                        Event newEvent = new Event();

                        String eventDateStr = body.get("eventDate");
                        String startTimeStr = body.get("startTime");
                        String endTimeStr = body.get("endTime");

                        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(eventDateStr);
                        Time startTime = Time.valueOf(startTimeStr + ":00"); // Adiciona ":00" para o formato HH:mm:ss
                        Time endTime = Time.valueOf(endTimeStr + ":00");

                        // Configura os dados do novo evento
                        newEvent.setIdUser(user);
                        newEvent.setEventDate(eventDate);
                        newEvent.setCategory(body.get("category"));
                        newEvent.setLocation(body.get("location"));
                        newEvent.setTitle(body.get("title"));
                        newEvent.setStartTime(startTime);
                        newEvent.setEndTime(endTime);

                        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(newEvent));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na conversão dos dados: " + e.getMessage());
                    }
                });
    }
}
