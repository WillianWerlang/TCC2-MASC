package org.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class _masc_usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String tipoUser;
    private String tipoDef;

    protected _masc_usuario() {}

    public _masc_usuario(String nome, String email, String senha, String tipoUser, String tipoDef) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUser = tipoUser;
        this.tipoDef = tipoDef;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getTipoDef() {
        return tipoDef;
    }

    public void setTipoDef(String tipoDef) {
        this.tipoDef = tipoDef;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, Nome='%s', email='%s' ']",
                id, nome, email);
    }
}
