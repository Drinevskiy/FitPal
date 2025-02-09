package by.drinevskiy.fitpal.presentation.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentAddBinding
import by.drinevskiy.fitpal.databinding.FragmentProfileBinding
import by.drinevskiy.fitpal.presentation.add.AddViewModel
import by.drinevskiy.fitpal.presentation.add.FoodAdapter
import by.drinevskiy.fitpal.presentation.profile.purchase.PurchaseFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private lateinit var adapter: FoodAdapter
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root = binding.root
        binding.button.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToNavigationPurchase()
            it.findNavController().navigate(action)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}