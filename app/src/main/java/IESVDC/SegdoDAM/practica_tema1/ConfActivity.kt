package IESVDC.SegdoDAM.practica_tema1

import Practica_tema1.IESVDC.SegdoDAM.databinding.ActivityConfBinding
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// --- ConfActivity: La Pantalla de Ajustes ---
// Esta pantalla permite al usuario guardar dos datos:
// 1. Un número de teléfono personalizado.
// 2. Una URL para el manual web.
class ConfActivity : AppCompatActivity() {

    // Declara una variable para el "binding".
    // El binding es una herramienta que conecta el código Kotlin con el diseño XML,
    // para poder acceder a los botones y textos de forma segura.
    private lateinit var binding: ActivityConfBinding

    // La función onCreate se ejecuta una sola vez, cuando la pantalla se crea.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla (crea) el objeto de binding a partir del layout XML (activity_conf.xml).
        binding = ActivityConfBinding.inflate(layoutInflater)
        // Establece el diseño de la pantalla usando la vista raíz del binding.
        setContentView(binding.root)

        // --- CARGAR DATOS GUARDADOS ---

        // Abre el fichero de preferencias llamado "numero" para leer datos.
        val sharedPreferences = getSharedPreferences("numero", MODE_PRIVATE)
        // Lee el valor guardado con la clave "numero". Si no hay nada, devuelve un texto vacío.
        val numero = sharedPreferences.getString("numero", "")
        // Si se encontró un número, lo muestra en la caja de texto correspondiente.
        if (numero != null ){
            binding.editTextPhone.setText(numero)
        }

        // (Aquí podrías añadir también la carga de la URL guardada para que se muestre al abrir)

        // --- CONFIGURACIÓN DEL BOTÓN DE GUARDAR ---

        // Establece un listener para que el código se ejecute cuando se pulsa el botón.
        binding.BotonGuardar.setOnClickListener {

            // --- GUARDAR DATOS ---

            // 1. Coge el texto que el usuario ha escrito en las cajas de texto.
            val numeroAGuardar = binding.editTextPhone.text.toString()
            val urlAGuardar = binding.editTextURL.text.toString()

            // 2. Abre los ficheros de preferencias para escribir los datos.
            // (Nota: Estás usando dos ficheros distintos, uno para el número y otro para la URL).
            val prefsNumero = getSharedPreferences("numero", MODE_PRIVATE)
            val prefsUrl = getSharedPreferences("url", MODE_PRIVATE)

            // 3. Guarda el número de teléfono.
            val editorNumero = prefsNumero.edit()
            editorNumero.putString("numero", numeroAGuardar)
            editorNumero.apply() // .apply() guarda los cambios en segundo plano.

            // 4. Guarda la URL.
            val editorUrl = prefsUrl.edit()
            editorUrl.putString("url", urlAGuardar)
            editorUrl.apply()

            // 5. Muestra un mensaje de confirmación al usuario.
            Toast.makeText(this, "Ajustes guardados correctamente", Toast.LENGTH_SHORT).show()

            // 6. Cierra la pantalla de ajustes y vuelve a la pantalla principal.
            finish()
        }
    }
}