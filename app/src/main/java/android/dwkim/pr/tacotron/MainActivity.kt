package android.dwkim.pr.tacotron

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLayouts()
    }

    private fun setLayouts(){
        btn_main_play.setOnClickListener {
            PlayerUtil().run {
                getSoundFileAndPlay(
                    this@MainActivity,
                    et_main_input.text.toString()
                )
            }
        }
    }
}