package com.example.comutopluluklar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.comutopluluklar.databinding.ActivitySignBinding
import com.example.comutopluluklar.ui.theme.COMUTopluluklarTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth

class SignScreen : ComponentActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun girisYapclick(view: View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "E-posta veya şifre boş olamaz!", Toast.LENGTH_LONG).show()
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@SignScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {exception ->
                Toast.makeText(this@SignScreen, exception.localizedMessage  , Toast.LENGTH_LONG).show()
            }
        }

    }

    fun kayitOlclick(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.emailText.text.toString()

        if(email.isEmpty()||password.isEmpty()) {
            Toast.makeText(this, "E-posta veya şifre boş olamaz!", Toast.LENGTH_LONG).show()
        }
        else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                // Başarılıysa, MainActivity'e yönlendir
                val intent = Intent(this@SignScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { exception ->
                // Başarısızlık durumunda hata mesajını göster
                Toast.makeText(this@SignScreen, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}