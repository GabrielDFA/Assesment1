import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3027.assesment1.R

class MakananFragment : Fragment() {
   private lateinit var makananAdapter: MakananAdapter
   private lateinit var makananViewModel: MakananViewModel

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      return inflater.inflate(R.layout.item_makanan, container, false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      makananAdapter = MakananAdapter()

      recyclerView.apply {
         layoutManager = LinearLayoutManager(context)
         adapter = makananAdapter
      }

      makananViewModel = ViewModelProvider(this).get(MakananViewModel::class.java)

      makananViewModel.makanan.observe(viewLifecycleOwner, Observer { makananList ->
         if (makananList.isNotEmpty()) {
            makananAdapter.submitList(makananList)
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
         } else {
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
         }
      })
   }

   override fun onStart() {
      super.onStart()
      makananViewModel.fetchMakanan()
   }
}
