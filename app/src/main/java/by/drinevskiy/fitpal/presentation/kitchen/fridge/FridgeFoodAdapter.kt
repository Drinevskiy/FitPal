package by.drinevskiy.fitpal.presentation.kitchen.fridge

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentFridgeBinding
import by.drinevskiy.fitpal.databinding.FridgeFoodItemBinding
import by.drinevskiy.fitpal.domain.model.FridgeFoodItem

class FridgeFoodAdapter: RecyclerView.Adapter<FridgeFoodAdapter.FridgeFoodHolder>() {
    var data = emptyList<FridgeFoodItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class FridgeFoodHolder(val binding: FridgeFoodItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeFoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FridgeFoodItemBinding.inflate(inflater, parent, false)
        return FridgeFoodHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FridgeFoodHolder, position: Int) {
        val fridgeFood = data[position]
        val context = holder.itemView.context
        Log.i("Fridge", fridgeFood.toString())
        with(holder.binding){
            fridgeFoodName.text = context.getString(R.string.food_name_format, fridgeFood.name, fridgeFood.weight)
            fridgeFoodCpfc.text = context.getString(R.string.food_cpfc_format, fridgeFood.kcal, fridgeFood.protein, fridgeFood.fat, fridgeFood.carbons)
        }
    }
}