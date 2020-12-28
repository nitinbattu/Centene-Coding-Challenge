package com.healthcare.app.backend.dao;

public interface DataOperations<T, R> {
    R createRecord(T t);
    T fetchRecord(String id);
    boolean updateRecord(T t);
    boolean saveRecord(T t);
    boolean deleteRecord(String id);
}
