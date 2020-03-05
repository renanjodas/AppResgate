package com.example.cadastroapp;

import com.google.firebase.auth.FirebaseAuth;

public class AutenticacaoComFirebase {
    //variavel estatica permanece na memoria
    private static FirebaseAuth auth;

    //cria método estático para verificar a autenticação
    public static FirebaseAuth check(){

        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
