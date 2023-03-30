package org.d3if3027.assesment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import org.d3if3027.assesment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener{kaloriHarian()}

    }



     fun kaloriHarian() {
        val berat = binding.beratBadanInp.text.toString()
        val beratFloat = berat.toFloat()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(this, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tinggi = binding.tinggiBadanInp.text.toString()
        val tinggiFloat = tinggi.toFloat()
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(this, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedID = binding.radioGroup.checkedRadioButtonId
        if(selectedID == -1){
            Toast.makeText(this, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }


        val usia = binding.usiaInp.text.toString()
        val usiaInt = usia.toInt()
        if(TextUtils.isEmpty(usia)){
            Toast.makeText(this, R.string.pesan_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedID == R.id.buttonPria

        val result = calculateBMR(beratFloat,tinggiFloat,usiaInt, isMale)

        binding.textView6.text = "Your BMR is: $result"

    }
    fun calculateBMR(weight: Float, height: Float, age: Int, isMale: Boolean): Float {
        val bmr: Float
        if (isMale) {
            bmr = (66 + (9.6 * weight) + (1.8 * height) - (4.7 * age)).toFloat()
        } else {
            bmr = (65 + (13.7 * weight) + (5 * height) - (6.8 * age)).toFloat()
        }
        return bmr
    }

}