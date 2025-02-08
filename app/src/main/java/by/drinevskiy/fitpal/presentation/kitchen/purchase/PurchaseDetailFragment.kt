package by.drinevskiy.fitpal.presentation.kitchen.purchase

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentKitchenBinding
import by.drinevskiy.fitpal.databinding.FragmentPurchaseDetailBinding
import by.drinevskiy.fitpal.presentation.kitchen.KitchenViewModel
import by.drinevskiy.fitpal.presentation.kitchen.PurchaseAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseDetailFragment : Fragment() {

    private var _binding: FragmentPurchaseDetailBinding? = null
    private val args by navArgs<PurchaseDetailFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: PurchaseDetailAdapter

    private val viewModel: KitchenViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // TODO: Use the ViewModel
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseDetailBinding.inflate(inflater, container, false)
        val root = binding.root
        viewModel.getPurchaseById(args.purchaseItem)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {

                    binding.text.setText(it.currentPurchase?.foods.toString())

                }
            }
        }

        return root
    }
}