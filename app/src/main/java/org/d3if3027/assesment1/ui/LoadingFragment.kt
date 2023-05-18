package org.d3if3027.assesment1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.databinding.LoadingLayoutBinding


class LoadingFragment : Fragment(R.layout.loading_layout) {
    private lateinit var binding: LoadingLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoadingLayoutBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.masukButton.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_loadingFragment_to_hitungFragment
            )
        }
    }
}