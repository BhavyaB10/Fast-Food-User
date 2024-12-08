package com.example.fastfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastfood.databinding.MenuItemBinding

class PopularMenuAdapter(
    private val menuItems: List<MenuItem>,
    private val menuImage: List<Int>
) : RecyclerView.Adapter<PopularMenuAdapter.PopularMenuViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PopularMenuViewHolder, position: Int) {
        val foodItem = menuItems[position]
        val foodImage = if (position < menuImage.size) menuImage[position] else R.drawable.banner1
        holder.bind(foodItem,foodImage)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    inner class PopularMenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imagesFood =binding.imageViewFood
        fun bind(menuItem: MenuItem, imageFood:Int) {
               binding.tvFoodName.text = menuItem.name
               binding.tvPrice.text = menuItem.price
              imagesFood.setImageResource(imageFood)


        }

    }
}