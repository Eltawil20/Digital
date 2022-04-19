package sa.digo.digital.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_about_company.*

import sa.digo.digital.ui.auth.LoginActivity
import sa.digo.digital.ui.home.HomeActivity
import sa.digo.digital.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

// load img splash screen
        Glide.with(applicationContext).load(R.drawable.splash_background).into(img_background)
        Glide.with(applicationContext).load(R.drawable.white_logo).into(img_splash_logo)
// animation splash screen
        img_background.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_splash_bottom))
        img_splash_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_anim_logo))



        Handler().postDelayed(Runnable {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
//            startActivity(Intent(this, LoginActivity::class.java))
//            startActivity(Intent(this, ServiceRequestActivity::class.java))

        }, 1500)

    }
}