package MainFiles

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class Create_Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_createaccount)

        val CAback = findViewById<ImageButton>(R.id.Backbutton_createaccount)
        CAback.setOnClickListener {
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }
        val loginhyper = findViewById<TextView>(R.id.Login_text)
        loginhyper.setOnClickListener {
            val Intent = Intent(this, Login_screen::class.java)
            startActivity(Intent)
        }
    }
}