package com.JournalApplication.JournalAppLTD.repository;

import com.JournalApplication.JournalAppLTD.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUserName(String userName);
}
