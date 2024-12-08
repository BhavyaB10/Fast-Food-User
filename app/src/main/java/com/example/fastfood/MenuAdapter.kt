package com.example.fastfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastfood.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: MutableList<MenuItem>,
    private val menuImage: List<Int>
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val foodItem = menuItems[position]
        val foodImage = if (position < menuImage.size) menuImage[position] else R.drawable.banner1
        holder.bind(foodItem,foodImage)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
    class MenuViewHolder(private val binding: MenuItemBinding) :RecyclerView.ViewHolder(binding.root)
    {
        private val imagesFood =binding.imageViewFood

        fun bind(menuItem: MenuItem , foodImage:Int) {
            binding.tvFoodName.text = menuItem.name
            binding.tvPrice.text = menuItem.price

            imagesFood.setImageResource(foodImage)

        }
    }

}