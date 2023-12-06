package com.example.projectwithmaven.Repository.ExceptionRepository;

public class DuplicateObjectException extends RepositoryException {
    public DuplicateObjectException(String message) {
        super(message);
    }

    public DuplicateObjectException(String message, Throwable e) {
        super(message, e);
    }
}