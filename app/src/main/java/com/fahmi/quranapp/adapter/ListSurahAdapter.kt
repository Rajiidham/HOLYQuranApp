package com.fahmi.quranapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahmi.quranapp.core.data.network.quran.SurahItem
import com.fahmi.quranapp.databinding.ItemSurahBinding
import com.fahmi.quranapp.presentation.quran.DetailSurahActivity

class ListSurahAdapter : RecyclerView.Adapter<ListSurahAdapter.SurahViewHolder>() {

    val listSurah = ArrayList<SurahItem>()

    fun setData(list: List<SurahItem>) {
        if (list == null) return
        listSurah.clear()
        listSurah.addAll(list)
    }

    class SurahViewHolder(val binding: ItemSurahBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder =
        SurahViewHolder(
            ItemSurahBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = listSurah.size

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val data = listSurah[position]

        holder.binding.apply {
            tvSurah.text = data.englishName
            val resultOfAyah = "${data.revelationType} - ${data.numberOfAyahs} Ayahs"
            tvAyah.text = resultOfAyah
            tvName.text = data.name
            tvNumber.text = data.number.toString()

            this.root.setOnClickListener {
                val intent = Intent(it.context, DetailSurahActivity::class.java)
                intent.putExtra(DetailSurahActivity.EXTRA_DATA, data)
                it.context.startActivity(intent)
            }
        }
    }
}