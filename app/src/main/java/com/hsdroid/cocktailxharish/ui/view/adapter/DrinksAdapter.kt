package com.hsdroid.cocktailxharish.ui.view.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hsdroid.cocktailxharish.R
import com.hsdroid.cocktailxharish.data.models.Drink
import com.hsdroid.cocktailxharish.databinding.ItemDrinksBinding

class DrinksAdapter : RecyclerView.Adapter<DrinksAdapter.ViewHolder>() {

    private var drinksList: List<Drink>? = null

    inner class ViewHolder(val binding: ItemDrinksBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksAdapter.ViewHolder {
        val binding = ItemDrinksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (drinksList == null) return 0
        return drinksList!!.size
    }

    override fun onBindViewHolder(holder: DrinksAdapter.ViewHolder, position: Int) {
        val currentItem = drinksList?.get(position)

        holder.binding.apply {
            tvDrinkName.text = currentItem?.strDrink

            Glide.with(imgDrink.context).apply {
                load(currentItem?.strDrinkThumb).into(imgDrink)
            }

            cardItem.setOnClickListener {
                val navController = Navigation.findNavController(it)
                val bundle = Bundle().apply {
                    putParcelable("drinkInfo", currentItem)
                }
                navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(drinksItem: List<Drink>?) {
        drinksList = drinksItem
        notifyDataSetChanged()
    }
}