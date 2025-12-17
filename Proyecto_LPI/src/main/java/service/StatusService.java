package service;

import java.sql.SQLException;

import repository.IStatusRepository;

public class StatusService implements IStatusService {
    private final IStatusRepository statusRepository;

    public StatusService(IStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public String getActiveStatusId() throws SQLException, ClassNotFoundException {
        return statusRepository.findByName("Active").getStatusId();
    }

    public String getInactiveStatusId() throws SQLException, ClassNotFoundException {
        return statusRepository.findByName("Inactive").getStatusId();
    }
}
