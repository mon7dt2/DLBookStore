package com.base.mvvmbasekotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.adapter.RecyclerViewAdapter
import com.base.mvvmbasekotlin.models.data.ProviderPreview

class ProviderAdapter(mContext: Context): RecyclerViewAdapter(mContext, true) {

    var onClickItem: (item: ProviderPreview) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_provider_list, parent, false)
        return ProviderViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProviderViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as ProviderAdapter.ProviderViewHolder).bind(getItem(position, ProviderPreview::class.java)!!)
    }

    inner class ProviderViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val txtName = itemView.findViewById<TextView>(R.id.txtNameProvider)
        private val txtPhone = itemView.findViewById<TextView>(R.id.txtPhoneProvider)
        private val txtEmail = itemView.findViewById<TextView>(R.id.txtEmailProvider)

        init {
            itemView.setOnClickListener {
                getItem(adapterPosition, ProviderPreview::class.java)?.let {
                    onClickItem(it)
                }
            }
        }
        fun bind(currentProvider: ProviderPreview) {
            txtName.text = currentProvider.displayName
            txtPhone.text = currentProvider.phone
            txtEmail.text = currentProvider.email
        }
    }
}