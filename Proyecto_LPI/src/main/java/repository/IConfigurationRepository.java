package repository;

import java.sql.SQLException;
import java.util.List;

import model.Configuration;

public interface IConfigurationRepository {
    Configuration findById(String configurationId) throws SQLException, ClassNotFoundException;
    Configuration findByKey(String configKey) throws SQLException, ClassNotFoundException;
    List<Configuration> findAll() throws SQLException, ClassNotFoundException;
    void update(Configuration configuration) throws SQLException, ClassNotFoundException;
    boolean existsByKey(String configKey) throws SQLException, ClassNotFoundException;
}