package com.example.mvvmdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.MapData
import kotlinx.android.synthetic.main.item_on_sell.view.*

/**
 *  Created by fangfuming
 *  2020/10/25
 */
class OnSellListAdapter: RecyclerView.Adapter<OnSellListAdapter.InnerHolder>() {

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val  mData = arrayListOf<MapData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_on_sell, parent, false)
        return InnerHolder(inflate)
    }

    override fun getItemCount(): Int {
       return mData.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val item = mData[position]

        holder.itemView.apply {
            with(item){
                titleTv.text = title
                offPriceTv.text = String.format("￥%.2f",zk_final_price.toFloat() - coupon_amount)
                Glide.with(holder.itemView).load("https:$pict_url").into(imageView)
            }
        }
    }

    fun setData(it: List<MapData>) {
        mData.clear()
        mData.addAll(it)
        notifyDataSetChanged()
    }
}