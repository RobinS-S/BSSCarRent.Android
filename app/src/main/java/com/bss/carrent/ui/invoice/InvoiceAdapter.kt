package com.bss.carrent.ui.invoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.R
import com.bss.carrent.data.Invoice

class InvoiceAdapter : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {
    private var invoiceList: List<Invoice> = emptyList()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(invoice: Invoice)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setInvoiceList(invoiceList: List<Invoice>) {
        this.invoiceList = invoiceList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.invoice_info_row, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        holder.bind(invoiceList[position])
    }

    override fun getItemCount(): Int {
        return invoiceList.size
    }

    inner class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val invoiceIdTextView: TextView = itemView.findViewById(R.id.invoice_id_value)
        private val invoiceInitialCostTextView : TextView = itemView.findViewById(R.id.invoice_initialcost_value)
        private val invoiceMileageTotalTextView : TextView = itemView.findViewById(R.id.invoice_mileagetotal_value)
        private val invoiceMileageCostsTextView : TextView = itemView.findViewById(R.id.invoice_mileagecosts_value)
        private val invoicekmPackageTextView : TextView = itemView.findViewById(R.id.invoice_kmpackage_value)
        private val invoiceTotalPriceTextView : TextView = itemView.findViewById(R.id.invoice_totalprice_value)
        private val invoiceTotalhoursUsedTextView : TextView = itemView.findViewById(R.id.invoice_totalhoursused_value)
        private val invoiceTotalhourPriceTextView : TextView = itemView.findViewById(R.id.invoice_totalhourprice_value)
        private val invoiceIsPaydTextView : TextView = itemView.findViewById(R.id.invoice_ispayd_value)
        private val invoiceIsPaydButtonView : TextView = itemView.findViewById(R.id.pay_invoice_button)

        init {
            itemView.setOnClickListener {
                val clickedPosition = bindingAdapterPosition
                val clickedInvoice = invoiceList[clickedPosition]
                listener.onItemClick(clickedInvoice)
            }
        }

        fun bind(invoice: Invoice) {
            invoiceIdTextView.text = invoice.id.toString()
            invoiceInitialCostTextView.text = invoice.initialCost.toString()
            invoiceMileageTotalTextView.text = invoice.mileageTotal.toString()
            invoiceMileageCostsTextView.text = invoice.mileageCosts.toString()
            invoicekmPackageTextView.text = invoice.kmPackage.toString()
            invoiceTotalPriceTextView.text = invoice.totalPrice.toString()
            invoiceTotalhoursUsedTextView.text = invoice.totalHoursUsed.toString()
            invoiceTotalhourPriceTextView.text = invoice.totalHourPrice.toString()
            invoiceIsPaydTextView.text = invoice.isPaid.toString()
            //invoiceIsPaydButtonView.text = View.VISIBLE.toString()
        }
    }
}