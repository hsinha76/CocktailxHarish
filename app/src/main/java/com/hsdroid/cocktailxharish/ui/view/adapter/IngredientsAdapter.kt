package com.hsdroid.cocktailxharish.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hsdroid.cocktailxharish.databinding.ItemIngredientsBinding

class IngredientsAdapter(private val ingredients: List<Pair<String, String>>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): IngredientsAdapter.ViewHolder {
        val binding =
            ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientsAdapter.ViewHolder, position: Int) {
        val currentItem = ingredients[position]

        with(holder.binding) {
            tvIngredient.text = currentItem.first
            tvMeasure.text = currentItem.second
        }
    }
}