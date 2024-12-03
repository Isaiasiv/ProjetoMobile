package com.example.projetomobile.ui.home.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import org.w3c.dom.Text


class TaskAdapter(private val dataList:ArrayList<DataTask>):RecyclerView.Adapter<TaskAdapter.ViewHolderTask>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTask {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout,parent,false)
        return ViewHolderTask(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderTask, position: Int) {
        val currentItem = dataList[position]
        holder.rvMateria.text = currentItem.materia
        holder.rvAtividade.text = currentItem.atividade
        holder.rvData.text = currentItem.data
    }


    class ViewHolderTask(itemView: View):RecyclerView.ViewHolder(itemView) {
        val rvMateria:TextView = itemView.findViewById(R.id.task_materia)
        val rvAtividade:TextView = itemView.findViewById(R.id.task_atividade)
        val rvData:TextView = itemView.findViewById(R.id.task_data)

    }



}