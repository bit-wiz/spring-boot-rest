package com.example.assignment.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {
  
  @GetMapping("/")
  String home() {
    return "YELLOW";
  }
}
