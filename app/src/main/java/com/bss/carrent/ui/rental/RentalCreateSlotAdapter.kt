package com.bss.carrent.ui.rental

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.data.rental.RentalPeriodDto
import com.bss.carrent.databinding.RentalCreateSlotBinding
import com.bss.carrent.misc.Helpers

class RentalCreateSlotAdapter : RecyclerView.Adapter<RentalCreateSlotAdapter.SlotViewHolder>() {
    private var slotList: List<RentalPeriodDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val binding = RentalCreateSlotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        holder.bind(slotList[position])
    }

    override fun getItemCount(): Int {
        return slotList.size
    }

    fun setSlotList(slotList: List<RentalPeriodDto>) {
        this.slotList = slotList
        notifyDataSetChanged()
    }


    inner class SlotViewHolder(val binding: RentalCreateSlotBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slotDto: RentalPeriodDto) {
            binding.textView.text = String.format("%s - %s", Helpers.formatDateTime(slotDto.reservedFrom), Helpers.formatDateTime(slotDto.reservedUntil))
        }
    }
}