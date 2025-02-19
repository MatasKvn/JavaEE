package org.example.demo;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;


@RequestScoped
@Named
public class UserBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String submit() {
        // Logic to handle the submit action
        System.out.println("Name: " + name);
        return "success";  // Redirect to another page, if necessary
    }
}
