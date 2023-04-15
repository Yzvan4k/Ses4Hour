package com.example.session4hour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.session4hour.Modules.Lesson
import com.example.session4hour.databinding.DelaySesBinding

class PlanAdapter(var list:List<Lesson>): RecyclerView.Adapter<PlanAdapter.PlanHolder>() {

    private val pairTime = mutableListOf<Pair<String,List<Lesson>>>()

    init {
        calcPairtime()
    }




    class PlanHolder(val binding:DelaySesBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(modelItemLesson:Lesson){
            binding.lessonTitle.text = modelItemLesson.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
        return PlanHolder(DelaySesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlanHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(plan: List<Lesson>) {
        this.list =  list
        notifyDataSetChanged()

    }
    private fun calcPairtime() {
        var times = list.maxOf { it.datetime.substringBefore("") }
    }
}