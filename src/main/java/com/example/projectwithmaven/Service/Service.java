package com.example.projectwithmaven.Service;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;

import java.util.List;

public class Service<T extends Entitate> implements IService<T> {
    private GenericRepository<T> repoGeneric;
    public Service(GenericRepository<T> Repository) {
        this.repoGeneric = Repository;
    }

    @Override
    public T findById(int id) {
        return repoGeneric.findById(id);
    }

    @Override
    public int returnPosition(int id) {
        return repoGeneric.returnPosition(id);
    }

    @Override
    public void add(T entitate) throws RepositoryException {
        repoGeneric.add(entitate);
    }

    @Override
    public void update(T entitate) throws RepositoryException {
        repoGeneric.update(entitate);
    }

    @Override
    public void delete(int id) throws RepositoryException {
        repoGeneric.delete(id);
    }

    @Override
    public List<T> getAllEntities() {
        return repoGeneric.getAllEntities();
    }
}


