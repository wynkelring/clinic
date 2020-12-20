package pl.pisarkiewicz.User.controller;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/list/doctor/json", produces = "application/json")
    public ResponseEntity<List<User>> getDoctorListJson() {
        return new ResponseEntity<>(userService.getDoctorsList(), HttpStatus.OK);
    }

    @GetMapping(value = "/list/doctor/xml", produces = "application/xml")
    public ResponseEntity<List<User>> getDoctorListXml() {
        return new ResponseEntity<>(userService.getDoctorsList(), HttpStatus.OK);
    }

}

