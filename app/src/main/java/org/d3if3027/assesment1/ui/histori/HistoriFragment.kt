package org.d3if3027.assesment1.ui.histori

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.databinding.LayoutHistoriBinding
import org.d3if3027.assesment1.db.DbBmr

class HistoriFragment : Fragment() {

    private val viewModel: HistoriViewModel by lazy {
        val db = DbBmr.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory) [HistoriViewModel::class.java]
    }

    private lateinit var binding: LayoutHistoriBinding
    private lateinit var myAdapter: HistoriAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutHistoriBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoriAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if(it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.hapus_menu){
           hapusData()
           return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusData(){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirm_hapus)
            .setPositiveButton(getString(R.string.hapus)){
                _, _ -> viewModel.hapusData()
            }
            .setNegativeButton(getString(R.string.batal)){dialog, _ -> dialog.cancel()
            }
            .show()
            }
    }
