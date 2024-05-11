package at.fhburgenland.backend.service;

import at.fhburgenland.backend.Status;
import at.fhburgenland.backend.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Create new status
     * @param status Status to be created
     * @return Created status
     */
    public Status createStatus(Status status) {
        return this.statusRepository.createStatus(status);
    }

    /**
     * Get one specific status
     * @param username Access-ID of corresponding status
     * @return Retrieved status
     */
    public Status getStatusByName(String username) {
        return this.statusRepository.getStatusByName(username);
    }

    /**
     * Get all statuses
     * @return List of all statuses
     */
    public List<Status> getAllStatuses() {
        return this.statusRepository.getStatuses();
    }

    /**
     * Update existing status
     * @param status Status to be updated
     * @return Updated status
     */
    public Status updateStatus(Status status, String username) {
        return this.statusRepository.updateStatus(status, username);
    }

    /**
     * Delete existing status
     * @param username Access-ID of corresponding status
     */
    public void deleteStatus(String username) {
        this.statusRepository.deleteStatus(username);
    }
}