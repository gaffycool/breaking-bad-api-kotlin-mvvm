package com.gaffy.brackingbadtechtest.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaffy.brackingbadtechtest.R
import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import kotlinx.android.synthetic.main.layout_item.view.*

class CharacterAdapter(
    val characters: MutableList<BreakingBadChar>,
    private val clickListener: (BreakingBadChar) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
            , clickListener
        )
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun updateData(characters: List<BreakingBadChar>) {
        this.characters.clear()
        this.characters.addAll(characters)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View, private val clickListener: (BreakingBadChar) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(character: BreakingBadChar) {
            itemView.setOnClickListener {
                clickListener.invoke(character)
            }
            itemView.tv_name.text = character.name
            Glide.with(itemView.iv_character).load(character.img).into(itemView.iv_character)
        }
    }
}
