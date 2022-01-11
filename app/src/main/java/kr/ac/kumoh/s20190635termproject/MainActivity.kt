package kr.ac.kumoh.s20190635termproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kumoh.s20190635termproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var gameVM: GameViewModel //저장되어있는 ViewModel 데이터를 가져옴
    private lateinit var layout: ActivityMainBinding //바인딩을 하여 main 레이아웃 적용
    private lateinit var game: GameAdapter //데이터를 리스트 형태로 보여주기 위한 어댑터 적용
    @SuppressLint("NotifyDataSetChanged")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layout.root)

        gameVM = ViewModelProvider(this).get(GameViewModel::class.java)
        game = GameAdapter(gameVM) {
                review->
            adapterOnClick(review)
        }

        layout.games.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = game
        }

        gameVM.getLiveData().observe(this, Observer<ArrayList<GameViewModel.Review>> {
            game.notifyDataSetChanged()
        })
        gameVM.getReviews()
    }


    //intent란 어플리케이션 구성요소 간에 작업 수행을 위한 정보를 전달하는 역할
    private fun adapterOnClick(review: GameViewModel.Review) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("title", review.title)
        intent.putExtra("image", review.image)
        intent.putExtra("memo", review.memo)
        startActivity(intent)
    }
}