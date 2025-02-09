package by.drinevskiy.fitpal.presentation.kitchen

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentFridgeBinding
import by.drinevskiy.fitpal.databinding.FragmentKitchenBinding
import by.drinevskiy.fitpal.presentation.profile.ProfileFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitchenFragment : Fragment() {

    private var _binding: FragmentKitchenBinding? = null
    private val binding get() = _binding!!

//    private lateinit var adapter:
    private val viewModel: KitchenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKitchenBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.fabAddDish.setOnClickListener {
            val action = KitchenFragmentDirections.actionNavigationKitchenToFridgeFragment()
            it.findNavController().navigate(action)
        }

        return root
    }
}