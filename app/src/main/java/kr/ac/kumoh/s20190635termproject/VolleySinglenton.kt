package kr.ac.kumoh.s20190635termproject

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class VolleySinglenton constructor(context: Context) { //어떤 클래스가 최초로 한번만 메모리를 할당하고 그 메모리에 객체를 만들어 사용하는 디자인

    /*Volley 라이브러리는 안드로이드 애플리케이션을 위한 네트워킹을
    보다 쉽고 빠르게 만들어주는 HTTP 라이브러리*/

    companion object {
        @Volatile
        private var INSTANCE: VolleySinglenton? = null
        fun getInstance(context: Context) = INSTANCE?: synchronized(this) {
            INSTANCE?: VolleySinglenton(context).also {
                INSTANCE = it
            }
        }
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, object: ImageLoader.ImageCache{
            private val cache = LruCache<String, Bitmap>(100)
            override fun getBitmap(url: String?): Bitmap? {
                return cache.get(url)
            }
            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                cache.put(url, bitmap)
            }
        })
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

}