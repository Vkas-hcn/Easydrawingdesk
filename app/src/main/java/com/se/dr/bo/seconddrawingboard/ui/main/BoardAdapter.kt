package com.se.dr.bo.seconddrawingboard.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.utils.MMKVStorage

class BoardAdapter(val dataList: MutableList<String>) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBoard: ImageView = itemView.findViewById(R.id.img_board)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemLongClick(position)
                }
                true
            }
        }
    }

    // 定义点击事件的回调接口
    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
        onItemLongClickListener = listener
    }

    // 在 item 点击事件中触发回调
    private fun onItemClick(position: Int) {
        onItemClickListener?.onItemClick(position)
    }
    private fun onItemLongClick(position: Int) {
        onItemLongClickListener?.onItemLongClick(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView: View = inflater.inflate(R.layout.item_board, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        val bitmapData = MMKVStorage.getBitmap(item)
        if (bitmapData != null) {
            holder.imgBoard.setImageBitmap(bitmapData)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    //更新数据
    fun updateData(dataList: MutableList<String>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
}