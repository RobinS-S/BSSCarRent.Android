package com.bss.carrent.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.R
import com.bss.carrent.data.Car

class CarAdapter : RecyclerView.Adapter<CarAdapter.NameViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_info_row, parent, false)
        return NameViewHolder(view)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(carList[position])
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.rv_car_image)
        private val brandTextView: TextView = itemView.findViewById(R.id.rv_info_car_brand)
        private val modelTextView: TextView = itemView.findViewById(R.id.rv_info_car_model)
        private val buildYearTextView: TextView = itemView.findViewById(R.id.rv_info_car_buildyear)
        private val colorTextView: TextView = itemView.findViewById(R.id.rv_info_car_color)
        private val carTypeTextView: TextView = itemView.findViewById(R.id.rv_info_car_type)


        init {
            itemView.setOnClickListener {
                val clickedPosition = adapterPosition
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
        }
    }
}