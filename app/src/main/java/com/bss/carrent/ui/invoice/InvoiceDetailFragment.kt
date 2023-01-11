package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bss.carrent.R
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.databinding.InvoiceDetailFragmentBinding

class InvoiceDetailFragment : Fragment() {
    private var _binding: InvoiceDetailFragmentBinding? = null

    private val args: InvoiceDetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val invoiceDetailViewModel =
            ViewModelProvider(this)[InvoiceDetailViewModel::class.java]

        _binding = InvoiceDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val invoiceId: TextView = binding.invoiceDetailIdValue
        val invoiceTotalPrice: TextView = binding.invoiceDetailTotalPriceValue
        invoiceId.text = args.invoice.id.toString()
        invoiceTotalPrice.text = args.invoice.totalPrice.toString()

        val payButton: Button = binding.payInvoiceDetailButton
        payButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.nav_invoices)
        }

        val backButton: Button = binding.payInvoiceDetailBackButton
        backButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.nav_invoices)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}