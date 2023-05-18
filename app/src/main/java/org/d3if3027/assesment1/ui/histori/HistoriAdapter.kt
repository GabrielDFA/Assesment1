package org.d3if3027.assesment1.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.databinding.ItemHistoriBinding
import org.d3if3027.assesment1.db.EntityBmr
import org.d3if3027.assesment1.model.calculateBMR
import java.text.SimpleDateFormat
import java.util.*


class HistoriAdapter :
    ListAdapter<EntityBmr, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<EntityBmr>() {
                override fun areItemsTheSame(
                    oldData: EntityBmr, newData: EntityBmr
                ): Boolean {
                    return oldData.id == newData.id
                }

                override fun areContentsTheSame(
                    oldData: EntityBmr, newData: EntityBmr
                ): Boolean {
                    return oldData.id == newData.id
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: EntityBmr) = with(binding) {
            val hasil = item.calculateBMR()
            val colorRes = R.color.teal_200
            val background = urutan.background as GradientDrawable
            background.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            bmrText.text = hasil.toString()
            val gender = root.context.getString(
            if (item.isMale) R.string.pria else R.string.wanita)

            dataText.text= "Berat: ${item.berat}, Tinggi: ${item.tinggi}, Usia: ${item.usia}, $gender"
        }
    }


}