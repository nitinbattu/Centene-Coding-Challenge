package com.healthcare.app.backend.dao.impl;

import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.Enrollee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.healthcare.app.backend.util.Constants.MONGO_ENROLLEE_COLLECTION;

@Repository
@Slf4j
public class EnrolleeDao implements DataOperations<Enrollee, String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String createRecord(Enrollee enrollee) {
        enrollee.setId(null); // making sure unique id even if someone passes id accidentally
        mongoTemplate.save(enrollee, MONGO_ENROLLEE_COLLECTION);
        return enrollee.getId();
    }

    @Override
    public Enrollee fetchRecord(String enrollee_id) {
        return mongoTemplate.findById(enrollee_id, Enrollee.class, MONGO_ENROLLEE_COLLECTION);
    }

    @Override
    public boolean updateRecord(Enrollee enrollee) {
        Enrollee existingObject = fetchRecord(enrollee.getId());
        if(existingObject == null) return false;

        existingObject = existingObject.mergeObject(enrollee);
        mongoTemplate.save(existingObject, MONGO_ENROLLEE_COLLECTION);
        return true;
    }

    @Override
    public boolean saveRecord(Enrollee enrollee) {
        mongoTemplate.save(enrollee);
        return true;
    }

    @Override
    public boolean deleteRecord(String id) {
        mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(id)), Enrollee.class, MONGO_ENROLLEE_COLLECTION);
        return true;
    }
}
