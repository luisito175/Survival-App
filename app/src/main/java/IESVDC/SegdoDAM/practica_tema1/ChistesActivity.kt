package IESVDC.SegdoDAM.practica_tema1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import IESVDC.SegdoDAM.practica_tema1.databinding.ActivityChistesBinding
import java.util.Locale
import kotlin.random.Random

class ChistesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChistesBinding
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var handler: Handler
    private val TOUCH_MAX_TIME = 500
    private var touchLastTime: Long = 0
    private val MYTAG = "CHISTES_LOG"

    private val chistes = listOf(
        "Cuál es el animal más antiguo. La cebra porque está en blanco y negro.",
        "Por qué los pájaros no usan Facebook. Porque ya tienen Twitter.",
        "Cuál es el café más peligroso. El ex-preso.",
        "Qué hace una abeja en el gimnasio. Zum-ba.",
        "Cuál es el colmo de un electricista. Que le dé miedo la oscuridad.",
        "Por qué las focas miran siempre hacia arriba. Porque ahí están los focos.",
        "Qué hace una vaca cuando sale el sol. Sombra.",
        "Cuál es el animal más dormilón. El koala, porque siempre está colgado.",
        "Qué le dice un pez a otro pez. Nada, nada.",
        "Por qué los esqueletos no pelean entre ellos. Porque no tienen agallas."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureTextToSpeech()
        initHandler()
        initEvent()
    }

    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.Builder().setLanguage("es").build()
                Log.i(MYTAG, "TextToSpeech configurado correctamente en español")
            } else {
                Log.i(MYTAG, "Error al configurar TextToSpeech")
            }
        }
    }

    private fun initHandler() {
        handler = Handler(Looper.getMainLooper())
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLeerChiste.visibility = View.GONE

        Thread {
            Thread.sleep(2000) // ejemplo de tiempo de carga
            handler.post {
                binding.progressBar.visibility = View.GONE
                binding.btnLeerChiste.visibility = View.VISIBLE
                speakMeDescription("Pulsa una vez para descripción. Pulsa dos veces para escuchar un chiste.")
            }
        }.start()
    }

    private fun initEvent() {
        binding.btnLeerChiste.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - touchLastTime < TOUCH_MAX_TIME) {
                // doble pulsación -> chiste aleatorio
                val chiste = chistes.random()
                binding.txtChiste.text = chiste
                speakMeDescription(chiste)
                Log.i(MYTAG, "Doble pulsación: chiste leído")
            } else {
                // pulsación simple -> descripción del botón
                speakMeDescription("Botón para escuchar un chiste")
                Log.i(MYTAG, "Pulsación simple: descripción")
            }
            touchLastTime = currentTime
        }
    }

    private fun speakMeDescription(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ttsChiste")
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
