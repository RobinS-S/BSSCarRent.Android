package com.bss.carrent.ui.invoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bss.carrent.R
import com.bss.carrent.data.InvoiceDto

class InvoiceAdapter : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {
    private var invoiceList: List<InvoiceDto> = emptyList()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(invoice: InvoiceDto)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setInvoiceList(invoiceList: List<InvoiceDto>) {
        this.invoiceList = invoiceList.sortedByDescending { it.id }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.invoice_info_row, parent, false)
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
        private val invoiceInitialCostTextView: TextView =
            itemView.findViewById(R.id.invoice_initialcost_value)
        private val invoiceMileageTotalTextView: TextView =
            itemView.findViewById(R.id.invoice_mileagetotal_value)
        private val invoiceMileageCostsTextView: TextView =
            itemView.findViewById(R.id.invoice_mileagecosts_value)
        private val invoicekmPackageTextView: TextView =
            itemView.findViewById(R.id.invoice_kmpackage_value)
        private val invoiceTotalPriceTextView: TextView =
            itemView.findViewById(R.id.invoice_totalprice_value)
        private val invoiceTotalhoursUsedTextView: TextView =
            itemView.findViewById(R.id.invoice_totalhoursused_value)
        private val invoiceTotalhourPriceTextView: TextView =
            itemView.findViewById(R.id.invoice_totalhourprice_value)
        private val invoiceIsPaidTextView: TextView =
            itemView.findViewById(R.id.invoice_ispaid_value)
        private val invoicePayButton: Button = itemView.findViewById(R.id.pay_invoice_button)

        fun bind(invoice: InvoiceDto) {
            invoiceIdTextView.text = invoice.id.toString()
            invoiceInitialCostTextView.text = invoice.initialCost.toString()
            invoiceMileageTotalTextView.text = invoice.mileageTotal.toString()
            invoiceMileageCostsTextView.text = invoice.mileageCosts.toString()
            invoicekmPackageTextView.text = invoice.kmPackage.toString()
            invoiceTotalPriceTextView.text = invoice.totalPrice.toString()
            invoiceTotalhoursUsedTextView.text = invoice.totalHoursUsed.toString()
            invoiceTotalhourPriceTextView.text = invoice.totalHourPrice.toString()
            invoiceIsPaidTextView.text = if (invoice.isPaid) "paid" else "not paid"
            invoicePayButton.visibility = if (!invoice.isPaid) View.VISIBLE else View.INVISIBLE
            invoicePayButton.setOnClickListener {
                var action = InvoiceListFragmentDirections.actionNavInvoicesToNavInvoice(invoice)
                itemView.findNavController().navigate(action)
            }
        }
    }
}