package com.bss.carrent.ui.rental

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.databinding.RentalListRowBinding
import com.bss.carrent.misc.Helpers

class RentalListAdapter : RecyclerView.Adapter<RentalListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RentalListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var rentalDtoList: List<RentalDto> = emptyList()
    private lateinit var listener: OnItemClickListener
    private lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(rentalDto: RentalDto)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setRentalList(rentalDtoList: List<RentalDto>) {
        this.rentalDtoList = rentalDtoList.sortedByDescending { it.id }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RentalListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(rentalDtoList[position]) {
                binding.rentalReservationStartDatetime.text =
                    Helpers.formatDateTime(this.reservedFrom)
                binding.rentalReservationEndDatetime.text =
                    Helpers.formatDateTime(this.reservedUntil)
                binding.rentalTotalMileage.text = this.mileageTotal.toString()
                binding.rentalKmAmount.text = this.kmPackage.toString()
                binding.rentalDrivingstyleScore.text =
                    if (this.drivingStyleScore != null) this.drivingStyleScore.toString() else "-"
                binding.rentalPickupDatetime.text =
                    if (this.pickedUpAt != null) Helpers.formatDateTime(this.pickedUpAt) else "-"
                binding.rentalDeliveredDatetime.text =
                    if (this.deliveredAt != null) Helpers.formatDateTime(this.deliveredAt) else "-"

                itemView.setOnClickListener {
                    val clickedPosition = bindingAdapterPosition
                    val clickedRental = rentalDtoList[clickedPosition]
                    listener.onItemClick(clickedRental)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return rentalDtoList.size
    }
}