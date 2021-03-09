package com.mun0n.marvelapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mun0n.marvelapp.R
import com.mun0n.marvelapp.databinding.ActivityMainBinding
import com.mun0n.marvelapp.model.CharacterResponse
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.model.Result
import com.mun0n.marvelapp.ui.detail.DetailActivity
import com.mun0n.marvelapp.ui.main.adapter.CharacterAdapter
import com.mun0n.marvelapp.ui.main.adapter.CharacterItemClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), CharacterItemClickListener {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val charactersList: MutableList<Result> = mutableListOf()


    private val characterResponseObserver = Observer<Resource<CharacterResponse>> { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                binding.mainRecyclerView.visibility = View.VISIBLE
                binding.mainProgressBar.visibility = View.GONE
                binding.mainErrorTextView.visibility = View.GONE
                Log.d("MainActivity", "Received data from viewmodel")
                resource.data?.data?.results?.let {
                    charactersList.addAll(it)
                    charactersList.distinct()
                }
                characterAdapter.notifyDataSetChanged()
                binding.mainRecyclerView.addOnScrollListener(scrollListener)
            }
            Resource.Status.ERROR -> {
                binding.mainRecyclerView.visibility = View.GONE
                binding.mainProgressBar.visibility = View.GONE
                binding.mainErrorTextView.visibility = View.VISIBLE
            }
            Resource.Status.LOADING -> {
                binding.mainRecyclerView.visibility = View.GONE
                binding.mainProgressBar.visibility = View.VISIBLE
                binding.mainErrorTextView.visibility = View.GONE
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val linearLayoutManager = binding.mainRecyclerView.layoutManager as LinearLayoutManager
            if (linearLayoutManager.itemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                Log.d("MainActivity RecyclerView", "Load new list")
                recyclerView.removeOnScrollListener(this)
                viewModel.charactersRequest(charactersList.size + 20)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MarvelApp)
        super.onCreate(savedInstanceState)
        characterAdapter = CharacterAdapter(charactersList, this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.adapter = characterAdapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        setContentView(binding.root)
        viewModel.getCharacters().observe(this, characterResponseObserver)
    }

    override fun onCharacterItemClicked(id: Long) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, id.toString())
        }
        this.startActivity(intent)
    }
}