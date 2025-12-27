package service;

import java.sql.SQLException;
import java.util.List;

import model.Configuration;
import repository.IConfigurationRepository;

public class ConfigurationService implements IConfigurationService {
    private final IConfigurationRepository configurationRepository;
    
    public ConfigurationService(IConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public Configuration getByKey(String configKey) throws SQLException, ClassNotFoundException {
        return configurationRepository.findByKey(configKey);
    }

    @Override
    public List<Configuration> getAllConfigurations() throws SQLException, ClassNotFoundException {
        return configurationRepository.findAll();
    }

    @Override
    public void updateConfiguration(Configuration configuration) throws SQLException, ClassNotFoundException {
        Configuration existing = configurationRepository.findById(configuration.getConfigurationId());
        
        if (existing == null) {
            throw new IllegalArgumentException("Configuración no encontrada");
        }
        
        validateConfigValue(configuration);
        
        configurationRepository.update(configuration);
    }

    @Override
    public int getIntValue(String configKey, int defaultValue) throws SQLException, ClassNotFoundException {
        try {
            Configuration config = configurationRepository.findByKey(configKey);
            if (config != null && config.getConfigValue() != null) {
                return Integer.parseInt(config.getConfigValue());
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear valor INT para " + configKey + ": " + e.getMessage());
        }
        return defaultValue;
    }

    @Override
    public String getStringValue(String configKey, String defaultValue) throws SQLException, ClassNotFoundException {
        Configuration config = configurationRepository.findByKey(configKey);
        if (config != null && config.getConfigValue() != null) {
            return config.getConfigValue();
        }
        return defaultValue;
    }

    @Override
    public boolean getBooleanValue(String configKey, boolean defaultValue) throws SQLException, ClassNotFoundException {
        Configuration config = configurationRepository.findByKey(configKey);
        if (config != null && config.getConfigValue() != null) {
            String value = config.getConfigValue().toLowerCase();
            return value.equals("true") || value.equals("1") || value.equals("yes");
        }
        return defaultValue;
    }

    @Override
    public double getDecimalValue(String configKey, double defaultValue) throws SQLException, ClassNotFoundException {
        try {
            Configuration config = configurationRepository.findByKey(configKey);
            if (config != null && config.getConfigValue() != null) {
                return Double.parseDouble(config.getConfigValue());
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear valor DECIMAL para " + configKey + ": " + e.getMessage());
        }
        return defaultValue;
    }

    /**
     * Valida que el valor de configuración sea del tipo correcto
     */
    private void validateConfigValue(Configuration config) {
        String type = config.getConfigType();
        String value = config.getConfigValue();
        
        try {
            switch (type) {
                case "INT":
                    Integer.parseInt(value);
                    break;
                case "DECIMAL":
                    Double.parseDouble(value);
                    break;
                case "BOOLEAN":
                    String normalizedValue = value.toLowerCase();
                    if (!normalizedValue.equals("true") && !normalizedValue.equals("false") &&
                        !normalizedValue.equals("1") && !normalizedValue.equals("0") &&
                        !normalizedValue.equals("yes") && !normalizedValue.equals("no")) {
                        throw new IllegalArgumentException("Valor booleano inválido");
                    }
                    break;
                case "STRING":
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de configuración desconocido: " + type);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor no es válido para el tipo " + type);
        }
    }
}