package at.fhburgenland.backend.rest;

import at.fhburgenland.backend.Status;
import at.fhburgenland.backend.service.StatusService;
import at.fhburgenland.backend.service.StatusUpdateSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    private final StatusService statusService;
    private final StatusUpdateSender statusUpdateSender;

    @Autowired
    public StatusController(StatusService statusService, StatusUpdateSender statusUpdateSender) {
        this.statusService = statusService;
        this.statusUpdateSender = statusUpdateSender;
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status createdStatus = this.statusService.createStatus(status);
        statusUpdateSender.sendCreateStatusUpdate(createdStatus);
        return ResponseEntity.ok(createdStatus);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Status> getStatusByName(@PathVariable String username) {
        Status status = this.statusService.getStatusByName(username);
        statusUpdateSender.sendReadStatusUpdate(status);
        return ResponseEntity.ok(status);
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = this.statusService.getAllStatuses();
        statuses.forEach(statusUpdateSender::sendReadStatusUpdate);
        return ResponseEntity.ok(statuses);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status, @PathVariable String username) {
        Status updatedStatus = this.statusService.updateStatus(status, username);
        statusUpdateSender.sendUpdateStatusUpdate(updatedStatus);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteStatus(@PathVariable String username) {
        this.statusService.deleteStatus(username);
        Status deletedStatus = new Status(username, null, null); // Status-Objekt mit nur dem Benutzernamen erstellen
        statusUpdateSender.sendDeleteStatusUpdate(deletedStatus);
        return ResponseEntity.noContent().build();
    }
}
