package at.fhburgenland.node.repository;

import at.fhburgenland.node.Status;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatusRepository {
    private final List<Status> statuses = new ArrayList<>();

    public StatusRepository() {
        this.statuses.add(new Status("Max", "idle", LocalDateTime.now().minusHours(1)));
        this.statuses.add(new Status("Julian", "idle", LocalDateTime.now().minusHours(3)));
        this.statuses.add(new Status("Niklas", "active", LocalDateTime.now().minusHours(2)));
        this.statuses.add(new Status("Markus", "shutdown", LocalDateTime.now().minusHours(4)));
        this.statuses.add(new Status("Marco", "inactive", LocalDateTime.now().minusHours(50)));
    }

    /**
     * Create new status
     *
     * @param status Status to be created
     * @return Created status
     */
    public Status createStatus(Status status) {
        if (!statuses.contains(status)) {
            statuses.add(status);
            System.out.println("Created status with username " + status.getUsername());
            return statuses.getLast();
        }
        System.out.println("State does already exist!");
        return null;
    }

    /**
     * Get one specific status
     *
     * @param username Access-ID of corresponding status
     * @return Retrieved status
     */
    public Status getStatusByName(String username) {
        if (!statuses.isEmpty()) {
            for (Status status : statuses) {
                if (status.getUsername().equals(username)) {
                    System.out.println("Found status with name " + status.getUsername());
                    return status;
                }
            }
            System.out.println("No status with name " + username);
        }
        System.out.println("There are no statuses available");
        return null;
    }

    /**
     * Get all statuses
     *
     * @return List of all statuses
     */
    public List<Status> getStatuses() {
        if (!statuses.isEmpty()) {
            System.out.println("Statuses available");
            return statuses;
        }
        System.out.println("There are no statuses available!");
        return null;
    }

    /**
     * Update existing status
     *
     * @param status Status to be updated
     * @return Updated status
     */
    public Status updateStatus(Status status, String username) {
        if (!statuses.isEmpty()) {
            for (int i = 0; i < statuses.size(); i++) {
                if (statuses.get(i).getUsername().equals(username)) {
                    statuses.set(i, status);
                    System.out.println("Updated status with username " + status.getUsername());
                    return statuses.get(i);
                }
            }
            System.out.println("No status with name " + username);
        }
        System.out.println("There are no statuses available!");
        return null;
    }

    /**
     * Delete existing status
     *
     * @param username Access-ID of corresponding status
     */
    public void deleteStatus(String username) {
        if (!statuses.isEmpty()) {
            for (Status status : statuses) {
                if (status.getUsername().equals(username)) {
                    statuses.remove(status);
                    System.out.println("Deleted status with username " + status.getUsername());
                    return;
                } else {
                    System.out.println("Status does not exist!");
                }
            }
        } else {
            System.out.println("There are no statuses available!");
        }
    }
}
