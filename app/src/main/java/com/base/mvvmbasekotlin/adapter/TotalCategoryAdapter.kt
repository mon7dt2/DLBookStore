package com.base.mvvmbasekotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.adapter.RecyclerViewAdapter
import com.base.mvvmbasekotlin.models.data.ItemData
import com.base.mvvmbasekotlin.models.data.ProviderPreview

class TotalCategoryAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_dashboard, parent, false)
        return TotalCategoryViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TotalCategoryViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as TotalCategoryAdapter.TotalCategoryViewHolder).bind(getItem(position, ItemData::class.java)!!)
    }

    inner class TotalCategoryViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val txtName = itemView.findViewById<TextView>(R.id.txtName)
        private val txtQuantity = itemView.findViewById<TextView>(R.id.txtQuantity)

        fun bind(currentItemData: ItemData) {
            txtName.text = currentItemData.name
            txtQuantity.text = currentItemData.number.toString()
        }
    }
}