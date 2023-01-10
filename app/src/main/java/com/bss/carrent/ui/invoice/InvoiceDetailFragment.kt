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
import com.bss.carrent.R
import com.bss.carrent.data.Invoice
import com.bss.carrent.databinding.FragmentInvoiceBinding

class InvoiceDetailFragment : Fragment() {

    private var _binding: FragmentInvoiceBinding? = null

    private lateinit var invoice: Invoice

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        invoice = arguments?.getSerializable("invoice") as Invoice

        val invoiceDetailViewModel =
            ViewModelProvider(this).get(InvoiceDetailViewModel::class.java)

        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val invoiceDetailId: TextView = binding.invoiceIdValue
        val payButton: Button = binding.payInvoiceButton
        invoiceDetailViewModel.text.observe(viewLifecycleOwner) {
            invoiceDetailId.text = it
        }
        payButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.pay_invoice)
        }

        invoiceDetailViewModel.setText(invoice.id)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}