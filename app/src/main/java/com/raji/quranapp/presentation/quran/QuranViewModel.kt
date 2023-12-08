package com.raji.quranapp.presentation.quran

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raji.quranapp.core.data.network.ApiConfig
import com.raji.quranapp.core.data.network.quran.AyahResponse
import com.raji.quranapp.core.data.network.quran.SurahResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuranViewModel : ViewModel() {

    private var _listSurah = MutableLiveData<SurahResponse>()
    val listSurah: LiveData<SurahResponse> = _listSurah

    private var _listAyah = MutableLiveData<AyahResponse>()
    val listAyah: LiveData<AyahResponse> = _listAyah


    fun getListSurah() {
        ApiConfig.getQuranService.getListSurah().enqueue(object : Callback<SurahResponse> {
            override fun onResponse(
                call: Call<SurahResponse>,
                response: Response<SurahResponse>
            ) {
                if (response.isSuccessful) {
                    _listSurah.postValue(response.body())
                } else Log.e(
                    "QuranViewModel",
                    "onResponse: Call error with Http status code " + response.code()
                )
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                Log.e(
                    "QuranViewModel",
                    "onResponse: Call error with Http status code " + t.localizedMessage
                )
            }
        })
    }

    fun getListAyah(number: Int) {
        ApiConfig.getQuranService.getDetailSurah(number).enqueue(object : Callback<AyahResponse> {
            override fun onResponse(call: Call<AyahResponse>, response: Response<AyahResponse>) {
                if (response.isSuccessful) {
                    _listAyah.postValue(response.body())
                } else Log.e(
                    "QuranViewModel",
                    "onResponse: Call error with Http status code " + response.code()
                )
            }

            override fun onFailure(call: Call<AyahResponse>, t: Throwable) {
                Log.e(
                    "QuranViewModel",
                    "onResponse: Call error with Http status code " + t.localizedMessage
                )
            }
        })
    }
}