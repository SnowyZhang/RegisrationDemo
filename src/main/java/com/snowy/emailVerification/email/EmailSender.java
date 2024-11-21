package com.snowy.emailVerification.email;

public interface EmailSender {
    void send(String to, String email);
}
