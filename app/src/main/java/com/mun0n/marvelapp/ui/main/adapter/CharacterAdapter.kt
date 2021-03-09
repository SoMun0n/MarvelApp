package com.mun0n.marvelapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mun0n.marvelapp.R
import com.mun0n.marvelapp.databinding.ItemCharacterBinding
import com.mun0n.marvelapp.model.Result

class CharacterAdapter(private val characterList: List<Result>,
                       private val listener: CharacterItemClickListener) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(currentCharacter: Result, listener : CharacterItemClickListener){
            binding.result = currentCharacter
            binding.characterItemClick = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemCharacterBinding = DataBindingUtil
                    .inflate(layoutInflater, R.layout.item_character,
                        parent, false)
                return CharacterViewHolder(binding)
            }
        }
    }

    companion object: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(characterList[position], listener)

    override fun getItemCount(): Int {
        return characterList.size
    }
}