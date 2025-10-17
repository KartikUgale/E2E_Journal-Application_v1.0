package com.JournalApplication.JournalAppLTD.controller;

import com.JournalApplication.JournalAppLTD.entity.UserEntry;
import com.JournalApplication.JournalAppLTD.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntry> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserEntry> createUser(@RequestBody UserEntry userEntry) {
        try {
            userService.saveUser(userEntry);
            return new ResponseEntity<>(userEntry, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserEntry> getUser(@PathVariable String userName) {
        UserEntry user = userService.findByUserName(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntry user, @PathVariable String userName) {
        UserEntry userInDB = userService.findByUserName(userName);
        if (userInDB != null) {
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveUser(userInDB);
            return new ResponseEntity<>(userInDB, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable ObjectId id) {
        userService.delete(id);
    }
}
