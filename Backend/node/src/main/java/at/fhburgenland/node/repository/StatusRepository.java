package at.fhburgenland.node.repository;

import at.fhburgenland.node.Status;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Repository for status operations (CRUD)
 */
@Repository
public class StatusRepository {
    private final List<Status> statuses = new ArrayList<>();

    /**
     * Constructor to initialize some dummy data.
     */
    public StatusRepository() {
        this.statuses.add(new Status("Max", "idle", LocalDateTime.now().minusHours(1)));
        this.statuses.add(new Status("Julian", "idle", LocalDateTime.now().minusHours(3)));
        this.statuses.add(new Status("Niklas", "active", LocalDateTime.now().minusHours(2)));
        this.statuses.add(new Status("Markus", "shutdown", LocalDateTime.now().minusHours(4)));
        this.statuses.add(new Status("Marco", "inactive", LocalDateTime.now().minusHours(50)));
    }

    /**
     * Create new status or update existing status if more recent
     * @param status Status to create or update
     * @return Created or updated status
     */
    public Status createStatus(Status status) {
        Optional<Status> existingStatus = findStatusByUsername(status.getUsername());
        if (existingStatus.isPresent()) {
            // Check if incoming status is more recent
            if (status.getTime().isAfter(existingStatus.get().getTime())) {
                statuses.remove(existingStatus.get());
                statuses.add(status);
                System.out.println("Updated status with more recent data for " + status.getUsername());
            } else {
                System.out.println("Ignored older or same time update for " + status.getUsername());
            }
        } else {
            // Simply add new status if it doesn't exist yet
            statuses.add(status);
            System.out.println("Created new status for " + status.getUsername());
        }
        return status;
    }

    /**
     * Get status by username
     * @param username Username to search for
     * @return Status object or null if not found
     */
    public Status getStatusByName(String username) {
        return findStatusByUsername(username).orElse(null);
    }

    /**
     * Get all statuses
     * @return List of all statuses
     */
    public List<Status> getStatuses() {
        return new ArrayList<>(statuses);
    }

    /**
     * Update status for a given username
     * @param status Status to update
     * @return Updated status
     */
    public Status updateStatus(Status status) {
        return createStatus(status);  // Redirect update requests through createStatus() to leverage the LWW logic
    }

    /**
     * Delete status for a given username
     * @param username Username to delete status for
     */
    public void deleteStatus(String username) {
        // statuses::remove is a method reference to the remove method of the `statuses` list
        // could be written as `s -> statuses.remove(s)` as well
        findStatusByUsername(username).ifPresent(statuses::remove);
    }

    /**
     * Find the most recent status for a given username
     * @param username Username to search for
     * @return Most recent status or empty Optional if not found
     */
    private Optional<Status> findStatusByUsername(String username) { // Optional<Status> is used to avoid explicit null checks
        return statuses.stream()
                // iterate over all statuses and filter by username
                .filter(s -> s.getUsername().equals(username))
                // Comparator orders `Status` objects by their `time` field, max() returns the most recent object
                .max(Comparator.comparing(Status::getTime));
    }
}
