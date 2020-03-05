package com.example.cadastroapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    //agora crie os atributos do usuário
    String id, nome, telefone, email, senha, tipo;

    public String getId() {
        return id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    //A linha abaixo exclui a senha do usuario do Banco de Dados
    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }



    public Usuario() {

    }

    //dentro da classe do usuario
    public void cadastraUsuario(String id){

        DatabaseReference reference = ConexaoRealtimeDatabase.check();

        reference.child("usuario").child(id).setValue(this);
    }
}
