package com.example.homework4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class DreamListAdapter : ListAdapter<Dream, DreamListAdapter.DreamViewHolder>(DreamComparator()) {

    class DreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val idTextView : TextView = itemView.findViewById(R.id.textView_idNumber)
        val titleTextView : TextView = itemView.findViewById(R.id.textView_itemtitle)

        fun bindText(text:String?, textView: TextView){
            textView.text = text
        }

        companion object{
            fun create(parent: ViewGroup) : DreamViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dream, parent, false)
                //view.setOnClickListener{
                    //val intent = Intent(it.context, DetailedDreamActivity::class.java)
                    //startActivity(it.context, intent, null)
                //}
                return DreamViewHolder(view)
            }
        }
    }

    class DreamComparator : DiffUtil.ItemCallback<Dream>(){
        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamViewHolder {
        return DreamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val currentDream = getItem(position)
        holder.bindText(currentDream.id.toString(), holder.idTextView)
        holder.bindText(currentDream.title, holder.titleTextView)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailedDreamActivity::class.java)
            intent.putExtra("Dreamid", currentDream.id)
            startActivity(holder.itemView.context, intent, null)
        }


    }

}