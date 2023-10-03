package MainFiles

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class MainActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_support)

        /*val signinbutton = findViewById<Button>(R.id.Button_Signin)
        signinbutton.setOnClickListener{
            val Intent = Intent(this,Login_screen::class.java)
            startActivity(Intent)
        }*/
    }
}