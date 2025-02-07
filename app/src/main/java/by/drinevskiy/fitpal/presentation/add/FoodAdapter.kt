package by.drinevskiy.fitpal.presentation.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FoodListItemBinding
import by.drinevskiy.fitpal.domain.model.FoodListItem

class FoodAdapter(private val onLikeClick: (FoodListItem) -> Unit) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    var data: List<FoodListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class FoodViewHolder(val binding: FoodListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodListItemBinding.inflate(inflater, parent, false)

        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = data[position]
        val context = holder.itemView.context
        with(holder.binding){
            val color = if (food.isLiked) R.color.md_theme_errorContainer_mediumContrast else R.color.md_theme_outline
            foodName.text = context.getString(R.string.food_name_format, food.name, food.weight)
            foodCpfc.text = context.getString(R.string.food_cpfc_format, food.ccal, food.protein, food.fat, food.carbons)
            imageViewLike.setColorFilter(
                ContextCompat.getColor(context, color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            imageViewLike.setOnClickListener {
                onLikeClick(food)
            }
        }
    }
}