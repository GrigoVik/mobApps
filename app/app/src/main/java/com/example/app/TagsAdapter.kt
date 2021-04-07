package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomRecyclerAdapter(var hits: List<Item>, var clickListener: onImageItemClickListener)
    : RecyclerView.Adapter<CustomRecyclerAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tags: TextView = itemView.findViewById(R.id.items_description)
        val previewImage: ImageView = itemView.findViewById(R.id.items_avatar)

        fun init(item: Item, action: onImageItemClickListener)
        {
            tags.text = item.tags
            Picasso.get().load(item?.previewURL).into(previewImage)

            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomRecyclerAdapter.ItemViewHolder, position: Int) {
        val item = hits[position]
        holder.init(hits[position], clickListener)
    }

    override fun getItemCount(): Int {
        return hits.size
    }


}
interface onImageItemClickListener{
    fun onItemClick(item: Item, position: Int)
}