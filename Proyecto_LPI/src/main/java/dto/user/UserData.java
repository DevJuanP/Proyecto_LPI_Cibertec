package dto.user;

import java.time.LocalDateTime;
import java.util.List;

public class UserData {
    private final String userId;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String statusId;
    private final String statusName;
    private final List<String> roleNames;

    public UserData(String userId, String email, LocalDateTime createdAt, 
                    LocalDateTime updatedAt, String statusId, String statusName,
                    List<String> roleNames) {
        this.userId = userId;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statusId = statusId;
        this.statusName = statusName;
        this.roleNames = roleNames;
    }

    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getStatusId() { return statusId; }
    public String getStatusName() { return statusName; }
    public List<String> getRoleNames() { return roleNames; }
    
    public boolean hasRole(String roleName) {
        return roleNames != null && roleNames.stream()
            .anyMatch(role -> roleName.equalsIgnoreCase(role));
    }
    
    public String getRolesDisplay() {
        if (roleNames == null || roleNames.isEmpty()) {
            return "Sin roles";
        }
        return String.join(", ", roleNames);
    }
}