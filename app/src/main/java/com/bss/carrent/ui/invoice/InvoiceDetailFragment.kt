package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.databinding.InvoiceDetailFragmentBinding

class InvoiceDetailFragment : Fragment() {

    private var _binding: InvoiceDetailFragmentBinding? = null

    private lateinit var invoice: InvoiceDto

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        invoice = arguments?.getSerializable("invoice") as InvoiceDto

        val invoiceDetailViewModel =
            ViewModelProvider(this)[InvoiceDetailViewModel::class.java]

        _binding = InvoiceDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val invoiceId: TextView = binding.invoiceDetailIdValue
        val invoiceTotalPrice: TextView = binding.invoiceDetailTotalPriceValue

        invoiceId.text = invoice.id.toString()
        invoiceTotalPrice.text = invoice.totalPrice.toString()

//        val payButton: Button = binding.payInvoiceButton
//        payButton.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.pay_invoice)
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}