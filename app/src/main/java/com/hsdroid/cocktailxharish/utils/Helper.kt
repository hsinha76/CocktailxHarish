package com.hsdroid.cocktailxharish.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.hsdroid.cocktailxharish.data.models.Drink

class Helper {
    companion object {
        fun showToast(context: Context?, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun setViewsVisibility(visibility: Int, vararg views: View) {
            views.forEach { it.visibility = visibility }
        }

        fun combineIngredientsAndMeasures(drink: Drink): List<Pair<String, String>> {
            val combinedList = mutableListOf<Pair<String, String>>()
            val properties = Drink::class.java.declaredFields
            for (i in 1..15) {
                val ingredientField = properties.find { it.name == "strIngredient$i" }
                val measureField = properties.find { it.name == "strMeasure$i" }
                ingredientField?.isAccessible = true
                measureField?.isAccessible = true
                val ingredient = ingredientField?.get(drink) as? String
                val measure = measureField?.get(drink) as? String
                if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                    combinedList.add(ingredient to measure)
                }
            }
            return combinedList
        }

    }
}