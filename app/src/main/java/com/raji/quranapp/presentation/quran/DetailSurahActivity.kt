package com.raji.quranapp.presentation.quran

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.raji.quranapp.R
import com.raji.quranapp.databinding.ActivityDetailSurahBinding
import com.raji.quranapp.databinding.CustomViewAlertdialogBinding
import com.raji.quranapp.adapter.ListAyahAdapter
import com.raji.quranapp.core.data.network.quran.AyahsItem
import com.raji.quranapp.core.data.network.quran.SurahItem
import java.lang.Exception

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding

    private val viewModel: QuranViewModel by viewModels()

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private var _mediaPlayer: MediaPlayer? = null

    private val mediaPlayer get() = _mediaPlayer as MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail Surah"
        }

        val surah = intent.getParcelableExtra<SurahItem>(EXTRA_DATA)



        binding.apply {
            tvDetailAyah.text = "${surah?.revelationType} - ${surah?.numberOfAyahs} Ayahs"
            tvDetailName.text = surah?.name
            tvDetailSurah.text = surah?.englishName
            tvDetailNameTranslation.text = surah?.englishNameTranslation
        }

        surah?.number?.let { viewModel.getListAyah(it) }

        val mAdapter = ListAyahAdapter()
        mAdapter.setOnItemClickCallback(object : ListAyahAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AyahsItem) {
                if (surah != null) {
                    showCustomAlertDialog(data, surah)
                }
            }
        })


        viewModel.listAyah.observe(this@DetailSurahActivity) { ayah ->
            binding.rvSurah.apply {
                mAdapter.setData(ayah.quranEdition.get(0).listAyahs, ayah.quranEdition)
                adapter = mAdapter
                layoutManager = LinearLayoutManager(this@DetailSurahActivity)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCustomAlertDialog(dataAudio: AyahsItem, surah: SurahItem) {
        val progressDialog = AlertDialog.Builder(this@DetailSurahActivity).create()
        val view = CustomViewAlertdialogBinding.inflate(layoutInflater)
        progressDialog.setView(view.root)
        view.apply {
            tvSurah.text = surah.englishName
            tvName.text = surah.name
            tvNumberAyah.text = "Ayah ${dataAudio.numberInSurah}"
        }

        progressDialog.setOnShowListener {
            view.btnPlay.visibility = View.GONE
            view.loadingView.visibility = View.VISIBLE
            view.loadingView.isEnabled = false
            dataAudio.audio?.let { sound ->
                loadAudio(sound, view, progressDialog)
            }
        }

        progressDialog.setOnDismissListener {
            binding.progressBar.visibility = View.GONE
        }

        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    private fun loadAudio(
        sound: String,
        view: CustomViewAlertdialogBinding,
        progressDialog: AlertDialog
    ) {
        _mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                setDataSource(sound)
                prepareAsync()
                setOnPreparedListener {
                    view.btnPlay.isEnabled = true
                    view.loadingView.visibility = View.GONE
                    view.btnPlay.visibility = View.VISIBLE
                    view.btnPlay.text = getString(R.string.play_audio)
                    view.btnPlay.setOnClickListener { btn ->
                        btn.isEnabled = false
                        view.btnPlay.text = getString(R.string.playing_audio)
                        start()
                    }

                    view.btnCancel.setOnClickListener {
                        stop()
                        progressDialog.dismiss()
                    }

                    // Menutup dialog ketika audio selesai diputar
                    setOnCompletionListener {
                        progressDialog.dismiss()
                    }

                    binding.progressBar.visibility = View.GONE
                    binding.root.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                progressDialog.dismiss()
            }
        }

    }


}