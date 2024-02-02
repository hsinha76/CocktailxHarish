package com.hsdroid.cocktailxharish.ui.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hsdroid.cocktailxharish.data.models.Cocktail
import com.hsdroid.cocktailxharish.databinding.FragmentHomeBinding
import com.hsdroid.cocktailxharish.ui.view.adapter.DrinksAdapter
import com.hsdroid.cocktailxharish.ui.viewmodel.HomeViewModel
import com.hsdroid.cocktailxharish.utils.ApiState
import com.hsdroid.cocktailxharish.utils.Helper.Companion.setViewsVisibility
import com.hsdroid.cocktailxharish.utils.Helper.Companion.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val drinksAdapter by lazy {
        DrinksAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                //Added a 2 sec delay just for showing animation
                delay(2000)

                setViewsVisibility(View.GONE, viewAnimation)
                setViewsVisibility(View.VISIBLE, viewStart)
            }
        }

        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {

        binding.recyclerView.apply {
            adapter = drinksAdapter
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.trim().isNotEmpty()) {
                    homeViewModel.getDrinks(s.toString())
                }
            }

        })

        collectFlows()
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.response.collectLatest {
                    when (it) {
                        is ApiState.LOADING -> loadingView()
                        is ApiState.SUCCESS -> setDataToRecycler(it.data)
                        is ApiState.FAILURE -> errorView(it.throwable)
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun loadingView() {
        with(binding) {
            setViewsVisibility(View.GONE, recyclerView, tvError)
            setViewsVisibility(View.VISIBLE, progressCircular)
        }
    }

    private fun setDataToRecycler(data: Cocktail?) {
        with(binding) {

            if (data?.drinks != null) {
                drinksAdapter.setList(data.drinks)
            } else {
                with(binding) {
                    setViewsVisibility(View.GONE, progressCircular, recyclerView)
                    setViewsVisibility(View.VISIBLE, tvError)
                }
            }
            setViewsVisibility(View.GONE, progressCircular, tvError)
            setViewsVisibility(View.VISIBLE, recyclerView)
        }
    }

    private fun errorView(throwable: Throwable) {
        with(binding) {
            setViewsVisibility(View.GONE, progressCircular, recyclerView)
            setViewsVisibility(View.VISIBLE, tvError)
        }

        showToast(context, throwable.message)
    }
}