package IESVDC.SegdoDAM.practica_tema1


import IESVDC.SegdoDAM.practica_tema1.databinding.ActivityDadosBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DadosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDadosBinding
    private var sum: Int = 0
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())
        initEvent()
    }

    private fun initEvent() {
        binding.txtResultado.visibility = View.INVISIBLE
        binding.imageButton.setOnClickListener {
            binding.txtResultado.visibility = View.VISIBLE
            binding.imageButton.isEnabled = false
            scheduleRun()
        }
    }

    private fun scheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 100

        // Tiradas estéticas
        for (i in 1..5) {
            schedulerExecutor.schedule({
                handler.post { throwDados() }
            }, msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        // Mostrar resultado y desbloquear botón
        schedulerExecutor.schedule({
            handler.post {
                viewResult()
                binding.imageButton.isEnabled = true
            }
        }, msc * 7L, TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }

    private fun throwDados() {
        val numDados = Array(2) { Random.nextInt(1, 7) }  // dos dados
        val imagViews: Array<ImageView> = arrayOf(
            binding.imagviewDado1,
            binding.imagviewDado2
        )

        sum = numDados.sum()

        for (i in 0..1)
            selectView(imagViews[i], numDados[i])

        showCarta(sum)
    }

    private fun selectView(imgV: ImageView, v: Int) {
        val drawable = when (v) {
            1 -> R.drawable.dado1
            2 -> R.drawable.dado2
            3 -> R.drawable.dado3
            4 -> R.drawable.dado4
            5 -> R.drawable.dado5
            6 -> R.drawable.dado6
            else -> R.drawable.dado1
        }
        imgV.setImageResource(drawable)
    }

    private fun viewResult() {
        binding.txtResultado.text = sum.toString()
    }

    private fun showCarta(suma: Int) {
        val cartaId = when (suma) {
            2 -> R.drawable.card_clubs_02
            3 -> R.drawable.card_clubs_03
            4 -> R.drawable.card_clubs_04
            5 -> R.drawable.card_clubs_a
            6 -> R.drawable.card_clubs_06
            7 -> R.drawable.card_clubs_07
            8 -> R.drawable.card_clubs_08
            9 -> R.drawable.card_clubs_09
            10 -> R.drawable.card_clubs_10
            11 -> R.drawable.card_clubs_j
            12 -> R.drawable.card_back
            else -> R.drawable.card_back
        }
        binding.imagviewCarta.setImageResource(cartaId)
    }
}
