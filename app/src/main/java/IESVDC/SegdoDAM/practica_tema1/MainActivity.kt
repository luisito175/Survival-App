package IESVDC.SegdoDAM.practica_tema1

import IESVDC.SegdoDAM.practica_tema1.databinding.ActivityMainBinding
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón Ajustes
        binding.botonAjustes.setOnClickListener {
            startActivity(Intent(this, ConfActivity::class.java))
        }

        binding.botonllamar.setOnClickListener {
            startActivity(Intent(this, llamada::class.java))
        }

        // Botón Web
        binding.botonweb.setOnClickListener {
            val prefs = getSharedPreferences("confPrefs", Context.MODE_PRIVATE)
            val url = prefs.getString("urlWeb", "https://esupervivencia.com/wp-content/uploads/2012/05/curso-supervivencia-bosque.pdf")
            if (!url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay URL configurada", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Ahorro
        binding.botonahorro.setOnClickListener {
            startActivity(Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS))
        }

        // Botón Alarma
        binding.alarma.setOnClickListener {
            val calendar = Calendar.getInstance().apply { add(Calendar.MINUTE, 2) }
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE))
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma SOS")
            }
            startActivity(intent)
        }



        // Botón Dados
        binding.botonChiste.setOnClickListener {
            startActivity(Intent(this, DadosActivity::class.java))
        }

        // Botón Chistes
        binding.botonDado.setOnClickListener {
            startActivity(Intent(this, ChistesActivity::class.java))
        }

        // Botones y lógica de supervivencia se mantienen igual
        binding.botonllamar //... etc (todo lo que estaba antes sigue exactamente igual)
    }
}
