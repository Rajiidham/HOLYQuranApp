package com.raji.quranapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raji.quranapp.databinding.ItemAyahBinding
import com.raji.quranapp.core.data.network.quran.AyahsItem
import com.raji.quranapp.core.data.network.quran.QuranEditionItem

class ListAyahAdapter : RecyclerView.Adapter<ListAyahAdapter.AyahViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: AyahsItem)
    }

    private val listAyah = ArrayList<AyahsItem>()
    private val listQuranEdition = ArrayList<QuranEditionItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(dataAyah: List<AyahsItem>, dataEdition: List<QuranEditionItem>) {
        if (dataAyah == null || dataEdition == null) return
        listAyah.clear()
        listAyah.addAll(dataAyah)
        listQuranEdition.clear()
        listQuranEdition.addAll(dataEdition)
    }

    class AyahViewHolder(val binding: ItemAyahBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyahViewHolder =
        AyahViewHolder(
            ItemAyahBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = listAyah.size

    override fun onBindViewHolder(holder: AyahViewHolder, position: Int) {
        val listAyah = listAyah[position]
        val quranAudio = listQuranEdition[1].listAyahs[position]
        val quranTranslateIndo = listQuranEdition[2].listAyahs[position]

        holder.binding.apply {
            itemNumberAyah.text = listAyah.numberInSurah.toString()
            itemAyah.text = listAyah.text
            itemTranslation.text = quranTranslateIndo.text

            this.root.setOnClickListener {
                quranAudio.let { data ->
                    onItemClickCallback?.onItemClicked(data)
                }
            }

        }
    }
}