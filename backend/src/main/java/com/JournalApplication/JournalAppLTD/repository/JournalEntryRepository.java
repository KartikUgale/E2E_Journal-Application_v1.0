package com.JournalApplication.JournalAppLTD.repository;

import com.JournalApplication.JournalAppLTD.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
