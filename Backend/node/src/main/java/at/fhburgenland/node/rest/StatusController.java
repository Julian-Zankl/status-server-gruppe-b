package at.fhburgenland.node.rest;

import at.fhburgenland.node.Status;
import at.fhburgenland.node.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatusController {

    // Define Endpoints as CRUD Functionalities
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Create new status
     * @param status Status to be created
     * @return Response with status code 200 and created status
     */
    @PostMapping(path = "/statuses")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        return ResponseEntity.ok(this.statusService.createStatus(status));
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
        return ResponseEntity.ok(this.statusService.updateStatus(status, username));
    }

    /**
     * Delete existing status
     * @param username Access-ID of corresponding status
     * @return Response with status code 204 and no content
     */
    @DeleteMapping(path = "/statuses/{username}")
    public ResponseEntity<?> deleteStatus(@PathVariable String username) {
        this.statusService.deleteStatus(username);
        return ResponseEntity.noContent().build();
    }


}
