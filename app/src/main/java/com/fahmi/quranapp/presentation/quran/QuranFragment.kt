package com.fahmi.quranapp.presentation.quran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmi.quranapp.R
import com.fahmi.quranapp.adapter.ListSurahAdapter
import com.fahmi.quranapp.databinding.FragmentQuranBinding

class QuranFragment : Fragment() {

    private lateinit var binding: FragmentQuranBinding

    private val quranViewModel: QuranViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuranBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quranViewModel.getListSurah()
        val mAdapter = ListSurahAdapter()

//        Loading
        binding.progressBar.visibility = View.GONE

        quranViewModel.listSurah.observe(viewLifecycleOwner) { surah ->
            mAdapter.setData(surah.listSurah)
            binding.rvQuran.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

}