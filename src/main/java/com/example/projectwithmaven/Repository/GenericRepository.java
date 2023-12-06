package com.example.projectwithmaven.Repository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Repository.ExceptionRepository.DuplicateObjectException;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.IRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericRepository<T extends Entitate> implements IRepository<T> {
    /*protected*/public List<T> entitati = new ArrayList<>();

    @Override
    public T findById(int id) {
        for (T entitate : entitati)
            if (entitate.getId() == id)
                return entitate;
        return null;
//        throw new NoSuchElementException("Nu avem acest element adaugat");
    }

    @Override
    public int returnPosition(int id) {
        for (int i = 0; i < entitati.size(); i++)
            if (entitati.get(i).getId() == id)
                return i;
        return -1;
//        throw new NoSuchElementException("Nu avem acest element adaugat");
    }

    @Override
    public void add(T entitate) throws RepositoryException {
        try {
            int existaEntitate = returnPosition(entitate.getId());

            // Obiectul transmis ar putea sa fie NULL
//            if (entitate == null) {
//                throw new IllegalArgumentException();
//            }
            // Nu avem aceasta entitate adaugata in lista noastra
            if (existaEntitate == -1) {
                entitati.add(entitate);
            } else {
                throw new DuplicateObjectException("Deja exista aceasta entitate adaugata");
            }
        } catch (DuplicateObjectException doe) {
            System.out.println(doe);
        }
    }

    @Override
    public void update(T entitate) throws RepositoryException {
        int existaEntitate = returnPosition(entitate.getId());

        if (existaEntitate == -1) {
            // Nu avem aceasta entitate adaugata in lista noastra generica
            throw new RepositoryException("Nu putem modifica atributele acestei entitati, pentru ca nu este inca adaugata");
        } else {
            entitati.set(existaEntitate, entitate);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        int existaEntitate = returnPosition(id);

        try{
            if (existaEntitate == -1) {
                throw new RepositoryException("Nu putem da delete, pentru ca nu este inca adaugata");
            } else {
                entitati.remove(existaEntitate);
            }
        }catch (RepositoryException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<T> getAllEntities() {
        return entitati;
    }

    @Override
    public Iterator<T> iterator() {
        // returnam un iterator pe un shallow copy :) al campului data
        return new ArrayList<T>(entitati).iterator();
    }
}
