package com.JournalApplication.JournalAppLTD.controller;

import com.JournalApplication.JournalAppLTD.entity.JournalEntry;
import com.JournalApplication.JournalAppLTD.entity.UserEntry;
import com.JournalApplication.JournalAppLTD.service.JournalEntryService;
import com.JournalApplication.JournalAppLTD.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> entry = journalEntryService.getAll();
        if (!entry.isEmpty()) {
            return ResponseEntity.ok(entry);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUsers(@PathVariable String userName) {
        UserEntry user = userService.findByUserName(userName);
        List<JournalEntry> allJournalEntries = journalEntryService.getAll();
        if (!allJournalEntries.isEmpty()) {
            return ResponseEntity.ok(allJournalEntries);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String userName, @RequestBody JournalEntry entry) {
        try {
            journalEntryService.saveEntry(userName, entry);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ) {
        JournalEntry oldEntry = journalEntryService.getById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(oldEntry.getTitle() != null || !oldEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(oldEntry.getContent() != null || !oldEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id, @PathVariable String userName) {
        journalEntryService.deleteEntry(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
