package com.master.udd.exception;

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String type, Long id) {
        super(String.format("%s with id %d doesn't exist.", type, id));
    }
}
