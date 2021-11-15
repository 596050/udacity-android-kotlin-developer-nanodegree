package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import  com.udacity.shoestore.models.ShoeListViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [ShoeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        binding.cancelShoeDetailButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_shoeDetailFragment_to_shoeListFragment
            )
        )
        binding.viewModel = shoeListViewModel

        binding.saveShoeDetailButton.setOnClickListener{
            onSaveButtonClick()
        }

        return binding.root

    }

    fun onSaveButtonClick() {
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