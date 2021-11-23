package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import  com.udacity.shoestore.models.ShoeListViewModel

class ShoeDetailFragment : Fragment() {
    private var _binding: FragmentShoeDetailBinding? = null
    private val binding get() = _binding!!
    private val shoeListViewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoe_detail, container, false
        )
        binding.cancelShoeDetailButton.setOnClickListener { view ->
            closeKeyboard(view)
            findNavController().navigateUp()
        }
        binding.viewModel = shoeListViewModel

        binding.saveShoeDetailButton.setOnClickListener{ view ->
            closeKeyboard(view)
            onSaveButtonClick()
        }

        return binding.root
    }

    private fun closeKeyboard(view: View) {
        val imm = ContextCompat.getSystemService(view.context, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun onSaveButtonClick() {
        shoeListViewModel.createShoe(
            name = binding.shoeNameInput.text.toString(),
            size = binding.shoeSizeInput.text.toString(),
            company = binding.companyNameInput.text.toString(),
            description = binding.descriptionInput.text.toString()
        )
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}