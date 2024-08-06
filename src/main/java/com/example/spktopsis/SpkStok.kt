package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spktopsis.adapter.BarangSpinnerAdapter
import kotlin.math.pow
import kotlin.math.sqrt

class SpkStok : AppCompatActivity() {
    private lateinit var btnKeputusan:Button
    private lateinit var back:ImageView

    private lateinit var spinnerBarang1: Spinner
    private lateinit var spinnerBarang2: Spinner
    private lateinit var spinnerBarang3: Spinner
    private lateinit var spinnerBarang4: Spinner
    private lateinit var spinnerBarang5: Spinner

    private lateinit var inputMinatPelanggan1: EditText
    private lateinit var inputMinatPelanggan2: EditText
    private lateinit var inputMinatPelanggan3: EditText
    private lateinit var inputMinatPelanggan4: EditText
    private lateinit var inputMinatPelanggan5: EditText

    private lateinit var inputBobotKriteria1: EditText
    private lateinit var inputBobotKriteria2: EditText
    private lateinit var inputBobotKriteria3: EditText
    private lateinit var inputBobotKriteria4: EditText
    private lateinit var inputBobotKriteria5: EditText

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var barangList: ArrayList<Barang>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spk_stok)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)
        barangList = databaseHelper.ambilSemuaBarang()

        spinnerBarang1 = findViewById(R.id.spinnerBarang1)
        spinnerBarang2 = findViewById(R.id.spinnerBarang2)
        spinnerBarang3 = findViewById(R.id.spinnerBarang3)
        spinnerBarang4 = findViewById(R.id.spinnerBarang4)
        spinnerBarang5 = findViewById(R.id.spinnerBarang5)

        inputMinatPelanggan1 = findViewById(R.id.minatPelanggan1)
        inputMinatPelanggan2 = findViewById(R.id.minatPelanggan2)
        inputMinatPelanggan3 = findViewById(R.id.minatPelanggan3)
        inputMinatPelanggan4 = findViewById(R.id.minatPelanggan4)
        inputMinatPelanggan5 = findViewById(R.id.minatPelanggan5)

        inputBobotKriteria1 = findViewById(R.id.bobotKriteria1)
        inputBobotKriteria2 = findViewById(R.id.bobotKriteria2)
        inputBobotKriteria3 = findViewById(R.id.bobotKriteria3)
        inputBobotKriteria4 = findViewById(R.id.bobotKriteria4)
        inputBobotKriteria5 = findViewById(R.id.bobotKriteria5)

        back=findViewById(R.id.btnBack)
        btnKeputusan=findViewById(R.id.btnKeputusan)

        val adapter = BarangSpinnerAdapter(this, android.R.layout.simple_spinner_item, barangList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBarang1.adapter = adapter
        spinnerBarang2.adapter = adapter
        spinnerBarang3.adapter = adapter
        spinnerBarang4.adapter = adapter
        spinnerBarang5.adapter = adapter

        back.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnKeputusan.setOnClickListener{
            hitungTopsis()
        }
    }

    private fun hitungTopsis() {
        val minatPelanggan = arrayOf(
            inputMinatPelanggan1.text.toString().toDouble(),
            inputMinatPelanggan2.text.toString().toDouble(),
            inputMinatPelanggan3.text.toString().toDouble(),
            inputMinatPelanggan4.text.toString().toDouble(),
            inputMinatPelanggan5.text.toString().toDouble()
        )

        val bobotKriteria = arrayOf(
            inputBobotKriteria1.text.toString().toDouble(),
            inputBobotKriteria2.text.toString().toDouble(),
            inputBobotKriteria3.text.toString().toDouble(),
            inputBobotKriteria4.text.toString().toDouble(),
            inputBobotKriteria5.text.toString().toDouble()
        )

        // Mengambil data barang dari spinner
        val selectedBarang = arrayOf(
            barangList[spinnerBarang1.selectedItemPosition],
            barangList[spinnerBarang2.selectedItemPosition],
            barangList[spinnerBarang3.selectedItemPosition],
            barangList[spinnerBarang4.selectedItemPosition],
            barangList[spinnerBarang5.selectedItemPosition]
        )

        // Matriks keputusan
        val matrix = Array(5) { DoubleArray(2) }
        for (i in 0..4) {
            matrix[i][0] = selectedBarang[i].harga
            matrix[i][1] = selectedBarang[i].jumlahStok.toDouble()
        }

        // Normalisasi matriks
        val normalizedMatrix = Array(5) { DoubleArray(2) }
        // Loop untuk setiap kolom dalam matriks keputusan
        for (j in 0..1) {
            var sum = 0.0

            //Hitung jumlah kuadrat dari setiap elemen dalam kolom j
            for (i in 0..4) {
                sum += matrix[i][j].pow(2.0)
            }
            // Hitung akar kuadrat dari jumlah kuadrat
            val sqrtSum = sqrt(sum)
            // Normalisasi setiap elemen dalam kolom j dengan membagi elemen tersebut dengan sqrtSum
            for (i in 0..4) {
                normalizedMatrix[i][j] = matrix[i][j] / sqrtSum
            }
        }

        // Normalisasi terbobot
        val weightedNormalizedMatrix = Array(5) { DoubleArray(2) }
        for (i in 0..4) {
            for (j in 0..1) {
                weightedNormalizedMatrix[i][j] = normalizedMatrix[i][j] * bobotKriteria[j]
            }
        }

        // Menghitung solusi ideal positif dan negatif
        val idealPositive = DoubleArray(2) { Double.NEGATIVE_INFINITY }
        val idealNegative = DoubleArray(2) { Double.POSITIVE_INFINITY }
        // Loop untuk setiap kolom dalam matriks tertimbang
        for (j in 0..1) {
            // Loop untuk setiap baris dalam matriks terbobot
            for (i in 0..4) {
                if (weightedNormalizedMatrix[i][j] > idealPositive[j]) {
                    idealPositive[j] = weightedNormalizedMatrix[i][j]
                }
                if (weightedNormalizedMatrix[i][j] < idealNegative[j]) {
                    idealNegative[j] = weightedNormalizedMatrix[i][j]
                }
            }
        }

        // Menghitung jarak ke solusi ideal positif dan negatif
        val distancePositive = DoubleArray(5) // Array untuk menyimpan jarak ke solusi ideal positif
        val distanceNegative = DoubleArray(5) // Array untuk menyimpan jarak ke solusi ideal negatif
        for (i in 0..4) {
            distancePositive[i] = sqrt(weightedNormalizedMatrix[i].mapIndexed { j, value -> (value - idealPositive[j]).pow(2.0) }.sum())
            distanceNegative[i] = sqrt(weightedNormalizedMatrix[i].mapIndexed { j, value -> (value - idealNegative[j]).pow(2.0) }.sum())
        }

        // Menghitung skor preferensi
        val preferenceScores = DoubleArray(5)
        for (i in 0..4) {
            preferenceScores[i] = distanceNegative[i] / (distancePositive[i] + distanceNegative[i])
        }

        // Mengurutkan hasil berdasarkan skor preferensi
        val sortedResults = selectedBarang.zip(preferenceScores.toList()).sortedByDescending { it.second }


        // Menampilkan hasil ke Toast
        val hasilToast = sortedResults.withIndex().joinToString("\n") { (index, result) ->
            val jumlahStokTambahan = (result.second * 10).toInt() // Misalnya, tambahkan 10% dari nilai preferensi
            "${index + 1}. ${result.first.nama}: ${result.second}, Stok yang harus ditambahkan: $jumlahStokTambahan"
        }
        Toast.makeText(this, hasilToast, Toast.LENGTH_LONG).show()

//        val intent = Intent(this, Hasil_keputusan::class.java)
//        val sortedResultsParcelable = sortedResults.map { it.first }.toTypedArray()
//        intent.putExtra("sortedResults", sortedResultsParcelable)
//        startActivity(intent)

    }

}