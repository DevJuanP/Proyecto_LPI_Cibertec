package service;

import java.sql.SQLException;

public interface IStatusService {
    String getActiveStatusId() throws SQLException, ClassNotFoundException;

    String getInactiveStatusId() throws SQLException, ClassNotFoundException;
}
