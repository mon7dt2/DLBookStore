package com.base.mvvmbasekotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.adapter.RecyclerViewAdapter
import com.base.mvvmbasekotlin.models.data.Order

class OrderAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {

    var onClickItem: (item: Order) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, parent, false)
        return OrderViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as OrderAdapter.OrderViewHolder).bind(getItem(position, Order::class.java)!!)
    }

    inner class OrderViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val txtSearchKey = itemView.findViewById<TextView>(R.id.txtSearchKey)
        private val txtOCName = itemView.findViewById<TextView>(R.id.txtOCName)
        private val txtOCPhone = itemView.findViewById<TextView>(R.id.txtOCPhone)
        private val txtOrderState = itemView.findViewById<TextView>(R.id.txtOrderState)

        init {
            itemView.setOnClickListener {
                getItem(adapterPosition, Order::class.java)?.let {
                    onClickItem(it)
                }
            }
        }
        fun bind(currentOrder: Order) {
            txtSearchKey.text = currentOrder.searchKey
            txtOCName.text = currentOrder.customer.fullName
            txtOCPhone.text = currentOrder.customer.phone
            txtOrderState.text = when (currentOrder.orderStatus) {
                1 -> "Đã giao"
                0 -> "Đang giao"
                else -> "Đã Hủy"
            }
        }
    }
}