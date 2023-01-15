package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bss.carrent.R
import com.bss.carrent.databinding.InvoiceDetailFragmentBinding
import kotlinx.coroutines.launch

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

        val invoiceIdTextView: TextView = binding.invoiceDetailIdValue
        val invoiceTotalPrice: TextView = binding.invoiceDetailTotalPriceValue
        invoiceIdTextView.text = args.invoice.id.toString()
        invoiceTotalPrice.text = args.invoice.totalPrice.toString()

        val payButton: Button = binding.payInvoiceDetailButton
        payButton.setOnClickListener {
            lifecycleScope.launch {
                val invoice = invoiceDetailViewModel.payInvoice(requireContext(), args.invoice.id)
                if (invoice != null) {
                    Toast.makeText(context, "You just paid!.", Toast.LENGTH_LONG).show()
                    view?.findNavController()?.navigate(R.id.nav_invoices)
                }
            }
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