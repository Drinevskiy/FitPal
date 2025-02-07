package by.drinevskiy.fitpal.presentation.kitchen

import android.graphics.Rect
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentAddBinding
import by.drinevskiy.fitpal.databinding.FragmentKitchenBinding
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import by.drinevskiy.fitpal.presentation.add.AddViewModel
import by.drinevskiy.fitpal.presentation.add.FoodAdapter
import by.drinevskiy.fitpal.presentation.utils.launchUntilPaused
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class KitchenFragment : Fragment() {

    private var _binding: FragmentKitchenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: PurchaseAdapter
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
        val root: View = binding.root

        adapter = PurchaseAdapter()
        lifecycleScope.launchUntilPaused(this){
            viewModel.uiState.collect{
                adapter.data = it.purchaseList
            }
        }
        binding.purchaseRecycler.layoutManager = LinearLayoutManager(requireContext())
//        binding.purchaseRecycler.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL )
        binding.purchaseRecycler.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(binding.purchaseRecycler.context, LinearLayoutManager.VERTICAL)
        binding.purchaseRecycler.addItemDecoration(dividerItemDecoration)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // Получаем позицию элемента
        val column = position % spanCount // Определяем, в каком столбце находится элемент

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // Отступ слева
            outRect.right = (column + 1) * spacing / spanCount // Отступ справа

            if (position < spanCount) { // Отступ сверху для первой строки
                outRect.top = spacing
            }
            outRect.bottom = spacing // Отступ снизу
        } else {
            outRect.left = column * spacing / spanCount // Отступ слева
            outRect.right = spacing - (column + 1) * spacing / spanCount // Отступ справа
            if (position >= spanCount) {
                outRect.top = spacing // Отступ сверху для остальных элементов
            }
        }
    }
}