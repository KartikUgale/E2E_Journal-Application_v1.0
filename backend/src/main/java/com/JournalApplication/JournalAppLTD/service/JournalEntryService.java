package com.JournalApplication.JournalAppLTD.service;

import com.JournalApplication.JournalAppLTD.entity.JournalEntry;
import com.JournalApplication.JournalAppLTD.entity.UserEntry;
import com.JournalApplication.JournalAppLTD.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepo;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }

    @Transactional
    public void saveEntry(String userName, JournalEntry journalEntry) {
        try {
            UserEntry user = userService.findByUserName(userName);
            journalEntry.setDateTime(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    public void deleteEntry(ObjectId id, String userName) {
        UserEntry user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.equals(id));
        userService.saveUser(user);
        journalEntryRepo.deleteById(id);
    }

}
