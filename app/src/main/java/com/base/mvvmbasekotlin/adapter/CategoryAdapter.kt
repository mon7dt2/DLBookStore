package com.base.mvvmbasekotlin.adapter

import android.content.Context
import android.util.Log
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.*

class CategoryAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {

    var onClickCategory: (item: CategoryPreview) -> Unit = {}
    var onClickRemoveCategory: (item: CategoryPreview) -> Unit = {}

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
        private val iconEdit = itemView.findViewById<ImageView>(R.id.iconEditCategory)
        private val iconDelete = itemView.findViewById<ImageView>(R.id.iconRemoveCategory)
    init {
        iconEdit.setOnClickListener {
            getItem(adapterPosition, CategoryPreview::class.java)?.let {
                onClickCategory(it)
            }
        }
        iconDelete.setOnClickListener {
            getItem(adapterPosition, CategoryPreview::class.java)?.let {
                onClickRemoveCategory(it)
            }
        }
    }
        fun bind(currentCategory: CategoryPreview) {
            val r = Random()
            val token: Int = r.nextInt()

            Glide.with(itemView.context)
                .load(currentCategory.avatarUrl + "?" + token)
                .override(70, 70)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCategory)

            txtNameCategoryListItem.text = currentCategory.displayName
            //Log.d("myLog",currentCategory.coverUrl)
        }
    }

}