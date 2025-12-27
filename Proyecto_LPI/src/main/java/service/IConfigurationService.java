package service;

import java.sql.SQLException;
import java.util.List;

import model.Configuration;

public interface IConfigurationService {
    Configuration getByKey(String configKey) throws SQLException, ClassNotFoundException;
    List<Configuration> getAllConfigurations() throws SQLException, ClassNotFoundException;
    void updateConfiguration(Configuration configuration) throws SQLException, ClassNotFoundException;
    
    int getIntValue(String configKey, int defaultValue) throws SQLException, ClassNotFoundException;
    String getStringValue(String configKey, String defaultValue) throws SQLException, ClassNotFoundException;
    boolean getBooleanValue(String configKey, boolean defaultValue) throws SQLException, ClassNotFoundException;
    double getDecimalValue(String configKey, double defaultValue) throws SQLException, ClassNotFoundException;
}