package com.example.assignment.root;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<String> getRoot() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
