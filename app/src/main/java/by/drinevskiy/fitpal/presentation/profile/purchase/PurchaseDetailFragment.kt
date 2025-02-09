package by.drinevskiy.fitpal.presentation.profile.purchase

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentPurchaseDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class PurchaseDetailFragment : Fragment() {

    private var _binding: FragmentPurchaseDetailBinding? = null
    private val args by navArgs<PurchaseDetailFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: PurchaseDetailAdapter

    private val viewModel: PurchaseViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseDetailBinding.inflate(inflater, container, false)
        val root = binding.root
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        adapter = PurchaseDetailAdapter()
        viewModel.getPurchaseById(args.purchaseItem)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
//                    Log.i("Purchase", it.currentPurchase.toString())
                    it.currentPurchase?.let{ item ->
                        adapter.data = item.foods
                        binding.textPurchaseCost.text = requireContext().getString(R.string.purchase_detail_cost_format, item.cost)
                        binding.textPurchaseDate.text = item.date.format(formatter)
                    }
                }
            }
        }
        binding.purchaseDetailRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.purchaseDetailRecycler.adapter = adapter
        return root
    }
}