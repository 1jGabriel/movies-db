package db.movies.movies.view.activity

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import db.movies.movies.R
import kotlinx.android.synthetic.main.activity_autores.*
import setSafeOnClickListener

class AutoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autores)


        emailJoao.setSafeOnClickListener {
            sendEmail("gsantana152@gmail.com")
        }

        emailGerson.setSafeOnClickListener {
            sendEmail("gerson.jr_@hotmail.com")
        }

        emailSergio.setSafeOnClickListener {
            sendEmail("serginhoaquiles@gmail.com")
        }

        emailVitor.setSafeOnClickListener {
            sendEmail("vitorsimoes09@hotmail.com")
        }
    }


    fun sendEmail(email : String){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback")
        if (intent.resolveActivity(this.packageManager) != null) {
            startActivity(intent)
        }
    }
}
