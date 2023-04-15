package com.example.session4hour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.session4hour.Modules.Lesson
import com.example.session4hour.databinding.DelaySesBinding


// Данный класс подставляет View в RecyclerView
// 15.04.23
// Yzvan

class DelayAdapter(var list: List<Lesson>): RecyclerView.Adapter<DelayAdapter.VH>() {
    class VH(val binding:DelaySesBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(modelitemLesson:Lesson){
            binding.lessonTitle.text = modelitemLesson.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(DelaySesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }

    fun setData(lessons: List<Lesson>) {
        this.list = lessons
        notifyDataSetChanged()

    }
}