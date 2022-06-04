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
import com.base.mvvmbasekotlin.models.data.CustomerPreview
import com.base.mvvmbasekotlin.models.data.ProductPreview
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.*

class ProductAdapter(mContext: Context): RecyclerViewAdapter(mContext, true)  {

    var onClickItem: (item: ProductPreview) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as ProductAdapter.ProductViewHolder).bind(getItem(position, ProductPreview::class.java)!!)
    }

    inner class ProductViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val imgProduct = itemView.findViewById<ImageView>(R.id.imgProduct)
        private val txtName = itemView.findViewById<TextView>(R.id.txtNameProduct)
        private val txtCate = itemView.findViewById<TextView>(R.id.txtCategoryProduct)
        private val txtPublisher = itemView.findViewById<TextView>(R.id.txtPublisher)
        private val txtQuantity = itemView.findViewById<TextView>(R.id.txtQuantity)

        init {
            itemView.setOnClickListener {
                getItem(adapterPosition, ProductPreview::class.java)?.let {
                    onClickItem(it)
                }
            }
        }
        fun bind(currentProduct: ProductPreview) {
            val r = Random()
            val token: Int = r.nextInt()

            Glide.with(itemView.context)
                .load(currentProduct.avatarUrl + "?" + token)
                .override(120, 120)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgProduct)

            txtName.text = currentProduct.displayName
            txtCate.text = currentProduct.category.displayName
            txtPublisher.text = currentProduct.publisher
            val quantity = "Số lượng: ${currentProduct.quantity}"
            txtQuantity.text = quantity
        }
    }
}