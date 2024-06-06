package com.example.comutopluluklar

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.comutopluluklar.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //private val database = FirebaseDatabase.getInstance("https://COMUtopluluklar-27e29-default-rtdb.firebaseio.com/")
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        var topID=""
        binding.buttonKaydet.setOnClickListener {
            topID= Random.nextInt(100).toString()
            var etToplulukismi = binding.etToplulukad.text.toString().trim()
            var etToplulukaciklama = binding.etToplulukAciklama.text.toString().trim()
            if (TextUtils.isEmpty(etToplulukismi)) {
                binding.etToplulukad.error = "Topluluk ismi giriniz"
            }
            if(TextUtils.isEmpty(etToplulukaciklama)){
                binding.etToplulukAciklama.error = "Topluluk açıklaması giriniz"
            }
            else if(!TextUtils.isEmpty(etToplulukaciklama)&&!TextUtils.isEmpty(etToplulukismi)){
                var database=FirebaseDatabase.getInstance()
                var databaseReference=database.reference.child("Topluluklar")
                var id=databaseReference.child(topID)

                id.child("id").setValue(topID)
                id.child("toplulukadi").setValue(etToplulukismi)
                id.child("toplulukaciklama").setValue(etToplulukaciklama)
                id.child("toplulukgonderileri")
                Toast.makeText(this,"Topluluk kaydedildi",Toast.LENGTH_LONG).show()

            }

        }
        binding.buttonKayitlarigoster.setOnClickListener{
            val intent = Intent(this, ToplulukListActivity::class.java)

            startActivity(intent)
            finish()
        }
    }

}
