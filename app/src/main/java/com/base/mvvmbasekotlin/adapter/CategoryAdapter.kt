package com.base.mvvmbasekotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.adapter.RecyclerViewAdapter
import com.base.mvvmbasekotlin.models.data.CategoryPreview
import com.bumptech.glide.Glide
import java.util.*

class CategoryAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {

    var onClickCategory: (id: CategoryPreview) -> Unit = {}
    var onClickRemoveCategory: (id: CategoryPreview) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
        return CategoryViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(getItem(position, CategoryPreview::class.java)!!)
    }

    inner class CategoryViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val imgCategory = itemView.findViewById<ImageView>(R.id.imgCategoryItem)
        private val txtNameCategoryListItem = itemView.findViewById<TextView>(R.id.txtNameCategoryListItem)
    init {
        itemView.setOnClickListener { view ->
            when(view.id){
                R.id.iconRemoveCategory -> {
                    getItem(adapterPosition, CategoryPreview::class.java)?.let{
                        onClickRemoveCategory(it)
                    }
                }
                R.id.iconEditCategory -> {
                    getItem(adapterPosition, CategoryPreview::class.java)?.let{
                        onClickCategory(it)
                    }
                }
                else -> {
                    getItem(adapterPosition, CategoryPreview::class.java)?.let{
                        onClickCategory(it)
                    }
                }
            }

        }
    }
        fun bind(currentCategory: CategoryPreview) {
            val r = Random()
            val token: Int = r.nextInt()
            Glide.with(itemView.context)
                .load(currentCategory.coverUrl + "?" + token)
                .override(70, 70)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCategory)

            txtNameCategoryListItem.text = currentCategory.displayName
        }
    }

}