package english.words

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.room.Room
import androidx.room.RoomDatabase
import english.words.database.AppDatabase
import english.words.database.User

const val MODE_LOGIN = 0
const val MODE_REGISTER = 1

class SessionActivity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var emailText: TextView
    private lateinit var email: EditText
    private lateinit var usernameText: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var changeMode: TextView
    private lateinit var button: Button
    private lateinit var wrapper: ConstraintLayout
    private var mode = MODE_LOGIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        title = findViewById(R.id.title)
        emailText = findViewById(R.id.emailText)
        email = findViewById(R.id.email)
        usernameText = findViewById(R.id.usernameText)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        changeMode = findViewById(R.id.changeMode)
        button = findViewById(R.id.button)
        wrapper = findViewById(R.id.wrapper)

        wrapper.translationY -= 50f

        changeMode.setOnClickListener {
            changeMode()
        }

        button.setOnClickListener {
            if (mode == MODE_LOGIN)
                signIn()
            else
                signUp()
        }
    }

    private fun changeMode() {
        mode = if (mode == MODE_LOGIN) {
            title.setText(R.string.sign_up)
            changeMode.setText(R.string.register_mode)
            button.setText(R.string.register)

            SpringAnimation(wrapper, DynamicAnimation.TRANSLATION_Y, 50f).start()

            emailText.visibility = TextView.VISIBLE
            email.visibility = EditText.VISIBLE

            MODE_REGISTER
        } else {
            title.setText(R.string.sign_in)
            changeMode.setText(R.string.login_mode)
            button.setText(R.string.login)

            SpringAnimation(wrapper, DynamicAnimation.TRANSLATION_Y, -50f).start()

            emailText.visibility = TextView.INVISIBLE
            email.visibility = EditText.INVISIBLE

            MODE_LOGIN
        }
    }

    private fun signIn() {
        val iUsername = username.text.toString()
        val iPassword = password.text.toString()

        if (iUsername.isEmpty() || iPassword.isEmpty()) {
            Toast.makeText(applicationContext, "Lütfen tüm alanları doldurun.", Toast.LENGTH_LONG).show()
        } else {
            val db = Room.databaseBuilder(
                this,
                AppDatabase::class.java, "app"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            val user = db.userDao().getUser(iUsername)

            if (user == null || user.password != iPassword) {
                Toast.makeText(applicationContext, "Hatalı kullanıcı adı veya şifre.", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun signUp() {
        val iEmail = email.text.toString()
        val iUsername = username.text.toString()
        val iPassword = password.text.toString()

        if (iEmail.isEmpty() || iUsername.isEmpty() || iPassword.isEmpty()) {
            Toast.makeText(applicationContext, "Lütfen tüm alanları doldurun.", Toast.LENGTH_LONG).show()
        } else {
            val db = Room.databaseBuilder(
                this,
                AppDatabase::class.java, "app"
            ).allowMainThreadQueries().build()
            val result = db.userDao().add(User(email=iEmail, username=iUsername, password=iPassword))
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}