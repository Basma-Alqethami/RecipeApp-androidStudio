package com.example.recipeapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_row.view.*

class RVAdapter (private val appContext: Context, private var recipes: ArrayList<recipeDataItem>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.itemView.apply {
            tvTitle.text = recipe.title
            tvAuthor.text = recipe.author
            cardView.setOnClickListener {
                val recData = recipeDataItem(recipes[position].pk,recipes[position].author, recipes[position].ingredients, recipes[position].instructions,recipes[position].title)
                val intent = Intent(holder.itemView.context,ShowInformation::class.java)
                intent.putExtra("displayData",recData)
                holder.itemView.context.startActivity(intent) 
            }
        }
    }

    override fun getItemCount() = recipes.size

}
