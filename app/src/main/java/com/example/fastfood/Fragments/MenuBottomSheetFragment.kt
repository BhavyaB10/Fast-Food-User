package com.example.fastfood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastfood.FastDeliveryAdapter
import com.example.fastfood.FoodItems
import com.example.fastfood.MenuAdapter
import com.example.fastfood.MenuItem
import com.example.fastfood.R
import com.example.fastfood.databinding.FragmentHomeBinding
import com.example.fastfood.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var  binding:FragmentMenuBottomSheetBinding
    private lateinit var foodList: MutableList<MenuItem>
    private lateinit var database: DatabaseReference
    private lateinit var foodAdapter: MenuAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        // Initialize the Firebase database reference
        database = FirebaseDatabase.getInstance().reference.child("MenuItems")

        val menuFoodName = listOf("Burger","Sandwich","Momos","Item")
        val menuFoodPrice=listOf("$5","$7","$8","$9")
        val menuFoodImages = listOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner1,R.drawable.banner2)

        // Initialize the list and adapter
        foodList = mutableListOf()
        foodAdapter = MenuAdapter(foodList,menuFoodImages)


        binding.menuRV.layoutManager=LinearLayoutManager(requireContext())
        binding.menuRV.adapter=foodAdapter

        // Fetch food items from Firebase
        fetchFoodItems()

        return binding.root
    }

    private fun fetchFoodItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                for (dataSnapshot in snapshot.children) {
                    val foodItem = dataSnapshot.getValue(MenuItem::class.java)
                    foodItem?.let {
                        foodList.add(it)
                    }
                }
                foodAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }


}