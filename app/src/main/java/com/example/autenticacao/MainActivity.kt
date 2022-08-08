package com.example.autenticacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val signUpCall: TextView = findViewById(R.id.signUpCall)

        signUpCall.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val btnLogar:Button = findViewById(R.id.btnLogin)
        btnLogar.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email: TextView = findViewById(R.id.loginEmail)
        val senha: TextView = findViewById(R.id.loginSenha)

        if(email.text.isEmpty() || senha.text.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val emailString = email.text.toString()
        val senhaString = senha.text.toString()

        auth.signInWithEmailAndPassword(emailString, senhaString)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Autenticação bem-sucedida!",
                        Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Autenticação falhou \uD83D\uDE1E!",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Erro brabo ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
    }
}