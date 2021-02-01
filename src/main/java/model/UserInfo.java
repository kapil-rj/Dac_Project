package model;

import entity.*;
import lombok.Data;

@Data
public class UserInfo {

    private String firstName;
    private String lastName;
    private String userEmail;
    private String userName;
    private String userRole;

    public UserInfo() {
    }

    public UserInfo(Account user) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userEmail = user.getUserEmail();
        this.userRole = user.getUserRole();
    }

    public UserInfo(String userName, String firstName, String lastName, String userEmail, String userRole) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }
}
