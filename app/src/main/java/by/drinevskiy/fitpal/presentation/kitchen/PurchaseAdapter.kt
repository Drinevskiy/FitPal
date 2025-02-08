package by.drinevskiy.fitpal.presentation.kitchen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController

//import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.PurchaseListItemBinding
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import java.time.format.DateTimeFormatter


class PurchaseAdapter: RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {
    var data: List<PurchaseListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged() // Уведомляем адаптер об изменениях
        }
    class PurchaseViewHolder(val binding: PurchaseListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PurchaseListItemBinding.inflate(inflater, parent, false)
        return PurchaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val purchase = data[position]
        val context = holder.itemView.context
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        with(holder.binding){
            purchaseCostWeight.text = context.getString(R.string.purchase_cost_weight_format, purchase.cost, purchase.weight)
            purchaseDate.text = purchase.date.format(formatter)
            holder.itemView.setOnClickListener {
                val action = KitchenFragmentDirections.actionNavigationKitchenToPurchaseDetailFragment(purchase.id)
                it.findNavController().navigate(action)
            }
        }
    }
}