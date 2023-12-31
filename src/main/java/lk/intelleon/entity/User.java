package lk.intelleon.entity;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:04 PM
 **/

public class User {
    private String userId;
    private String userName;
    private String password;
    private Boolean activeState;
    private String userType;

    public User() {
    }

    public User(String userId, String userName, String password, Boolean activeState, String userType) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.activeState = activeState;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActiveState() {
        return activeState;
    }

    public void setActiveState(Boolean activeState) {
        this.activeState = activeState;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", activeState=" + activeState +
                ", userType='" + userType + '\'' +
                '}';
    }
}
