package com.example.fastfood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fastfood.FastDeliveryAdapter
import com.example.fastfood.FoodItems
import com.example.fastfood.PopularAdapter
import com.example.fastfood.PopularFoodItems
import com.example.fastfood.R
import com.example.fastfood.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var foodList: MutableList<FoodItems>
    private lateinit var foodList2: MutableList<PopularFoodItems>
    private lateinit var binding : FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var foodAdapter: FastDeliveryAdapter
    private lateinit var foodAdapter2: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)

        binding.textViewSellAll.setOnClickListener {
             val bottomSheetDialog=MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")

        }

        binding.textViewSellAllPopular.setOnClickListener {
            val bottomSheetDailog=PopularMenuBottiomSheetFragment()
            bottomSheetDailog.show(parentFragmentManager,"Test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList =ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2,ScaleTypes.FIT))

        val imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        // Initialize the Firebase database reference
        database = FirebaseDatabase.getInstance().reference.child("FoodItems")
        database2 = FirebaseDatabase.getInstance().reference.child("PopularFoodItems")


        val foodName = listOf("Burger","Sandwich","Momos","Item")
        val desc= listOf("Description 1","Description 2","Description 3","Description 4",)
        val Price = listOf("$5","$7","$8","$9")
        val foodImages = listOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner1,R.drawable.banner2)

        // Initialize the list and adapter
        foodList = mutableListOf()
        foodAdapter = FastDeliveryAdapter(foodList,foodImages)

        binding.fastDeliveryRecyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.fastDeliveryRecyclerView.adapter = foodAdapter

        // Fetch food items from Firebase
        fetchFoodItems()

        val popularFoodItemName = listOf("Burger","Noodles","Momos")
        val popularFoodImages = listOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner1)

        foodList2= mutableListOf()
        foodAdapter2 = PopularAdapter(foodList2,popularFoodImages)

        binding.popularRV.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.popularRV.adapter=foodAdapter2

        // Fetch food items from Firebase
        fetchPopularFoodItems()

    }

    private fun fetchPopularFoodItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList2.clear()
                for (dataSnapshot in snapshot.children) {
                    val foodItem = dataSnapshot.getValue(PopularFoodItems::class.java)
                    foodItem?.let {
                        foodList2.add(it)
                    }
                }
                foodAdapter2.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchFoodItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                for (dataSnapshot in snapshot.children) {
                    val foodItem = dataSnapshot.getValue(FoodItems::class.java)
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