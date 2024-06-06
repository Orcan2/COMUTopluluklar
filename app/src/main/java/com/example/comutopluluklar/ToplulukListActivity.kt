package com.example.comutopluluklar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.comutopluluklar.databinding.ActivityToplulukListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class ToplulukListActivity : AppCompatActivity() {
    lateinit var binding: ActivityToplulukListBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private lateinit var toplulukRecyclerView: RecyclerView
    private lateinit var toplulukArrayList: ArrayList<Topluluk>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityToplulukListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("Topluluklar")
        firestore = FirebaseFirestore.getInstance()
        //val binding = ActivityToplulukListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toplulukRecyclerView = binding.toplulukList

        toplulukRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        toplulukRecyclerView.setHasFixedSize(true)

        toplulukArrayList = arrayListOf()

        getToplulukData()
        checkIfUserIsAdmin()

        binding.buttonSignOff.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SignScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkIfUserIsAdmin() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            firestore.collection("admins").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Kullanıcı admin, buttonTopEkle'yi etkinleştir
                        binding.buttonTopEkle.visibility = View.VISIBLE
                        binding.buttonTopEkle.setOnClickListener {
                            val intent = Intent(this, SignScreen::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        // Kullanıcı admin değil, buttonTopEkle'yi gizle
                        binding.buttonTopEkle.visibility = View.GONE
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Admin kontrolü yapılamadı: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun getToplulukData() {
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (topSnapshot in snapshot.children) {
                        val topluluk = topSnapshot.getValue(Topluluk::class.java)
                        toplulukArrayList.add(topluluk!!)
                    }
                    toplulukRecyclerView.adapter = CustomAdapter(toplulukArrayList, this@ToplulukListActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ToplulukListActivity, "Veri alınamadı: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}

