package at.fhburgenland.node.repository;

import at.fhburgenland.node.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for status. This class is used to manage the status data.
 */
@Repository
public class StatusRepository {
    private final List<Status> statuses = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(StatusRepository.class);

    /**
     * Constructor to create some initial statuses
     */
    public StatusRepository() {
        this.statuses.add(new Status("Max", "idle", LocalDateTime.now().minusHours(1)));
        this.statuses.add(new Status("Julian", "idle", LocalDateTime.now().minusHours(3)));
        this.statuses.add(new Status("Niklas", "active", LocalDateTime.now().minusHours(2)));
        this.statuses.add(new Status("Markus", "shutdown", LocalDateTime.now().minusHours(4)));
        this.statuses.add(new Status("Marco", "inactive", LocalDateTime.now().minusHours(50)));
    }

    /**
     * Create new status
     * @param status Status to be created
     * @return Created status
     */
    public Status createStatus(Status status) {
        if (!statuses.contains(status)) {
            statuses.add(status);
            logger.info("Created status with username {}", status.getUsername());
            return statuses.getLast();
        }
        logger.info("State does already exist!");
        return null;
    }

    /**
     * Get one specific status
     * @param username Access-ID of corresponding status
     * @return Retrieved status
     */
    public Status getStatusByName(String username) {
        if (!statuses.isEmpty()) {
            for (Status status : statuses) {
                if (status.getUsername().equals(username)) {
                    logger.info("Found status with name {}", status.getUsername());
                    return status;
                }
            }
            logger.info("Could not get by name - no status with name {}", username);
        }
        logger.info("There are no statuses available");
        return null;
    }

    /**
     * Get all statuses
     * @return List of all statuses
     */
    public List<Status> getStatuses() {
        if (!statuses.isEmpty()) {
            logger.info("Statuses available");
            return statuses;
        }
        logger.info("Could not get statuses - there are no statuses available!");
        return null;
    }

    /**
     * Update existing status
     * @param status Status to be updated
     * @return Updated status
     */
    public Status updateStatus(Status status, String username) {
        if (!statuses.isEmpty()) {
            for (int i = 0; i < statuses.size(); i++) {
                if (statuses.get(i).getUsername().equals(username)) {
                    statuses.set(i, status);
                    logger.info("Updated status with username " + status.getUsername());
                    return statuses.get(i);
                }
            }
            logger.info("Could not update status - no status with name {}", username);
        }
        logger.info("Could not update status - there are no statuses available!");
        return null;
    }

    /**
     * Delete existing status
     * @param username Access-ID of corresponding status
     */
    public void deleteStatus(String username) {
        if (!statuses.isEmpty()) {
            for (Status status : statuses) {
                if (status.getUsername().equals(username)) {
                    statuses.remove(status);
                    logger.info("Deleted status with username {}", status.getUsername());
                    return;
                } else {
                    logger.info("Could not delete status - status does not exist!");
                }
            }
        } else {
            logger.info("Could not delete status - there are no statuses available!");
        }
    }
}
