package by.drinevskiy.fitpal.presentation.profile.purchase

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.drinevskiy.fitpal.databinding.FragmentPurchaseBinding
import by.drinevskiy.fitpal.presentation.utils.launchUntilPaused
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchaseFragment : Fragment() {

    private var _binding: FragmentPurchaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: PurchaseAdapter
    private val viewModel: PurchaseViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = PurchaseAdapter()
        lifecycleScope.launchUntilPaused(this){
            viewModel.uiState.collect{
                adapter.data = it.purchaseList
            }
        }
        binding.purchaseRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.purchaseRecycler.adapter = adapter

//        val dividerItemDecoration = DividerItemDecoration(binding.purchaseRecycler.context, LinearLayoutManager.VERTICAL)
//        binding.purchaseRecycler.addItemDecoration(dividerItemDecoration)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {
//
//    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        val position = parent.getChildAdapterPosition(view) // Получаем позицию элемента
//        val column = position % spanCount // Определяем, в каком столбце находится элемент
//
//        if (includeEdge) {
//            outRect.left = spacing - column * spacing / spanCount // Отступ слева
//            outRect.right = (column + 1) * spacing / spanCount // Отступ справа
//
//            if (position < spanCount) { // Отступ сверху для первой строки
//                outRect.top = spacing
//            }
//            outRect.bottom = spacing // Отступ снизу
//        } else {
//            outRect.left = column * spacing / spanCount // Отступ слева
//            outRect.right = spacing - (column + 1) * spacing / spanCount // Отступ справа
//            if (position >= spanCount) {
//                outRect.top = spacing // Отступ сверху для остальных элементов
//            }
//        }
//    }
//}