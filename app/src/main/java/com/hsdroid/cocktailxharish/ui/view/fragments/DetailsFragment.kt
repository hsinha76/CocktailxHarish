package com.hsdroid.cocktailxharish.ui.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.hsdroid.cocktailxharish.R
import com.hsdroid.cocktailxharish.data.models.Drink
import com.hsdroid.cocktailxharish.databinding.FragmentDetailsBinding
import com.hsdroid.cocktailxharish.ui.view.adapter.IngredientsAdapter
import com.hsdroid.cocktailxharish.utils.Helper.Companion.combineIngredientsAndMeasures
import com.hsdroid.cocktailxharish.utils.Helper.Companion.setViewsVisibility
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Parcelable data here
        val receivedData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("drinkInfo", Drink::class.java)
        } else {
            arguments?.getParcelable("drinkInfo")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            //Added a 2 sec delay just for showing animation
            delay(2000)
            initView(receivedData)
        }
    }

    private fun initView(receivedData: Drink?) {
        with(binding) {

            setViewsVisibility(View.VISIBLE, viewDetails)
            setViewsVisibility(View.GONE, viewLoading)

            receivedData?.let {

                Glide.with(requireContext()).apply {
                    load(it.strDrinkThumb).into(imgHeaderMeal).onLoadFailed(
                        ResourcesCompat.getDrawable(
                            resources, R.drawable.image_not_available, null
                        )
                    )
                }

                tvMealName.text = it.strDrink
                tvMealDescription.text = it.strInstructions
                tvCategory.text = it.strCategory

                val combinedData = combineIngredientsAndMeasures(receivedData)
                Log.d("harish", combinedData.toString())

                binding.recyclerViewIngredients.apply {
                    adapter = IngredientsAdapter(combinedData)
                }
            }

            imgBack.setOnClickListener {
                backScreen()
            }

        }
    }

    private fun backScreen() {
        val navController = Navigation.findNavController(binding.root)
        navController.navigateUp()
    }
}