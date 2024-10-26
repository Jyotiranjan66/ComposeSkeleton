package com.yudiz.testing.api

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yudiz.testing.R
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var rv: RecyclerView
    private lateinit var tvNoData: TextView
    private lateinit var retrofit: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.pb)
        rv = findViewById(R.id.rv_post)
        tvNoData = findViewById(R.id.tv_no_data)

        /*Timber.plant(Timber.DebugTree())
        Timber.e("TEST")*/

        setRetrofit()
        callApi()
    }

    private fun setRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl((application as App).baseUrl())
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .client(OkHttpProvider.getOkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

    private fun callApi() {
        progressBar.visibility = View.VISIBLE
        retrofit.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                progressBar.visibility = View.GONE

                response.body()?.let {
                    rv.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE

                    rv.adapter = PostAdapter(it)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                progressBar.visibility = View.GONE

                rv.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        })
    }

    private fun setUi() {

    }
}