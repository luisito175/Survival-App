package IESVDC.SegdoDAM.practica_tema1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import IESVDC.SegdoDAM.practica_tema1.databinding.ActivityAcertasteBinding

class acertaste : AppCompatActivity() {

    private lateinit var binding: ActivityAcertasteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcertasteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
