package com.bss.carrent.ui.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bss.carrent.R
import com.bss.carrent.data.Invoice
import com.bss.carrent.databinding.FragmentInvoicesBinding


class InvoiceFragment : Fragment() {

    private var _binding: FragmentInvoicesBinding? = null
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

        _binding = FragmentInvoicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.invoicesListRecyclerView.layoutManager = layoutManager

        invoiceAdapter = InvoiceAdapter()
        invoiceAdapter.setOnItemClickListener(object: InvoiceAdapter.OnItemClickListener {
            override fun onItemClick(invoice: Invoice) {
                val fragmentTransaction = parentFragmentManager.beginTransaction()

                val invoiceDetailFragment = InvoiceDetailFragment()
                val args = Bundle()
                args.putSerializable("invoice", invoice)
                invoiceDetailFragment.arguments = args

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, invoiceDetailFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        binding.invoicesListRecyclerView.adapter = invoiceAdapter

        invoiceViewModel.invoiceList.observe(viewLifecycleOwner) { invoiceList ->
            invoiceList?.let {
                invoiceAdapter.setInvoiceList(it)
                invoiceAdapter.notifyDataSetChanged()
                binding.invoicesListSwipeRefresh.isRefreshing = false
            }
        }

        binding.invoicesListSwipeRefresh.setOnRefreshListener {
            invoiceViewModel.getInvoices(requireContext())
        }

        invoiceViewModel.getInvoices(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}