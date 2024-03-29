package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bss.carrent.R
import com.bss.carrent.data.InvoiceDto
import com.bss.carrent.databinding.InvoiceListFragmentBinding
import kotlinx.coroutines.launch

class InvoiceListFragment : Fragment() {

    private var _binding: InvoiceListFragmentBinding? = null
    private lateinit var invoiceAdapter: InvoiceAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val invoiceViewModel =
            ViewModelProvider(this)[InvoiceViewModel::class.java]

        _binding = InvoiceListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.invoicesListRecyclerView.layoutManager = layoutManager

        invoiceAdapter = InvoiceAdapter()
        invoiceAdapter.setOnItemClickListener(object : InvoiceAdapter.OnItemClickListener {
            override fun onItemClick(invoice: InvoiceDto) {
            }
        })

        binding.invoicesListRecyclerView.adapter = invoiceAdapter
        binding.invoicesListSwipeRefresh.isRefreshing = true

        invoiceViewModel.invoiceList.observe(viewLifecycleOwner) { invoiceList ->
            invoiceList?.let {
                invoiceAdapter.setInvoiceList(it)
                invoiceAdapter.notifyDataSetChanged()
                binding.invoicesListSwipeRefresh.isRefreshing = false
            }
        }

        binding.invoicesListSwipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                binding.invoicesListSwipeRefresh.isRefreshing = true
                invoiceViewModel.getOwnedInvoices(requireContext())
            }
        }

        binding.rentalListRadiogroup.setOnCheckedChangeListener { group, checkedId ->
            binding.invoicesListSwipeRefresh.isRefreshing = true
            when (checkedId) {
                R.id.invoice_list_radio_button_mine -> {
                    lifecycleScope.launch {
                        invoiceViewModel.getOwnedInvoices(requireContext())
                    }
                }
                R.id.invoice_list_radio_button_owned -> {
                    lifecycleScope.launch {
                        invoiceViewModel.getMyInvoices(requireContext())
                    }
                }
            }
        }

        binding.invoiceListRadioButtonMine.isChecked = true

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}