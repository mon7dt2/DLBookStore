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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.*

class CustomerAdapter(mContext: Context): RecyclerViewAdapter(mContext, true)  {

    var onClickItem: (item: CustomerPreview) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_person_list, parent, false)
        return CustomerViewHolder(view)
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CustomerViewHolder(parent.rootView)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as CustomerAdapter.CustomerViewHolder).bind(getItem(position, CustomerPreview::class.java)!!)
    }

    inner class CustomerViewHolder(itemView: View) : NormalViewHolder(itemView) {
        private val imgCustomer = itemView.findViewById<ImageView>(R.id.imgPerson)
        private val txtName = itemView.findViewById<TextView>(R.id.txtNamePerson)
        private val txtPhone = itemView.findViewById<TextView>(R.id.txtPhonePerson)
        private val txtEmail = itemView.findViewById<TextView>(R.id.txtEmailPerson)

        init {
            itemView.setOnClickListener {
                getItem(adapterPosition, CustomerPreview::class.java)?.let {
                    onClickItem(it)
                }
            }
        }
        fun bind(currentCustomer: CustomerPreview) {
            val r = Random()
            val token: Int = r.nextInt()

            Glide.with(itemView.context)
                .load(currentCustomer.avatarUrl + "?" + token)
                .override(120, 120)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCustomer)

            txtName.text = currentCustomer.fullName
            txtPhone.text = currentCustomer.phone
            txtEmail.text = currentCustomer.email
        }
    }
}