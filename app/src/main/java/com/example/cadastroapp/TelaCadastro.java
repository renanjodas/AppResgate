package com.example.cadastroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class TelaCadastro extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;

    EditText editTextNome, editTextTelefone, editTextEmail, editTextSenha;
    Button buttonCadastrar;

    Switch switchTipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        auth = AutenticacaoComFirebase.check();

        editTextEmail = findViewById(R.id.editTextCadastroEmail);
        editTextNome = findViewById(R.id.editTextCadastroNome);
        editTextSenha = findViewById(R.id.editTextCadastroSenha);
        editTextTelefone = findViewById(R.id.editTextCadastroTel);

        switchTipoUsuario = findViewById(R.id.switchCadastro);

        //Ação do botao para cadastrar o usuario
        buttonCadastrar = findViewById(R.id.buttonCadastroCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCamposCadastro()){
                    cadastraUsuario();
                }
            }
        });

    }

    public String tipoUsuario(){
        return switchTipoUsuario.isChecked() ? "Socorrista" : "Usuario";
    }

    public boolean validarCamposCadastro(){

        if(editTextNome.getText().toString().isEmpty()){
            Toast.makeText(this, "Preencha o campo nome", Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return false;
        }else if(editTextTelefone.getText().toString().isEmpty() || editTextNome.getText().toString().length() < 8){
            Toast.makeText(this, "Preencha o campo telefone", Toast.LENGTH_SHORT).show();
            editTextTelefone.requestFocus();
            return false;
        }else if(editTextEmail.getText().toString().isEmpty() || editTextEmail.getText().toString().equals("@")){
            Toast.makeText(this, "Preencha o campo e-mail", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return false;
        }else if(editTextSenha.getText().toString().isEmpty() || editTextSenha.getText().toString().length() < 6) {
            Toast.makeText(this, "Preencha o campo senha", Toast.LENGTH_SHORT).show();
            editTextSenha.requestFocus();
            return false;
        }else{
            return true;
        }

    }

    //método que realmente cadastrará usuario
    public void cadastraUsuario(){
        //primeiro salvar os dados de login
        auth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),editTextSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //istancia a classe usuarios
                    Usuario usuario = new Usuario();
                    //pega o id que foi salvo no processo de criação do login e senha
                    usuario.setId(task.getResult().getUser().getUid());
                    usuario.setEmail(editTextEmail.getText().toString());
                    usuario.setSenha(editTextSenha.getText().toString());
                    usuario.setNome(editTextNome.getText().toString());
                    usuario.setTelefone(editTextTelefone.getText().toString());
                    usuario.setTipo(tipoUsuario());

                        if(tipoUsuario() == "Usuario") {
                            startActivity(new Intent(TelaCadastro.this,UsuarioMapsActivity.class));
                        }else{
                            startActivity(new Intent(TelaCadastro.this, SocorristaActivity.class));
                        }
                        usuario.cadastraUsuario(task.getResult().getUser().getUid());
                    }else{
                        try{
                            throw task.getException();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                }

                }
            });

    }
}
