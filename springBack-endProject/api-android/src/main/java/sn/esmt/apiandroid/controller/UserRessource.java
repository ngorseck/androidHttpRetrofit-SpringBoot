package sn.esmt.apiandroid.controller;

import org.springframework.web.bind.annotation.*;
import sn.esmt.apiandroid.entities.UserResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserRessource {

    @GetMapping( path = "/login")
    public UserResponse login(@RequestParam("email") String email, @RequestParam("password") String password) {

        if (email.equals("seck@seck.sn") && password.equals("passer")) {

            return new UserResponse("OK", email);
        } else {

            return new UserResponse("NOK", email);
        }
    }

    @GetMapping( path = "/users")
    public List<UserResponse> allUsers() {

        List<UserResponse> userResponses = new ArrayList<>();

        userResponses.add(new UserResponse("OK", "seck1@seck.sn"));
        userResponses.add(new UserResponse("OK", "seck2@seck.sn"));
        userResponses.add(new UserResponse("OK", "seck3@seck.sn"));

        return userResponses;
    }
}
