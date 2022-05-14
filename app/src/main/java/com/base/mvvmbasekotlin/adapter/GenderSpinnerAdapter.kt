package com.base.mvvmbasekotlin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.adapter.RecyclerViewAdapter
import com.base.mvvmbasekotlin.models.data.Category
import com.bumptech.glide.Glide

class GenderSpinnerAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return CategoryViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(getItem(position, Category::class.java)!!)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCategory = itemView.findViewById<ImageView>(R.id.imgCategoryItem)
        private val txtNameCategoryListItem = itemView.findViewById<TextView>(R.id.txtNameCategoryListItem)

        fun bind(currentCategory: Category) {
            Glide.with(itemView.context)
                .load(currentCategory.coverUrl)
                .override(70, 70)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCategory)

            txtNameCategoryListItem.text = currentCategory.displayName
        }
    }

}