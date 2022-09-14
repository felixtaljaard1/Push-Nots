package com.example.spaceapp.spacelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spaceapp.data.entities.ResultSpaceItem
import com.example.spaceapp.databinding.RowSpaceBinding


class SpaceAdapter(private val onClickListener: OnClickListener): ListAdapter<ResultSpaceItem, SpaceAdapter.MyViewHolder>(SpaceItemDiffCallback()) {

    class OnClickListener(val clickListener: (space: ResultSpaceItem) -> Unit){
        fun onClick(space: ResultSpaceItem) = clickListener(space)
    }

    class MyViewHolder(val rowSpaceBinding: RowSpaceBinding) : RecyclerView.ViewHolder(rowSpaceBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        val view = LayoutInflater.from(parent.context)
        val binding = RowSpaceBinding.inflate(view, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val spaceList = getItem(position)
        holder.rowSpaceBinding.tvTitle.text = spaceList.title
        holder.rowSpaceBinding.tvSummary.text = spaceList.summary

        Glide.with(holder.itemView.context)
            .load(spaceList.imageUrl)
            .into(holder.rowSpaceBinding.tvImage)

        holder.itemView.setOnClickListener{
            onClickListener.onClick(spaceList)
        }
    }

}

class SpaceItemDiffCallback: DiffUtil.ItemCallback<ResultSpaceItem>(){
    override fun areItemsTheSame(oldItem: ResultSpaceItem, newItem: ResultSpaceItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ResultSpaceItem, newItem: ResultSpaceItem): Boolean =
        oldItem == newItem
}