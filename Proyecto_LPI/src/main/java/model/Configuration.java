package model;

import java.time.LocalDateTime;

public class Configuration {
    private String configurationId;
    private String configKey;
    private String configValue;
    private String configType;
    private String displayName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Configuration() {
    }

    public Configuration(String configurationId, String configKey, String configValue, 
                        String configType, String displayName, String description) {
        this.configurationId = configurationId;
        this.configKey = configKey;
        this.configValue = configValue;
        this.configType = configType;
        this.displayName = displayName;
        this.description = description;
    }

    public Configuration(String configurationId, String configKey, String configValue, 
                        String configType, String displayName, String description,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.configurationId = configurationId;
        this.configKey = configKey;
        this.configValue = configValue;
        this.configType = configType;
        this.displayName = displayName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return configKey + ": " + configValue;
    }
}