package com.bss.carrent.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.R
import com.bss.carrent.api.CarApi
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.misc.Helpers
import com.bumptech.glide.Glide

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private var carDtoList: List<CarDto> = emptyList()
    private lateinit var listener: OnItemClickListener
    private lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(carDto: CarDto)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setCarList(carDtoList: List<CarDto>) {
        this.carDtoList = carDtoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_info_row, parent, false)
        this.context = parent.context
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(carDtoList[position])
    }

    override fun getItemCount(): Int {
        return carDtoList.size
    }

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.rv_car_image)
        private val brandTextView: TextView = itemView.findViewById(R.id.rv_info_car_brand)
        private val modelTextView: TextView = itemView.findViewById(R.id.rv_info_car_model)
        private val buildYearTextView: TextView = itemView.findViewById(R.id.rv_info_car_buildyear)
        private val colorTextView: TextView = itemView.findViewById(R.id.rv_info_car_color)
        private val carTransmissionTextView: TextView =
            itemView.findViewById(R.id.rv_info_car_transmission)
        private val carTypeTextView: TextView = itemView.findViewById(R.id.rv_info_car_type)
        private val carFuelTypeLabelTextView: TextView =
            itemView.findViewById(R.id.rv_info_car_fueltype_label)
        private val carFuelTypeTextView: TextView = itemView.findViewById(R.id.rv_info_car_fueltype)
        private val carInitialCostTextView: TextView =
            itemView.findViewById(R.id.rv_info_car_initialcost)
        private val carPriceKmTextView: TextView = itemView.findViewById(R.id.rv_car_info_price)
        private val carPriceHourTextView: TextView =
            itemView.findViewById(R.id.rv_info_car_priceperhour)
        private val carDistanceKmTextView: TextView =
            itemView.findViewById(R.id.rv_info_kilometersdistance)

        init {
            itemView.setOnClickListener {
                val clickedPosition = bindingAdapterPosition
                val clickedCar = carDtoList[clickedPosition]
                listener.onItemClick(clickedCar)
            }
        }

        fun bind(carDto: CarDto) {
            brandTextView.text = carDto.brand
            modelTextView.text = carDto.model
            buildYearTextView.text = carDto.constructed.year.toString()
            colorTextView.text = carDto.color
            carTypeTextView.text = carDto.carType.toString()
            carFuelTypeLabelTextView.isVisible = (carDto.fuelType != null)
            carFuelTypeTextView.isVisible = carDto.fuelType != null
            carFuelTypeTextView.text = carDto.fuelType?.toString() ?: ""
            carInitialCostTextView.text =
                Helpers.formatDoubleWithOptionalDecimals(carDto.initialCost)
            carPriceKmTextView.text =
                Helpers.formatDoubleWithOptionalDecimals(carDto.pricePerKilometer)
            carPriceHourTextView.text =
                Helpers.formatDoubleWithOptionalDecimals(carDto.pricePerHour)

            if(carDto.imageIds != null && carDto.imageIds.isNotEmpty()) {
                val imgId = carDto.imageIds.first()

                Glide.with(context)
                    .load(CarApi.generateImageUrl(carDto.id, imgId))
                    .into(imageView)
            }
        }
    }
}