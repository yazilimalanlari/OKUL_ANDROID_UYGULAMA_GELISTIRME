package school.assignment

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var layout: ConstraintLayout
    private lateinit var profileImage: ImageView
    private lateinit var textFullName: TextView
    private lateinit var btnMyWorkExperience: Button
    private lateinit var info: ConstraintLayout
    private lateinit var myWorkExperience: ConstraintLayout

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        // Screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        // Selected elements
        layout = findViewById(R.id.layout)
        profileImage = findViewById(R.id.profileImage)
        textFullName = findViewById(R.id.textFullName)
        btnMyWorkExperience = findViewById(R.id.btnMyWorkExperience)
        info = findViewById(R.id.info)


        // Pre animation
        profileImage.translationY -= screenHeight
        textFullName.translationY -= screenHeight
        btnMyWorkExperience.translationY += screenHeight
        info.translationX -= screenWidth

        // MyWorkExperience
        myWorkExperience = ConstraintLayout(this)
        myWorkExperience.id = R.id.myWorkExperience
        myWorkExperience.setBackgroundColor(ContextCompat.getColor(this, R.color.colorModalBackground))
        myWorkExperience.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
        myWorkExperience.setPadding(50, 100, 50,25)

        var params = myWorkExperience.layoutParams as ConstraintLayout.LayoutParams
        params.leftToLeft = layout.id
        params.topToTop = layout.id


        layout.addView(myWorkExperience)
        myWorkExperience.translationY -= screenHeight

        // Close
        val close = ImageView(this)
        close.id = R.id.close
        close.setBackgroundResource(R.drawable.ic_baseline_close_24)
        close.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

        params = close.layoutParams as ConstraintLayout.LayoutParams
        params.topToTop = myWorkExperience.id
        params.rightToRight = myWorkExperience.id

        myWorkExperience.addView(close)

        close.setOnClickListener {
            SpringAnimation(myWorkExperience, DynamicAnimation.TRANSLATION_Y, -abs(screenHeight).toFloat()).apply {
                spring.stiffness = SpringForce.STIFFNESS_LOW
                start()
            }
        }

        // Title
        val title = TextView(this)
        title.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        title.setText(R.string.myWorkExperienceTitle)
        title.setTextColor(Color.WHITE)
        title.textSize = 25f
        title.typeface = ResourcesCompat.getFont(this, R.font.josefin_sans_bold)

        params = title.layoutParams as ConstraintLayout.LayoutParams
        params.leftToLeft = myWorkExperience.id
        params.topToTop = myWorkExperience.id
        params.rightToRight = myWorkExperience.id

        title.y = 100f
        myWorkExperience.addView(title)


        // Text
        val text = TextView(this)
        text.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        text.setText(R.string.textMyWorkExperience)
        text.setTextColor(Color.WHITE)
        text.textSize = 18f
        text.typeface = ResourcesCompat.getFont(this, R.font.josefin_sans_bold)

        params = text.layoutParams as ConstraintLayout.LayoutParams
        params.leftToLeft = myWorkExperience.id
        params.topToTop = myWorkExperience.id
        params.rightToRight = myWorkExperience.id

        text.y = 250f


        myWorkExperience.addView(text)

        // /MyWorkExperience

        Handler().postDelayed({
            load()
        }, 500)
    }

    private fun load() {
        loadAnimation()
        btnMyWorkExperience.setOnClickListener {
            SpringAnimation(myWorkExperience, DynamicAnimation.TRANSLATION_Y, 0f).apply {
                spring.stiffness = SpringForce.STIFFNESS_LOW
                start()
            }
        }
    }

    private fun loadAnimation() {
        SpringAnimation(profileImage, DynamicAnimation.TRANSLATION_Y, 0f).apply {
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }

        SpringAnimation(textFullName, DynamicAnimation.TRANSLATION_Y, 0f).apply {
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }

        SpringAnimation(btnMyWorkExperience, DynamicAnimation.TRANSLATION_Y, 0f).apply {
            spring.stiffness = SpringForce.STIFFNESS_HIGH
            start()
        }.addEndListener { _, _, _, _ ->
            ObjectAnimator.ofFloat(btnMyWorkExperience, "translationY", 25f).apply {
                duration = 1000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                start()
            }
        }

        SpringAnimation(info, DynamicAnimation.TRANSLATION_X, 0f).apply {
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }
    }
}