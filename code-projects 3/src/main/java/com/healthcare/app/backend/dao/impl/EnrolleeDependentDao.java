package com.healthcare.app.backend.dao.impl;

import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.Dependent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.healthcare.app.backend.util.Constants.MONGO_ENROLLEE_DEPENDENT_COLLECTION;


@Repository
public class EnrolleeDependentDao implements DataOperations<Dependent, String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String createRecord(Dependent dependent) {
        dependent.setId(null);
        mongoTemplate.save(dependent, MONGO_ENROLLEE_DEPENDENT_COLLECTION);
        return dependent.getId();
    }

    @Override
    public Dependent fetchRecord(String dependentId) {
        return mongoTemplate.findById(dependentId, Dependent.class, MONGO_ENROLLEE_DEPENDENT_COLLECTION);
    }

    @Override
    public boolean updateRecord(Dependent dependent) {
        Dependent existingObject = fetchRecord(dependent.getId());
        if(existingObject == null) return false;
        existingObject = existingObject.mergeObject(dependent);
        mongoTemplate.save(existingObject, MONGO_ENROLLEE_DEPENDENT_COLLECTION);
        return true;
    }

    @Override
    public boolean saveRecord(Dependent dependent) {
        mongoTemplate.save(dependent);
        return true;
    }

    @Override
    public boolean deleteRecord(String dependentId) {
        mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(dependentId)), Dependent.class, MONGO_ENROLLEE_DEPENDENT_COLLECTION);
        return true;
    }
}
