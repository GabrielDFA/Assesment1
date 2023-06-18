
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.d3if3027.assesment1.MainActivity
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.databinding.LayoutMakananBinding
import org.d3if3027.assesment1.db.DbBmr
import org.d3if3027.assesment1.network.ApiStatus
import org.d3if3027.assesment1.network.MakananApi
import org.d3if3027.assesment1.ui.HitungViewModel
import org.d3if3027.assesment1.ui.hitung.HitungViewModelFactory


class MakananFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var makananAdapter: MakananAdapter
    private lateinit var binding: LayoutMakananBinding

    private val viewModel: HitungViewModel by lazy {
        val db = DbBmr.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_makanan, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        emptyView = view.findViewById(R.id.emptyView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        makananAdapter = MakananAdapter()
        recyclerView.adapter = makananAdapter
        binding = LayoutMakananBinding.inflate(layoutInflater, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        viewModel.getStatus().observe(viewLifecycleOwner, {
            updateProgress(it)
        })

        viewModel.scheduleUpdater(requireActivity().application)
    }

    private fun fetchData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = MakananApi.service.getMakanan()
                if (response.isNotEmpty()) {
                    makananAdapter.setData(response)
                    recyclerView.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                } else {
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
        }
    }

    private fun updateProgress(status: ApiStatus?) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.loadingIndicator.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            ApiStatus.FAILED -> {
                binding.loadingIndicator.visibility = View.GONE
            }
            else -> {

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }
}

