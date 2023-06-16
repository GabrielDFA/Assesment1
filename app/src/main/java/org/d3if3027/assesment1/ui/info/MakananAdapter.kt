import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.model.Makanan

class MakananAdapter : ListAdapter<Makanan, MakananAdapter.MakananViewHolder>(MakananDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_makanan, parent, false)
        return MakananViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val makanan = getItem(position)
        holder.bind(makanan)
    }

    inner class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(makanan: Makanan) {
            itemView.nama_makanan.text = makanan.nama_makanan
            itemView.kalori.text = makanan.kalori

            Glide.with(itemView.context)
                .load(makanan.image_id)
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.baseline_broken_image_24) // Error image if the image fails to load
                .into(itemView.image_id)
        }
    }

    private class MakananDiffCallback : DiffUtil.ItemCallback<Makanan>() {
        override fun areItemsTheSame(oldItem: Makanan, newItem: Makanan): Boolean {
            return oldItem.nama_makanan == newItem.nama_makanan
        }

        override fun areContentsTheSame(oldItem: Makanan, newItem: Makanan): Boolean {
            return oldItem == newItem
        }
    }
}
