package org.d3if3027.assesment1.ui

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
import org.d3if3027.assesment1.model.HasilBMR

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about){
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment
            )
            return true
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
        val beratFloat = berat.toFloat()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tinggi = binding.tinggiBadanInp.text.toString()
        val tinggiFloat = tinggi.toFloat()
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedID = binding.radioGroup.checkedRadioButtonId
        if(selectedID == -1){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }


        val usia = binding.usiaInp.text.toString()
        val usiaInt = usia.toInt()
        if(TextUtils.isEmpty(usia)){
            Toast.makeText(context, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedID == R.id.buttonPria

        viewModel.calculateBMR(beratFloat,tinggiFloat,usiaInt, isMale)
    }

    private fun showResult(result: HasilBMR?) {
        if (result == null) return
        binding.textView6.text = "Hasil BMR = " + result.hasil
    }

}