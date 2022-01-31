package com.master.udd.exception;

public class InvalidAddressException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidAddressException(String address) {
        super(String.format("'%s' is not valid.", address));
    }
}
