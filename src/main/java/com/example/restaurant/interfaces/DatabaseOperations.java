package com.example.restaurant.interfaces;

import java.util.List;

public interface DatabaseOperations<T> {
    void add(T t);
    void update(T t);
    void delete(int id);
    List<T> getAll();
}
