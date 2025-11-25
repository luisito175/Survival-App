package IESVDC.SegdoDAM.practica_tema1

import IESVDC.SegdoDAM.practica_tema1.databinding.ActivityConfBinding
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("confPrefs", Context.MODE_PRIVATE)

        // Cargar preferencias al iniciar
        binding.checkModoOscuro.isChecked = prefs.getBoolean("modoOscuro", false)
        binding.checkOpcion1.isChecked = prefs.getBoolean("opcion1", false)
        binding.checkOpcion2.isChecked = prefs.getBoolean("opcion2", false)
        binding.editNumero.setText(prefs.getString("numeroTelefono", ""))
        binding.editURL.setText(prefs.getString("urlWeb", ""))
        val radioSeleccionado = prefs.getInt("radioSeleccionado", -1)
        if (radioSeleccionado != -1) binding.radioGroup.check(radioSeleccionado)

        // Aplicar modo oscuro al iniciar
        aplicarModoOscuro(binding.checkModoOscuro.isChecked)

        // Listener modo oscuro
        binding.checkModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            aplicarModoOscuro(isChecked)
        }

        // Listener para CheckBox Opción 1
        binding.checkOpcion1.setOnCheckedChangeListener { _, isChecked ->
            val mensaje = if (isChecked) "Opción 1 activada" else "Opción 1 desactivada"
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        // Listener para CheckBox Opción 2
        binding.checkOpcion2.setOnCheckedChangeListener { _, isChecked ->
            val mensaje = if (isChecked) "Opción 2 activada" else "Opción 2 desactivada"
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        // Listener RadioGroup: muestra Toast con la opción seleccionada
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val texto = when (checkedId) {
                binding.radio1.id -> "Radio 1 seleccionado"
                binding.radio2.id -> "Radio 2 seleccionado"
                else -> "Nada seleccionado"
            }
            Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
        }

        // Guardar configuración
        binding.BotonGuardar.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean("modoOscuro", binding.checkModoOscuro.isChecked)
            editor.putBoolean("opcion1", binding.checkOpcion1.isChecked)
            editor.putBoolean("opcion2", binding.checkOpcion2.isChecked)
            editor.putString("numeroTelefono", binding.editNumero.text.toString())
            editor.putString("urlWeb", binding.editURL.text.toString())
            editor.putInt("radioSeleccionado", binding.radioGroup.checkedRadioButtonId)
            editor.apply()

            Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aplicarModoOscuro(activar: Boolean) {
        if (activar) {
            binding.root.setBackgroundColor(Color.BLACK)
            binding.textView5.setTextColor(Color.WHITE)
            binding.checkModoOscuro.setTextColor(Color.WHITE)
            binding.checkOpcion1.setTextColor(Color.WHITE)
            binding.checkOpcion2.setTextColor(Color.WHITE)
            binding.radio1.setTextColor(Color.WHITE)
            binding.radio2.setTextColor(Color.WHITE)
            binding.editNumero.setTextColor(Color.WHITE)
            binding.editURL.setTextColor(Color.WHITE)
        } else {
            binding.root.setBackgroundColor(Color.WHITE)
            binding.textView5.setTextColor(Color.BLACK)
            binding.checkModoOscuro.setTextColor(Color.BLACK)
            binding.checkOpcion1.setTextColor(Color.BLACK)
            binding.checkOpcion2.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio2.setTextColor(Color.BLACK)
            binding.editNumero.setTextColor(Color.BLACK)
            binding.editURL.setTextColor(Color.BLACK)
        }
    }
}
