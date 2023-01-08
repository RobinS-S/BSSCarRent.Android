package com.bss.carrent.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.R
import com.bss.carrent.data.Car
import com.bss.carrent.misc.Helpers

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private var carList: List<Car> = emptyList()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(car: Car)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setCarList(carList: List<Car>) {
        this.carList = carList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_info_row, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(carList[position])
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.rv_car_image)
        private val brandTextView: TextView = itemView.findViewById(R.id.rv_info_car_brand)
        private val modelTextView: TextView = itemView.findViewById(R.id.rv_info_car_model)
        private val buildYearTextView: TextView = itemView.findViewById(R.id.rv_info_car_buildyear)
        private val colorTextView: TextView = itemView.findViewById(R.id.rv_info_car_color)
        private val carTransmissionTextView: TextView = itemView.findViewById(R.id.rv_info_car_transmission)
        private val carTypeTextView: TextView = itemView.findViewById(R.id.rv_info_car_type)
        private val carFuelTypeLabelTextView: TextView = itemView.findViewById(R.id.rv_info_car_fueltype_label)
        private val carFuelTypeTextView: TextView = itemView.findViewById(R.id.rv_info_car_fueltype)
        private val carInitialCostTextView: TextView = itemView.findViewById(R.id.rv_info_car_initialcost)
        private val carPriceKmTextView: TextView = itemView.findViewById(R.id.rv_car_info_price)
        private val carPriceHourTextView: TextView = itemView.findViewById(R.id.rv_info_car_priceperhour)
        private val carDistanceKmTextView: TextView = itemView.findViewById(R.id.rv_info_kilometersdistance)

        init {
            itemView.setOnClickListener {
                val clickedPosition = bindingAdapterPosition
                val clickedCar = carList[clickedPosition]
                listener.onItemClick(clickedCar)
            }
        }

        fun bind(car: Car) {
            brandTextView.text = car.brand
            modelTextView.text = car.model
            buildYearTextView.text = car.constructed.year.toString()
            colorTextView.text = car.color
            carTypeTextView.text = car.carType.toString()
            carFuelTypeLabelTextView.isVisible = (car.fuelType != null)
            carFuelTypeTextView.isVisible = car.fuelType != null
            carFuelTypeTextView.text = car.fuelType?.toString() ?: ""
            carInitialCostTextView.text = Helpers.formatDoubleWithOptionalDecimals(car.initialCost)
            carPriceKmTextView.text = Helpers.formatDoubleWithOptionalDecimals(car.pricePerKilometer)
            carPriceHourTextView.text = Helpers.formatDoubleWithOptionalDecimals(car.pricePerHour)
        }
    }
}