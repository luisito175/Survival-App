package IESVDC.SegdoDAM.practica_tema1

import IESVDC.SegdoDAM.practica_tema1.ConfActivity
import Practica_tema1.IESVDC.SegdoDAM.R
import IESVDC.SegdoDAM.practica_tema1.llamada
import android.content.ActivityNotFoundException
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// --- MainActivity: La Pantalla Principal ---
// Esta es la primera pantalla que se abre. Su función es ser un menú principal
// con botones que llevan a las diferentes funciones de la aplicación.
class MainActivity : AppCompatActivity() {

    // La función onCreate se ejecuta una sola vez, cuando la pantalla se crea.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Carga el diseño visual definido en el archivo activity_main.xml.
        setContentView(R.layout.activity_main)

        // --- Configuración de los Botones ---

        // 1. Botón para ir a la pantalla de llamada de emergencia.
        val botonllamar = findViewById<ImageButton>(R.id.botonllamar)
        botonllamar.setOnClickListener {
            // Crea una "intención" para abrir la pantalla 'llamada'.
            val intent = Intent(this, llamada::class.java)
            // Inicia la nueva pantalla.
            startActivity(intent)
        }

        // 2. Botón para ir a la pantalla de Ajustes de la app.
        val botonAjustes = findViewById<ImageButton>(R.id.botonAjustes)
        botonAjustes.setOnClickListener {
            // Crea una "intención" para abrir la pantalla 'ConfActivity'.
            val intent = Intent(this, ConfActivity::class.java)
            startActivity(intent)
        }

        // 3. Botón para abrir una página web.
        val botonweb = findViewById<ImageButton>(R.id.botonweb)
        botonweb.setOnClickListener {
            // Busca en las preferencias si hay una URL guardada por el usuario.
            val sharedPreferences = getSharedPreferences("url", MODE_PRIVATE)
            val url = sharedPreferences.getString(
                "url",
                // Si no hay ninguna URL guardada, usa esta por defecto.
                "https://esupervivencia.com/wp-content/uploads/2012/05/curso-supervivencia-bosque.pdf"
            )

            // Si la URL no está vacía...
            if (!url.isNullOrEmpty()) {
                // Crea una "intención" para ver una URL en el navegador.
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                try {
                    // Intenta abrir el navegador.
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Si no hay un navegador instalado, muestra un aviso.
                    Toast.makeText(this, "No hay navegador disponible", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No has introducido una URL válida", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Botón para abrir los ajustes de ahorro de batería del teléfono.
        val botonahorro = findViewById<ImageButton>(R.id.botonahorro)
        botonahorro.setOnClickListener {
            // Crea una "intención" para abrir una pantalla de ajustes específica del sistema.
            val intent = Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS)
            startActivity(intent)
        }

        // 5. Botón para poner una alarma.
        val botonAlarma = findViewById<ImageButton>(R.id.alarma)
        botonAlarma.setOnClickListener {
            //Saco al hora actual y le sumamos dos minutos para ponerla
            val calendar = Calendar.getInstance().apply {
                add(Calendar.MINUTE, 2) // Añade 2 minutos al tiempo actual
            }
            //ponemos la alarma con sus datos
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE))
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma SOS")
            }
            //este if sacado de chatgpt lo puse para ver porque me fallaba, cosa que era tema de un permiso erroneo pero ya lo dejo para que no de error en el emulador
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay una app de alarma disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }
}