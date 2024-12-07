package com.example.projetomobile.ui.busca
/*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.databinding.ItemTipBinding

class TipsAdapter : ListAdapter<Tip, TipsAdapter.TipViewHolder>(DIFF_CALLBACK) {

    var gotItItemClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        return TipViewHolder.create(parent, gotItItemClickListener)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tip>() {
            override fun areItemsTheSame(oldItem: Tip, newItem: Tip): Boolean {
                return oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: Tip, newItem: Tip): Boolean {
                return oldItem == newItem
            }

        }
    }

    class TipViewHolder(
        private val itemBinding: ItemTipBinding,
        private val gotItItemClickListener: (() -> Unit)?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(tip: Tip) {
            itemBinding.textTip.text = tip.description

            itemBinding.textGotIt.setOnClickListener {
                gotItItemClickListener?.invoke()
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                gotItItemClickListener: (() -> Unit)?
            ): TipViewHolder {
                val itemBinding = ItemTipBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                return TipViewHolder(itemBinding, gotItItemClickListener)
            }
        }
    }
}*/