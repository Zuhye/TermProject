package kr.ac.kumoh.s20190635termproject

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class GameViewModel(application: Application): AndroidViewModel(application) {
    data class Review(var id: Int, var title: String, var singer: String, var image: String, var memo: String)

    private val list = ArrayList<Review>()
    private val liveData = MutableLiveData<ArrayList<Review>>()
    private var volley = VolleySinglenton.getInstance(application).requestQueue
    private val loader = VolleySinglenton.getInstance(application).imageLoader

    companion object {
        const val TAG = "GameRequest"
        const val SERVER = "https://expresssongdb-hkobe.run.goorm.io/"
    }

    init {
        liveData.value = list
    }

    fun getLiveData() = liveData
    fun getImageLoader() = loader
    fun getImageUrl(i: Int): String = "$SERVER/cover/" + URLEncoder.encode(list[i].image, "utf-8")
    fun getReview(i: Int) = list[i]
    fun getSize() = list.size
    fun getReviews() {
        val url = SERVER
        val request = JsonArrayRequest(Request.Method.GET, url, null, {
            list.clear()
            addReviews(it)
            liveData.value = list
        }, {
            Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
        })
        request.tag = TAG
        volley.add(request)
    }

    private fun addReviews(a: JSONArray) {
        for (i in 0 until a.length()) {
            val r = a[i] as JSONObject
            list.add(Review(r.getInt("id"), r.getString("title"), r.getString("singer"),
            r.getString("image"), r.getString("memo")))
        }
    }

    override fun onCleared() {
        super.onCleared()
        volley.cancelAll(TAG)
    }

}