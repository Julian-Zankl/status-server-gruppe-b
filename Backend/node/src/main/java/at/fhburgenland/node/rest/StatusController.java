package at.fhburgenland.node.rest;

import at.fhburgenland.node.Status;
import at.fhburgenland.node.StatusMessage;
import at.fhburgenland.node.service.MessageSenderService;
import at.fhburgenland.node.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Status controller for handling status requests.
 */
@RestController
public class StatusController {
    private final StatusService statusService;
    private final MessageSenderService messageSenderService;

    /**
     * Constructor
     * @param statusService Status service
     * @param messageSenderService Message sender service
     */
    @Autowired
    public StatusController(StatusService statusService, MessageSenderService messageSenderService) {
        this.statusService = statusService;
        this.messageSenderService = messageSenderService;
    }

    /**
     * Create new status
     * @param status Status to be created
     * @return Response with status code 200 and created status
     */
    @PostMapping(path = "/statuses")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status saved = this.statusService.createStatus(status);
        messageSenderService.sendMessage("CREATE", saved);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get one specific status
     * @param username Access-ID of corresponding status
     * @return Response with status code 200 and retrieved status
     */
    @GetMapping(path = "/statuses/{username}")
    public ResponseEntity<Status> getStatusByName(@PathVariable String username) {
        return ResponseEntity.ok(this.statusService.getStatusByName(username));
    }

    /**
     * Get all statuses
     * @return Response with status code 200 and a list of all statuses
     */
    @GetMapping(path = "/statuses")
    public ResponseEntity<List<Status>> getAllStatuses() {
        return ResponseEntity.ok(this.statusService.getAllStatuses());
    }

    /**
     * Update existing status
     * @param status Status to be updated
     * @return Response with status code 200 and updated status
     */
    @PutMapping(path = "/statuses/{username}")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status, @PathVariable String username) {
        Status updated = this.statusService.updateStatus(status, username);
        messageSenderService.sendMessage("UPDATE", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete existing status
     * @param username Access-ID of corresponding status
     * @return Response with status code 204 and no content
     */
    @DeleteMapping(path = "/statuses/{username}")
    public ResponseEntity<?> deleteStatus(@PathVariable String username) {
        Status toDelete = new Status();
        toDelete.setUsername(username);
        this.statusService.deleteStatus(username);
        this.messageSenderService.sendMessage("DELETE", toDelete);
        return ResponseEntity.noContent().build();
    }

}
