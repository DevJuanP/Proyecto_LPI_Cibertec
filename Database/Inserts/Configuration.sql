INSERT INTO Configuration (ConfigurationId, ConfigKey, ConfigValue, ConfigType, DisplayName, Description)
VALUES
(UUID_TO_BIN(UUID()), 'ItemsPerPage', '15', 'INT', 'Ítems por página', 'Número máximo de elementos a mostrar por página'),
(UUID_TO_BIN(UUID()), 'RentalRiskDays', '3', 'INT', 'Días de riesgo de vencimiento', 'Número de días antes de la fecha de vencimiento para considerar el alquiler en riesgo');
