package by.drinevskiy.fitpal.presentation.add

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.drinevskiy.fitpal.domain.model.FoodListItem

class SwipeToDeleteCallback(private val onLeftSwipe: (Int)->Unit) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        viewHolder.adapterPosition
        onLeftSwipe(viewHolder.adapterPosition)
    }

}