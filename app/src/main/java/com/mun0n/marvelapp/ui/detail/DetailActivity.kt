package com.mun0n.marvelapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mun0n.marvelapp.R
import com.mun0n.marvelapp.databinding.ActivityDetailBinding
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.model.SingleCharacterResponse
import com.mun0n.marvelapp.util.bindImageUrl
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    private var id: String? = null

    companion object {
        const val EXTRA_ID = "result_id"
    }

    private val characterResponseObserver =
        Observer<Resource<SingleCharacterResponse>> { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    binding.detailProgressBar.visibility = View.GONE
                    if (resource.data?.data?.results?.isNotEmpty() == true) {
                        val result = resource.data.data.results[0]
                        result.let { safeResult ->
                            binding.detailErrorTextView.visibility = View.GONE
                            binding.detailToolbar.title = safeResult.name
                            binding.detailImageView.bindImageUrl(
                                safeResult.thumbnail.path,
                                "landscape_incredible.jpg"
                            )
                            binding.descriptionTextView.text =
                                if (safeResult.description.isNotEmpty()) {
                                    safeResult.description
                                } else {
                                    getString(R.string.no_description)
                                }
                        }
                    } else {
                        binding.detailErrorTextView.visibility = View.VISIBLE
                    }

                }
                Resource.Status.ERROR -> {
                    binding.detailProgressBar.visibility = View.GONE
                    binding.detailErrorTextView.visibility = View.VISIBLE
                }
                Resource.Status.LOADING -> {
                    binding.detailProgressBar.visibility = View.VISIBLE
                    binding.detailErrorTextView.visibility = View.GONE
                }
            }
        }

    private val detailViewModel: DetailViewModel by viewModel { parametersOf(id) }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra(EXTRA_ID)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.detailToolbar.setNavigationOnClickListener {
            finish()
        }
        detailViewModel.getCharacterData().observe(this, characterResponseObserver)
    }
}