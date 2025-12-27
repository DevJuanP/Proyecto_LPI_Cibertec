package controller;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Configuration;
import service.IConfigurationService;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controlador para gestionar las configuraciones del sistema.
 */
@WebServlet("/admin/configuration")
public class ConfigurationController extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPostScoped(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            handleUpdate(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL + "?page=configuracion");
        }
    }

    /**
     * Maneja la actualización de configuraciones.
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        try {
            IConfigurationService configService = getService(IConfigurationService.class);
            
            // Obtener todas las configuraciones del formulario
            String[] configIds = request.getParameterValues("configId");
            String[] configKeys = request.getParameterValues("configKey");
            String[] configValues = request.getParameterValues("configValue");
            String[] configTypes = request.getParameterValues("configType");
            String[] displayNames = request.getParameterValues("displayName");
            String[] descriptions = request.getParameterValues("description");
            
            if (configIds != null && configIds.length > 0) {
                for (int i = 0; i < configIds.length; i++) {
                    Configuration config = new Configuration();
                    config.setConfigurationId(configIds[i]);
                    config.setConfigKey(configKeys[i]);
                    config.setConfigValue(configValues[i]);
                    config.setConfigType(configTypes[i]);
                    config.setDisplayName(displayNames[i]);
                    config.setDescription(descriptions[i]);
                    
                    configService.updateConfiguration(config);
                }
                
                request.getSession().setAttribute("success", "Configuraciones actualizadas correctamente");
            } else {
                request.getSession().setAttribute("error", "No se encontraron configuraciones para actualizar");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error al actualizar las configuraciones: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", "Error de validación: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL + "?page=configuracion");
    }
}