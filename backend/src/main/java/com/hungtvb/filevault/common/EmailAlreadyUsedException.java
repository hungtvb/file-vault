package com.hungtvb.filevault.common;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Email đã được sử dụng");
    }
}
