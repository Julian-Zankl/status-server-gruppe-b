package at.fhburgenland.node.repository;

import at.fhburgenland.node.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for status operations (CRUD)
 */
@Repository
public class StatusRepository {
    private final List<Status> statuses = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(StatusRepository.class);

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
        // Optional<Status> is used to avoid explicit null checks
        Optional<Status> existingStatus = findStatusByUsername(status.getUsername());
        if (existingStatus.isPresent()) {
            // check if incoming status is more recent
            if (status.getTime().isAfter(existingStatus.get().getTime())) {
                statuses.remove(existingStatus.get());
                statuses.add(status);
                logger.info("Updated status with more recent data for " + status.getUsername());
            } else {
                logger.info("Ignored older or same time update for " + status.getUsername());
            }
        } else {
            // simply add new status if it doesn't exist yet
            statuses.add(status);
            logger.info("Created new status for " + status.getUsername());
        }
        logger.info(status.getUsername() + " " + status.getStatusText() + " " + status.getTime());
        return status;
    }

    /**
     * Get status by username
     * @param username Username to search for
     * @return Status object or null if not found
     */
    public Status getStatusByName(String username) {
        Optional<Status> status = findStatusByUsername(username);
        if (status.isPresent()) {
            // if the Optional contains a Status
            logger.info("Found status for username " + username);
            return status.get();
        } else {
            // if the Optional is empty
            logger.info("Status not found for username " + username);
            return null;
        }
    }

    /**
     * Get all statuses
     * @return List of all statuses
     */
    public List<Status> getStatuses() {
        if (!this.statuses.isEmpty()) {
            logger.info("Statuses available");
            return this.statuses;
        }
        logger.info("Could not get statuses - there are no statuses available!");
        return null;
    }

    /**
     * Update existing status
     * @param status Status to update
     * @return Updated status
     */
    public Status updateStatus(Status status) {
        return createStatus(status);  // redirect update requests through createStatus() to leverage the LWW logic
    }

    /**
     * Delete status for a given username
     * @param username Username to delete status for
     */
    public void deleteStatus(String username) {
        // statuses::remove is a method reference to the remove method of the `statuses` list
        // could be written as `s -> statuses.remove(s)` as well
        findStatusByUsername(username).ifPresentOrElse(
                status -> {
                    statuses.remove(status);
                    logger.info("Successfully deleted status for username " + username);
                },
                () -> logger.info("Failed to delete status: No status found for username " + username)
        );
    }

    /**
     * Find the most recent status for a given username
     * @param username Username to search for
     * @return Most recent status or empty Optional if not found
     */
    private Optional<Status> findStatusByUsername(String username) {
        // iterate over all statuses and filter by username
        return statuses.stream()
                .filter(s -> s.getUsername().equals(username)).findFirst();
    }
}
