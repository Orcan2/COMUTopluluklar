package com.example.comutopluluklar

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comutopluluklar.databinding.ActivityDetayBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DetayActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetayBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var gonderiRecyclerView: RecyclerView
    private lateinit var gonderiArrayList: ArrayList<ToplulukGonderi>
    private lateinit var gonderiText: ArrayList<String>
    private lateinit var DetayAdapter: CustomDetayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView ve ArrayList inizializasyonu
        gonderiRecyclerView = binding.gonderiRV
        gonderiRecyclerView.layoutManager = LinearLayoutManager(this)
        gonderiRecyclerView.setHasFixedSize(true)

        gonderiArrayList = arrayListOf()
        DetayAdapter = CustomDetayAdapter(gonderiArrayList, this)
        gonderiRecyclerView.adapter = DetayAdapter

        // Firebase Realtime Database referansı oluştur
        dbref = Firebase.database.reference

        // Yeni gonderileri çek
        //val intent = intent


        val toplulukID=intent.getStringExtra("putID")
        val topluluklarDugumuRef = dbref.child("Topluluklar").child(toplulukID.toString())

        gonderiText = arrayListOf()

        topluluklarDugumuRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Önce mevcut listeyi temizle
                gonderiText.clear()
                gonderiArrayList.clear()

                // Tüm çocuk düğümleri üzerinden geçerek verileri ArrayList'e ekle
                for (childSnapshot in snapshot.children) {
                    val metin = childSnapshot.getValue(String::class.java)
                    if (metin != null) {
                        gonderiText.add(metin)
                        gonderiArrayList.add(ToplulukGonderi(metin))
                    }
                }

                // Veriler başarıyla çekildikten sonra RecyclerView'ı güncelle
                DetayAdapter.notifyDataSetChanged()
                println("Metinler başarıyla çekildi: $gonderiText")
            }

            override fun onCancelled(error: DatabaseError) {
                // Veritabanından veri çekme işlemi başarısız olursa
                println("Veri çekme işlemi başarısız: ${error.message}")
            }
        })

        // Detay tasarim sayfasına verileri cekiyoruz

        val toplulukAdi = intent.getStringExtra("putAd")
        val toplulukAciklamasi = intent.getStringExtra("putAciklama")

        binding.detayTopAd.text = toplulukAdi.toString()
        binding.detayTopAciklama.text = toplulukAciklamasi.toString()

        // Buton tıklaması ile yeni gönderi ekleme
        binding.buttonGonderiEkle.setOnClickListener {
            if (TextUtils.isEmpty(binding.yenigonderiText.text.toString())) {
                binding.yenigonderiText.error = "Buraya metin giriniz"
            }
            else {
                // Yeni metin verisi
                val yeniGonderi = binding.yenigonderiText.text.toString()

                // Yeni metni eklemek için düğüm referansı
                val yeniMetinRef =
                    topluluklarDugumuRef.push() // push() metodu yeni bir benzersiz alt düğüm oluşturur
                yeniMetinRef.setValue(yeniGonderi)
                    .addOnSuccessListener {
                        println("Metin başarıyla eklendi.")
                    }
                    .addOnFailureListener {
                        println("Metin eklenirken bir hata oluştu: ${it.message}")
                    }
            }
        }
    }
}
