package com.sultonuzdev.andersentask.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sultonuzdev.andersentask.R
import com.sultonuzdev.andersentask.databinding.ActivityProductListBinding
import com.sultonuzdev.andersentask.presentation.adapters.ImagePagerAdapter
import com.sultonuzdev.andersentask.presentation.adapters.ProductListItemAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val viewModel: MainViewModel by viewModel()

    private lateinit var imageViewPagerAdapter: ImagePagerAdapter
    private lateinit var productListItemAdapter: ProductListItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHideKeyboardOnTouch()
        setupImagePager()
        setupRecyclerView()
        setupSearchBar()
        setupFab()
        observeViewModel()
    }


    private fun setupHideKeyboardOnTouch() {
        binding.rootLayout.setOnClickListener {
            hideKeyboard()
        }

    }

    private fun setupImagePager() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.categories.isNotEmpty() && !::imageViewPagerAdapter.isInitialized) {
                        imageViewPagerAdapter =
                            ImagePagerAdapter(state.categoryImages, lifecycleScope)
                        binding.imageViewPager.adapter = imageViewPagerAdapter
                        setupDotIndicator(state.categoryImages.size)
                        updateDotIndicator(0)

                        binding.imageViewPager.registerOnPageChangeCallback(
                            object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    viewModel.onPageChange(position)
                                    updateDotIndicator(position)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun setupDotIndicator(count: Int) {


        (0 until count).forEach { i ->
            val imageDot = ImageView(this)
            imageDot.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageDot.setPadding(8, 0, 8, 0)
            imageDot.setImageResource(R.drawable.tab_selector)
            binding.dotsIndicator.addView(imageDot)
        }
    }

    private fun updateDotIndicator(position: Int) {
        val childCount = binding.dotsIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.dotsIndicator.getChildAt(i) as ImageView
            imageView.setImageResource(if (i == position) R.drawable.dot_indicator_active else R.drawable.dot_indicator_inactive)
        }

    }


    private fun setupRecyclerView() {

        productListItemAdapter = ProductListItemAdapter(lifecycleScope)
        binding.rvProduct.apply {
            adapter = productListItemAdapter
            layoutManager = LinearLayoutManager(this@ProductListActivity)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        hideKeyboard()
                    }
                }
            })
        }
    }


    private fun setupSearchBar() {
        binding.etSearch.doAfterTextChanged { text ->
            viewModel.onSearchQueryChange(text.toString())
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun setupFab() {
        binding.fabStatistics.setOnClickListener {
            showStatisticsBottomSheet(viewModel.state.value.categoryProductStatistics)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    productListItemAdapter.submitList(state.filteredProducts) {
                        binding.rvProduct.post {
                            val recyclerViewHeight = binding.rvProduct.height
                            val contentHeight = binding.rvProduct.computeVerticalScrollRange()

                            val canScroll = contentHeight > recyclerViewHeight

                            val pagerParams =
                                binding.pagerContainer.layoutParams as AppBarLayout.LayoutParams
                            val toolbarParams =
                                binding.toolbar.layoutParams as AppBarLayout.LayoutParams

                            if (canScroll) {
                                pagerParams.scrollFlags =
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                            AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                                toolbarParams.scrollFlags =
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or
                                            AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                            } else {
                                pagerParams.scrollFlags = 0
                                toolbarParams.scrollFlags = 0
                            }

                            binding.pagerContainer.layoutParams = pagerParams
                            binding.toolbar.layoutParams = toolbarParams
                        }
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { view ->
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
        binding.etSearch.clearFocus()
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun showStatisticsBottomSheet(statistics: ProductStatistics) {
        val bottomSheet = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_content, null)

        view.findViewById<TextView>(R.id.tv_category_name).text =
            statistics.categoryName

        view.findViewById<TextView>(R.id.tv_product_count).text =
            statistics.totalProduct.toString()

        val charactersContainer = view.findViewById<LinearLayout>(R.id.characters_container)
        charactersContainer.removeAllViews()

        statistics.topCharacter.forEach { (char, count) ->
            val characterView = layoutInflater.inflate(
                R.layout.item_character_stat,
                charactersContainer,
                false
            ) as TextView

            characterView.text = "$char : $count"
            charactersContainer.addView(characterView)
        }

        bottomSheet.setContentView(view)
        bottomSheet.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::imageViewPagerAdapter.isInitialized) {
            binding.imageViewPager.adapter = null
        }
    }
}