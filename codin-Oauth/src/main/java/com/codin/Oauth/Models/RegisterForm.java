package com.codin.Oauth.Models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by erhan on 30.10.2017.
 */
public class RegisterForm {
    @Size(min = 3, max = 50)
    @NotEmpty(message = "Please provide your firstName")
    private String firstName;

    @Size(min = 2, max = 50)
    @NotEmpty(message = "Please provide your lastName")
    private String lastName;

    @Email(message = "Please")
    private String emailAddress;

    @Size(min = 8, max = 20)
    @NotEmpty(message = "Password must be contains uppercase, lowercase, number , special character and min 8 characters)")
    private String password;
    @NotEmpty(message = "Please provide your password")
    private String passwordC;

    public RegisterForm() {
        this.firstName = "";
        this.lastName = "";
        this.emailAddress = "";
        this.password = "";
        this.passwordC = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordC() {
        return passwordC;
    }

    public void setPasswordC(String passwordC) {
        this.passwordC = passwordC;
    }
}
