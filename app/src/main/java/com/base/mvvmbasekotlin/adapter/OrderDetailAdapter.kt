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
import com.base.mvvmbasekotlin.models.data.OrderDetail
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.*

class OrderDetailAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail_list, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderDetailViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as OrderDetailAdapter.OrderDetailViewHolder).bind(getItem(position, OrderDetail::class.java)!!)
    }

    inner class OrderDetailViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val imgProductDetail = itemView.findViewById<ImageView>(R.id.imgProductDetail)
        private val txtNameProductOD = itemView.findViewById<TextView>(R.id.txtNameProductOD)
        private val txtDetailPrice = itemView.findViewById<TextView>(R.id.txtDetailPrice)
        private val txtDetailQuantity = itemView.findViewById<TextView>(R.id.txtDetailQuantity)
        private val txtDetailTotal = itemView.findViewById<TextView>(R.id.txtDetailTotal)


        fun bind(currentOrder: OrderDetail) {
            txtNameProductOD.text = currentOrder.book.displayName
            txtDetailPrice.text = currentOrder.book.price.toString()
            txtDetailQuantity.text = currentOrder.quantity.toString()
            txtDetailTotal.text = currentOrder.total.toString()
            val r = Random()
            val token: Int = r.nextInt()
            Glide.with(itemView.context)
                .load(currentOrder.book.avatarUrl + "?" + token)
                .override(120, 120)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgProductDetail)
        }
    }
}