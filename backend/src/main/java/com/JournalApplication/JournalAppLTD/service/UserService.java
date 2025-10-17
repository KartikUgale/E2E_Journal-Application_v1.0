package com.JournalApplication.JournalAppLTD.service;

import com.JournalApplication.JournalAppLTD.entity.UserEntry;
import com.JournalApplication.JournalAppLTD.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public List<UserEntry> getAll() {
        return userRepo.findAll();
    }

    public void saveUser(UserEntry user) {
        userRepo.save(user);
    }

    public UserEntry findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public Optional<UserEntry> getById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void delete(ObjectId id) {
        userRepo.deleteById(id);
    }
}
