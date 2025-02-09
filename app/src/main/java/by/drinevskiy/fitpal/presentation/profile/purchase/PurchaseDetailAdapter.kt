package by.drinevskiy.fitpal.presentation.profile.purchase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentPurchaseDetailBinding
import by.drinevskiy.fitpal.databinding.PurchaseDetailCardBinding
import by.drinevskiy.fitpal.databinding.PurchaseListItemBinding
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseDetailListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem

class PurchaseDetailAdapter: RecyclerView.Adapter<PurchaseDetailAdapter.PurchaseViewHolder>() {
    var data = listOf<PurchaseDetailListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class PurchaseViewHolder(val binding: PurchaseDetailCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val inftaler = LayoutInflater.from(parent.context)
        val binding = PurchaseDetailCardBinding.inflate(inftaler, parent, false)
        return PurchaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val purchaseDetailItem = data[position]
        val context = holder.itemView.context
        with(holder.binding){
            textFoodNameCost.text = context.getString(R.string.purchase_detail_name_cost_format, purchaseDetailItem.name, purchaseDetailItem.cost)
            textWeightValue.text = context.getString(R.string.purchase_detail_weight_format, purchaseDetailItem.weight)
            textEnergyValue.text = context.getString(R.string.purchase_detail_energy_format, purchaseDetailItem.kcal100g, purchaseDetailItem.kcal)
            textProteinValue.text = context.getString(R.string.purchase_detail_pfc_format, purchaseDetailItem.protein100g, purchaseDetailItem.protein)
            textFatValue.text = context.getString(R.string.purchase_detail_pfc_format, purchaseDetailItem.fat100g, purchaseDetailItem.fat)
            textCarbonValue.text = context.getString(R.string.purchase_detail_pfc_format, purchaseDetailItem.carbon100g, purchaseDetailItem.carbon)
        }
    }
}