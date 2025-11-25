package IESVDC.SegdoDAM.practica_tema1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// --- llamada: La Pantalla de Llamada ---
// Esta pantalla se encarga de una sola cosa: iniciar una llamada de teléfono.
// Gestiona la petición del permiso de llamada, que es obligatorio en Android.
class llamada : AppCompatActivity() {

    // Un "companion object" contiene valores que son compartidos por todas las instancias de esta clase.
    companion object {
        // Este es un código numérico único para identificar la petición del permiso de llamada.
        // Sirve para saber a qué petición está respondiendo el usuario.
        private const val REQUEST_PHONE_CALL = 1
    }

    // La función onCreate se ejecuta una sola vez, cuando la pantalla se crea.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Carga el diseño visual definido en el archivo activity_llamada.xml.
        setContentView(R.layout.activity_llamada)

        // --- Configuración de los Botones ---

        // 1. Botón para volver a la pantalla principal.
        val boton_volver = findViewById<ImageButton>(R.id.boton_volver_llamada)
        boton_volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 2. Botón principal para realizar la llamada.
        val boton_Llamada = findViewById<ImageButton>(R.id.BotonLlamada)
        boton_Llamada.setOnClickListener {
            // Comprueba si la app YA tiene permiso para hacer llamadas.
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                // Si ya tiene permiso, llama directamente.
                dialPhoneNumber()
            } else {
                // Si no tiene permiso, se lo pide al usuario.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            }
        }
    }

    // Esta función se ejecuta automáticamente DESPUÉS de que el usuario responde (Permitir/Denegar) a la petición de permiso.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Comprueba si la respuesta es para nuestra petición de llamada (usando el código que definimos antes).
        if (requestCode == REQUEST_PHONE_CALL) {
            // Si la respuesta no está vacía y el usuario ha concedido el permiso...
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // ...entonces, realiza la llamada.
                dialPhoneNumber()
            } else {
                // Si el usuario ha denegado el permiso, muestra un aviso.
                Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show()
                // Y lo lleva directamente a los ajustes de la app para que pueda activarlo manualmente si cambia de opinión.
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }

    // Función privada que se encarga de construir y lanzar la llamada.
    private fun dialPhoneNumber() {
        // Crea una "intención" para realizar una llamada (ACTION_CALL).
        val intent = Intent(Intent.ACTION_CALL).apply {
            // Lee el número de teléfono guardado por el usuario en las preferencias.
            val sharedPreferences = getSharedPreferences("confPrefs", MODE_PRIVATE)
            val numeroGuardado = sharedPreferences.getString("numeroTelefono", "") // Si no hay nada, usa "" (vacío)

            // Prepara el número para la llamada, añadiendo "tel:".
            data = Uri.parse("tel:$numeroGuardado")
        }
        // Comprueba si hay alguna app en el teléfono que pueda hacer llamadas.
        if (intent.resolveActivity(packageManager) != null) {
            // Si la hay, inicia la llamada.
            startActivity(intent)
        } else {
            // Si no, muestra un aviso.
            Toast.makeText(this, "No se puede realizar la llamada", Toast.LENGTH_SHORT).show()
        }
    }
}