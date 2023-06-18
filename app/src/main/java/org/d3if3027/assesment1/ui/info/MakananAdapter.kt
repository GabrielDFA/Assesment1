import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.databinding.ItemMakananBinding
import org.d3if3027.assesment1.model.Makanan
import org.d3if3027.assesment1.network.MakananApi

class MakananAdapter : ListAdapter<Makanan, MakananAdapter.MakananViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val binding =
            ItemMakananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MakananViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val currentMakanan = getItem(position)
        holder.bind(currentMakanan)
    }

    fun setData(response: List<Makanan>) {
        submitList(response)
    }

    inner class MakananViewHolder(private val binding: ItemMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(makanan: Makanan) {
            binding.namaMakanan.text = makanan.nama_makanan
            binding.kalori.text = makanan.kalori

            val imageUrl = MakananApi.getMakananUrl(makanan.image_id)
            Glide.with(binding.root.context)
                .load(imageUrl)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.imageId)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Makanan>() {
        override fun areItemsTheSame(oldItem: Makanan, newItem: Makanan): Boolean {
            return oldItem.nama_makanan == newItem.nama_makanan
        }

        override fun areContentsTheSame(oldItem: Makanan, newItem: Makanan): Boolean {
            return oldItem == newItem
        }
    }
}
