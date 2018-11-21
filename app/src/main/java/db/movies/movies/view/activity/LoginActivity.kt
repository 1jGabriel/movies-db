package db.movies.movies.view.activity

import android.content.Intent
import android.support.v7.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import db.movies.movies.R
import kotlinx.android.synthetic.main.activity_tela_login.*
import setSafeOnClickListener

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_login)

        btLogar.setSafeOnClickListener {
            if (etLogin!!.text.toString() == "teste" && etSenha!!.text.toString() == "123") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                dialogErro()
            }
        }
    }

    fun dialogErro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dados incorretos")
        builder.setMessage("Usuario ou Senha inválidos!")

        builder.setPositiveButton("Ok") { dialogInterface, i -> }

        builder.show()
    }
}
