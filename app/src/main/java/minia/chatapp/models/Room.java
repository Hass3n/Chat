package minia.chatapp.models;

public class Room {

    private String key;
    private String userId;
    private String employeeId;
    private Users user;
    private Employee employee;
    private Long createdAt;
    private String lastSentMessage;
    private Long lastMessageCreatedTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLastSentMessage() {
        return lastSentMessage;
    }

    public void setLastSentMessage(String lastSentMessage) {
        this.lastSentMessage = lastSentMessage;
    }

    public Long getLastMessageCreatedTime() {
        return lastMessageCreatedTime;
    }

    public void setLastMessageCreatedTime(Long lastMessageCreatedTime) {
        this.lastMessageCreatedTime = lastMessageCreatedTime;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
