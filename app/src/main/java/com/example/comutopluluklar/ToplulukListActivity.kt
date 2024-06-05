package com.example.comutopluluklar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Alignment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.comutopluluklar.databinding.ActivityToplulukListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ToplulukListActivity : AppCompatActivity() {
    lateinit var binding: ActivityToplulukListBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref:DatabaseReference
    private lateinit var toplulukRecyclerView: RecyclerView
    private lateinit var toplulukArrayList: ArrayList<Topluluk>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        dbref=FirebaseDatabase.getInstance().getReference("Topluluklar")
        val binding=ActivityToplulukListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toplulukRecyclerView=binding.toplulukList

        //toplulukRecyclerView.layoutManager=LinearLayoutManager(this)
        toplulukRecyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        toplulukRecyclerView.setHasFixedSize(true)

        toplulukArrayList= arrayListOf<Topluluk>()

        getToplulukData()
        binding.buttonTopEkle.setOnClickListener{
            val intent = Intent(this, SignScreen::class.java)

            startActivity(intent)
            finish()
        }
        binding.buttonSignOff.setOnClickListener{
                auth.signOut()
                val intent = Intent(this, SignScreen::class.java)
                startActivity(intent)
                finish()

        }
    }

    private fun getToplulukData() {

        dbref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (topSnapshot in snapshot.children){
                        val topluluk=topSnapshot.getValue(Topluluk::class.java)
                        toplulukArrayList.add(topluluk!!)
                    }
                    toplulukRecyclerView.adapter=CustomAdapter(toplulukArrayList,this@ToplulukListActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}