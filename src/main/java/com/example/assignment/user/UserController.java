package com.example.assignment.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// import javax.validation.constraints.Email;
// import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Map<String, User> userStorage = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public ResponseEntity<String> Hello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {


      if (userStorage.containsKey(user.getUsername())) {
          return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
      }

      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userStorage.put(user.getUsername(), user);
      return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User storedUser = userStorage.get(user.getUsername());
        if (storedUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login successful " + storedUser.getPassword(), HttpStatus.OK);
    }


    @GetMapping("/fetch")
    public ResponseEntity<Object> fetchUser(@RequestParam(required = false) String username) {
        if (username == null) {
            return new ResponseEntity<>("Username cannot be null", HttpStatus.BAD_REQUEST);
        }

        User user = userStorage.get(username);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", user.getUsername());
        userDetails.put("email", user.getEmail());

        return ResponseEntity.ok(userDetails);
    }

}

class User {

  private String username;
  private String email;
  private String password;

  public String getUsername() {
      return username;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
    }
}
