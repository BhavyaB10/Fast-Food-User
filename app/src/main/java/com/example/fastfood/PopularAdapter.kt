package com.example.fastfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastfood.databinding.PopulerItemBinding

class PopularAdapter(private val items:List<PopularFoodItems>,private val image :List<Int>):RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopulerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val images= image[position]

        holder.bind(item,images)
    }

    override fun getItemCount(): Int {
       return items.size
    }
    class PopularViewHolder(private val binding: PopulerItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val imagesFood = binding.imageViewFood
        fun bind(item: PopularFoodItems, images: Int) {
            binding.textViewFoodName.text = item.name

            imagesFood.setImageResource(images)

        }

    }
}