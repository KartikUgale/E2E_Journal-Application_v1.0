package com.JournalApplication.JournalAppLTD.entity;

import lombok.NonNull;
import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data   // no need to create getter and setter due to lombok tool
public class UserEntry {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @DBRef  // Linked 'User' Collection to 'journalEntry' collection
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
