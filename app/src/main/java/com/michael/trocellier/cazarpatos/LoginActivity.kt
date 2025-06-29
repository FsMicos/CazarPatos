package com.michael.trocellier.cazarpatos
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    //lateinit var manejadorArchivo: FileHandler
    lateinit var manejadorArchivo: FileHandler
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var buttonNewUser: Button
    lateinit var mediaPlayer: MediaPlayer
    lateinit var checkBoxRecordarme: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inicialización de variables
        //manejadorArchivo = SharedPreferencesManager(this)
        manejadorArchivo = EncriptedSharedPreferencesManager(this)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonNewUser = findViewById(R.id.buttonNewUser)
        checkBoxRecordarme = findViewById(R.id.checkBoxRecordarme)

        LeerDatosDePreferencias()

        // Inicialización del MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.title_screen)
        mediaPlayer.start()

        // Eventos clic
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val clave = editTextPassword.text.toString()

            // Validaciones de datos requeridos y formatos
            if (!validateRequiredData())
                return@setOnClickListener
            GuardarDatosEnPreferencias()
            // Si pasa validación de datos requeridos, ir a pantalla principal
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_LOGIN, email)
            startActivity(intent)
            //finish()
        }

        buttonNewUser.setOnClickListener {
            // Acción para nuevo usuario
        }
        mediaPlayer=MediaPlayer.create(this, R.raw.title_screen)
        mediaPlayer.start()
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */
    }
    private fun LeerDatosDePreferencias() {
        var datoLeido: Pair<String, String>

        // Usando SharedPreferencesManager normal
        manejadorArchivo = SharedPreferencesManager(this)
        datoLeido = manejadorArchivo.ReadInformation()
        Log.d("TAG", "SharedPreferencesManager " + datoLeido.toList().toString())

        // Usando EncryptedSharedPreferencesManager para datos sensibles
        manejadorArchivo = EncriptedSharedPreferencesManager(this)
        datoLeido = manejadorArchivo.ReadInformation()
        Log.d("TAG", "EncryptedSharedPreferencesManager " + datoLeido.toList().toString())

        // Usando FileInternalManager para archivos internos
        manejadorArchivo = FileInternalManager(this)
        datoLeido = manejadorArchivo.ReadInformation()
        Log.d("TAG", "FileInternalManager " + datoLeido.toList().toString())

        // Aplicar los datos leídos a la UI (puedes elegir cuál usar como fuente principal)
        // En este ejemplo uso FileInternalManager como fuente principal
        if (datoLeido.first.isNotEmpty()) {
            checkBoxRecordarme.isChecked = true
            editTextEmail.setText(datoLeido.first)
            editTextPassword.setText(datoLeido.second)
        }
    }

    private fun GuardarDatosEnPreferencias() {
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()
        val listadoAGrabar: Pair<String, String>

        if (checkBoxRecordarme.isChecked) {
            listadoAGrabar = email to clave
        } else {
            listadoAGrabar = "" to ""
        }

        // Guardar con SharedPreferencesManager normal
        manejadorArchivo = SharedPreferencesManager(this)
        manejadorArchivo.SaveInformation(listadoAGrabar)

        // Guardar con EncryptedSharedPreferencesManager (datos sensibles)
        manejadorArchivo = EncriptedSharedPreferencesManager(this)
        manejadorArchivo.SaveInformation(listadoAGrabar)

        // Guardar con FileInternalManager (archivos internos)
        manejadorArchivo = FileInternalManager(this)
        manejadorArchivo.SaveInformation(listadoAGrabar)
    }

    private fun validateRequiredData(): Boolean {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (email.isEmpty()) {
            editTextEmail.error = getString(R.string.error_email_required)
            editTextEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            editTextPassword.error = getString(R.string.error_password_required)
            editTextPassword.requestFocus()
            return false
        }

        if (password.length < 3) {
            editTextPassword.error = getString(R.string.error_password_min_length)
            editTextPassword.requestFocus()
            return false
        }

        return true
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_LOGIN = "extra_login"
    }
}
