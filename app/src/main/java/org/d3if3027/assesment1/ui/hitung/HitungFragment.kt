package org.d3if3027.assesment1.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3027.assesment1.R
import org.d3if3027.assesment1.databinding.FragmentHitungBinding
import org.d3if3027.assesment1.db.DbBmr
import org.d3if3027.assesment1.model.HasilBMR
import org.d3if3027.assesment1.ui.HitungViewModel

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = DbBmr.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.histori_menu ->{
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment
            )
            return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.calculate.setOnClickListener{kaloriHarian()}
        binding.share.setOnClickListener{bagikanData()}
        viewModel.getHasilBMR().observe(requireActivity(),{showResult(it)})
    }

    private fun bagikanData(){
        val berat = binding.beratBadanInp.text.toString()
        val tinggi = binding.tinggiBadanInp.text.toString()
        val usia = binding.usiaInp.text.toString()
        val hasil = binding.textView6.text.toString()

        val message = getString(R.string.template_share,
        berat, tinggi, usia, hasil)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if(shareIntent.resolveActivity(requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }

    }

     fun kaloriHarian() {
        val berat = binding.beratBadanInp.text.toString()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }
         val beratFloat = berat.toFloat()

        val tinggi = binding.tinggiBadanInp.text.toString()
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }
         val tinggiFloat = tinggi.toFloat()

        val selectedID = binding.radioGroup.checkedRadioButtonId
        if(selectedID == -1){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val usia = binding.usiaInp.text.toString()

        if(TextUtils.isEmpty(usia)){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }
         val usiaInt = usia.toInt()

        val isMale = selectedID == R.id.buttonPria

        viewModel.calculateBMR(beratFloat,tinggiFloat,usiaInt, isMale)
    }

    private fun showResult(result: HasilBMR?) {
        if (result == null) return
        binding.textView6.text = "Hasil BMR = " + result.hasil
    }

}