package com.example.fastfood

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddToCartActivity : AppCompatActivity() {
    private var quantity = 1 // Default quantity
    private val cart = mutableListOf<Pair<String, Int>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        val productName: TextView = findViewById(R.id.foodName)
        val quantityText: TextView = findViewById(R.id.quantityText)
        val minusButton: ImageView = findViewById(R.id.minusButton)
        val plusButton: ImageView = findViewById(R.id.plusButton)
        val addToCartButton: Button = findViewById(R.id.addToCartButton)

        // Increment quantity
        plusButton.setOnClickListener {
            quantity++
            quantityText.text = quantity.toString()
        }

        // Decrement quantity (ensure it's at least 1)
        minusButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityText.text = quantity.toString()
            } else {
                Toast.makeText(this, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show()
            }
        }

        // Add to cart
        addToCartButton.setOnClickListener {
            val product = productName.text.toString()
            addToCart(product, quantity)
        }

    }

    private fun addToCart(product: String, quantity: Int) {
        cart.add(Pair(product, quantity)) // Add product and quantity to the cart
        Toast.makeText(this, "$quantity x $product added to cart!", Toast.LENGTH_SHORT).show()
    }
}