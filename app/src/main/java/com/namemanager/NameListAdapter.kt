package com.namemanager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namemanager.NameListAdapter.NameViewHolder

class NameListAdapter : ListAdapter<Name, NameViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        return NameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            nameItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): NameViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return NameViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Name>() {
            override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
