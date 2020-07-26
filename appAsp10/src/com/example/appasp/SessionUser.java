package com.example.appasp;

public final class SessionUser {
	static Usuario c;

    public SessionUser() {

    }

    public SessionUser(Usuario c) {
        this.c = c;
    }

    public Usuario getUsuario() {
        return c;
    }
}
