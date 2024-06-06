package com.example.comutopluluklar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.comutopluluklar.databinding.ActivitySignBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignScreen : ComponentActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun girisYapclick(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
//        val isAdmin = binding.adminCheckBox.isChecked
//        val isNormalUser = binding.normalUserCheckBox.isChecked

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "E-posta veya şifre boş olamaz!", Toast.LENGTH_LONG).show()
        }

         else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(this@SignScreen, ToplulukListActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { exception ->
                Toast.makeText(this@SignScreen, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun kayitOlclick(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        val isAdmin = binding.adminCheckBox.isChecked
        val isNormalUser = binding.normalUserCheckBox.isChecked

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "E-posta veya şifre boş olamaz!", Toast.LENGTH_LONG).show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Şifre en az 6 karakter olmalıdır!", Toast.LENGTH_LONG).show()
        } else if (isAdmin && isNormalUser) {
            Toast.makeText(this, "Lütfen sadece bir seçenek işaretleyin!", Toast.LENGTH_LONG).show()
        } else if (!isAdmin && !isNormalUser) {
            Toast.makeText(this, "Lütfen bir kullanıcı türü seçin!", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
                val user = authResult.user
                if (user != null) {
                    // Kullanıcı bilgilerini içeren bir harita oluştur
                    val userMap = hashMapOf(
                        "email" to email,
                        "uid" to user.uid,
                        "isAdmin" to isAdmin
                    )

                    // Uygun koleksiyona kullanıcı bilgilerini kaydet
                    val collectionName = if (isAdmin) "admins" else "users"
                    FirebaseFirestore.getInstance().collection(collectionName).document(user.uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            val intent = Intent(this@SignScreen, ToplulukListActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this@SignScreen, "Kullanıcı bilgileri kaydedilemedi: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this@SignScreen, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}

