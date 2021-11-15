package com.udacity.shoestore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.models.ShoeListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ShoeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeListFragment : Fragment() {
    private var _binding: FragmentShoeListBinding? = null
    private val binding get() = _binding!!
    private val shoeListViewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoe_list, container, false
        )
        binding.addShoeButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_shoeListFragment_to_shoeDetailFragment
            )
        )

        return binding.root
    }
    


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        shoeListViewModel.shoeList.observe(viewLifecycleOwner) { shoes ->
            Log.d("Shoes", shoes.toString())
        }
    }
}