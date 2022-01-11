package kr.ac.kumoh.s20190635termproject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20190635termproject.databinding.ActivityGameBinding
import java.net.URLEncoder

class GameActivity: AppCompatActivity() {

    private lateinit var layout: ActivityGameBinding
    private lateinit var gameVM: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = ActivityGameBinding.inflate(layoutInflater)
        setContentView(layout.root)

        gameVM = ViewModelProvider(this).get(GameViewModel::class.java)
        layout.title.text = intent.getStringExtra("title")

        val url = "${GameViewModel.SERVER}/cover/" + URLEncoder.encode(intent.getStringExtra("image"),"utf-8")
        layout.cover.setImageUrl(url, gameVM.getImageLoader())
        layout.review.text = intent.getStringExtra("memo")

    }
}