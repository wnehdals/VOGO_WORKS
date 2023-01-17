package com.example.vogoworks.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vogoworks.R
import com.example.vogoworks.databinding.ItemAlarmBinding
import com.jdm.domain.model.AlarmInfo

class AlarmAdapter(
    private val context: Context
): RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    val alarmList = mutableListOf<AlarmInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alarmList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
    fun addData(list: List<AlarmInfo>) {
        alarmList.clear()
        alarmList.addAll(list)
    }

    inner class ViewHolder(val binding: ItemAlarmBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(alarmInfo: AlarmInfo, pos: Int) {
            binding.alarmTimeTv.text = "${alarmInfo.startHour}:${alarmInfo.startMinute} ~ ${alarmInfo.endHour}:${alarmInfo.endMinute}"
            if (alarmInfo.isOut) {
                binding.alarmOnOffBt.isSelected = false
                binding.alarmOnOffBt.text = context.getString(R.string.en_out)
                binding.alarmOnOffBt.setTextColor(ContextCompat.getColor(context, R.color.color_fb7474))
            } else {
                binding.alarmOnOffBt.isSelected = true
                binding.alarmOnOffBt.text = context.getString(R.string.en_in)
                binding.alarmOnOffBt.setTextColor(ContextCompat.getColor(context, R.color.color_745ff2))
            }


        }
    }
}