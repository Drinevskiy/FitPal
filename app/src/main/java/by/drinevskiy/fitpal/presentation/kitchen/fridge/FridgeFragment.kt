package by.drinevskiy.fitpal.presentation.kitchen.fridge

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
import androidx.recyclerview.widget.LinearLayoutManager
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentFridgeBinding
import by.drinevskiy.fitpal.databinding.FragmentPurchaseDetailBinding
import by.drinevskiy.fitpal.domain.model.FridgeFoodItem
import by.drinevskiy.fitpal.presentation.profile.purchase.PurchaseDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FridgeFragment : Fragment() {

    private var _binding: FragmentFridgeBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: FridgeFoodAdapter

    private val viewModel: FridgeViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFridgeBinding.inflate(inflater, container, false)
        val root = binding.root
        adapter = FridgeFoodAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    Log.i("Fridge", it.toString())
                    adapter.data = it.fridgeFoodList as List<FridgeFoodItem>
                    Log.i("Fridge", adapter.data.toString())
                }
            }
        }
        binding.fridgeFoodRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.fridgeFoodRecycler.adapter = adapter
        return root
    }
}