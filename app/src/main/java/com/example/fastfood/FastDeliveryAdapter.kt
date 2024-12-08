package com.example.fastfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastfood.databinding.FastetDeliveryItemBinding

class FastDeliveryAdapter(private val items: List<FoodItems> , private  val image :List<Int>) : RecyclerView.Adapter<FastDeliveryAdapter.FastDeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastDeliveryViewHolder {
        return FastDeliveryViewHolder(FastetDeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FastDeliveryViewHolder, position: Int) {
        val foodItem = items[position]
        val image=image[position]
        holder.bind(foodItem,image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FastDeliveryViewHolder(private val binding: FastetDeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val imagesFood =binding.imageViewFood
        fun bind(foodItem: FoodItems, images:Int) {
            // Bind the data to the views
            binding.textViewFoodName.text = foodItem.name
            binding.textViewFoodDescription.text = foodItem.description
            binding.textViewFoodPrice.text = foodItem.price

            imagesFood.setImageResource(images)

//            // Set image using Glide if the image URL is available
//            if (foodItem.image.isNotEmpty()) {
//                Glide.with(binding.root.context)
//                    .load(foodItem.image) // If it's a URL, or you can load from a drawable if it's a resource ID
//                    .into(binding.imageViewFood)
//            }
        }
    }
}