package com.example.projectwithmaven.Repository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;

import java.util.List;

public interface IRepository<T extends Entitate> extends Iterable<T> {
    T findById(int id);
    int returnPosition(int id);
    void add(T entitate) throws RepositoryException;
    void update(T entitate) throws RepositoryException;
    void delete(int id) throws RepositoryException;
    List<T> getAllEntities();
}