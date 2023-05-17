package com.binar.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.challenge6.model.MoviePopular
import com.binar.challenge6.network.APIMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.binar.challenge6.model.Result

class ViewModelMovie(): ViewModel() {
    lateinit var liveDataMovie: MutableLiveData<List<Result>>

    init {
        liveDataMovie = MutableLiveData()
    }

    fun getMovie() {
        APIMovie.instance.getPopularMovie().enqueue(object : Callback<MoviePopular<Result>> {
            override fun onResponse(call: Call<MoviePopular<Result>>, response: Response<MoviePopular<Result>>) {
                if (response.isSuccessful){
                    val movieresponse = response.body()
                    liveDataMovie.postValue(movieresponse?.results)

                }else{
                    liveDataMovie.value = emptyList()
                }
            }

            override fun onFailure(call: Call<MoviePopular<Result>>, t: Throwable) {
                liveDataMovie.value = emptyList()
            }

        })


    }
}