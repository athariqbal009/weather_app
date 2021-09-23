package com.athar.android.weatherapp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.athar.android.data.Resource
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.databinding.FragmentHelpBinding
import com.athar.android.weatherapp.ui.MainActivity
import com.athar.android.weatherapp.vm.LocationViewModel

class HelpFragment : Fragment() {
    private lateinit var fragmentHelpBinding: FragmentHelpBinding
    private lateinit var viewModel: LocationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentHelpBinding = FragmentHelpBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        viewModel.getHelp()
        viewModel.helpData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    viewModel.progress.value = true
                }
                is Resource.Success -> {
                    response.data?.let {
                        fragmentHelpBinding.webView.loadUrl(it)
                        viewModel.progress.value = false
                    }
                }
                is Resource.Error -> {
                    viewModel.progress.value = false
                }
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
}