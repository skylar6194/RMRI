package com.example.influenza

import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.CheckBox
import android.widget.TextView
import com.example.influenza.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.my_layout.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.influenza.ml.Influenza
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private var selectedGender = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        radioFemale = findViewById<RadioButton>(R.id.radioButtonFemale)
        radioMale = findViewById<RadioButton>(R.id.radioButtonMale)
        radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        var button : Button = findViewById<Button>(R.id.buttonSubmit)

        button.setOnClickListener(View.OnClickListener {

           // binding = ActivityMainBinding.inflate(layoutInflater)
            //val view = binding.root
            //setContentView(view)


            //breathlessness	cough	sore_throat	jaundice	dark_urine	redness_eyes	discharge_eyes	swelling _eyes
            val headache: CheckBox = findViewById(R.id.cb_Headache)
            val diarrhea: CheckBox = findViewById(R.id.cb_Diarrhea)
            val dysentery: CheckBox = findViewById(R.id.cb_Dysentry)
            val seizures:CheckBox = findViewById(R.id.cb_Seizures)
            val nausea: CheckBox = findViewById(R.id.cb_Nausea)
            val vomitting: CheckBox = findViewById(R.id.cb_Vomiting)
            val abdominalpain: CheckBox = findViewById(R.id.cb_abdomenpain)
            val fever:CheckBox = findViewById(R.id.cb_Fever)
            val breathless: CheckBox = findViewById(R.id.cb_Breathless)
            val cough: CheckBox = findViewById(R.id.cb_cough)
            val sorethroat: CheckBox = findViewById(R.id.cb_throat)
            val jaundice:CheckBox = findViewById(R.id.cb_jaundice)
            val urine: CheckBox = findViewById(R.id.cb_urinedark)
            val redeye: CheckBox = findViewById(R.id.cb_redeyes)
            val dischargeye: CheckBox = findViewById(R.id.cb_dischargeeye)
            val swelleye:CheckBox = findViewById(R.id.cb_swelleyes)

            val symptomCheckboxes: List<CheckBox> = listOf(headache,diarrhea,dysentery,seizures,nausea,vomitting,abdominalpain,fever,breathless,cough,sorethroat,jaundice,urine,redeye,dischargeye,swelleye)
            val symptoms = IntArray(symptomCheckboxes.size)

            // Set an OnCheckedChangeListener for each CheckBox
            symptomCheckboxes.forEachIndexed { index, checkBox ->
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    symptoms[index] = if (isChecked) 1 else 0 } }


            // AGE stored as float with age var
            //
            var ed1: EditText = findViewById(R.id.editTextAge)
            var age: Float=ed1.text.toString().toFloat()


            //GENDER
            //selectedGender = if (radioFemale.isChecked) { 0 } else {1}

            // Listen for changes in the RadioGroup
            radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
                selectedGender = when (checkedId) {
                    R.id.radioButtonFemale -> 0  // Female selected
                    R.id.radioButtonMale -> 1    // Male selected
                    else -> 0                   // Default or none selected (shouldn't happen)
                }


                //    //Sorting the Symptoms for transferring to model


                //gender	age	headache	diarrhea	dysentery	seizures	nausea	vomiting	abdominal_pain	fever	chills	breathlessness	cough	sore_throat	jaundice	dark_urine	redness_eyes	discharge_eyes	swelling _eyes

                val byteBuffer: ByteBuffer =ByteBuffer.allocateDirect(76)
                byteBuffer.putFloat(selectedGender.toFloat())
                byteBuffer.putFloat(age)
                symptoms.forEach { symptoms -> byteBuffer.putFloat(symptoms.toFloat()) }


                val model = Influenza.newInstance(this)

// Creates inputs for reference.
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1,19), DataType.FLOAT32)
                inputFeature0.loadBuffer(byteBuffer)


// Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature = outputs.outputFeature0AsTensorBuffer
                val outputValue = outputFeature.floatArray

                val tv: TextView = findViewById(R.id.textViewResult)
                tv.text = outputValue.joinToString(separator = ", ") { "%.2f".format(it) }

                //tv.text = "Negative acc to symptoms ${outputValues[0]}\n High Chances of positive acc to symptoms ${outputValues[1]}"
                //tv.setText("Negative acc to symptoms " + outputValues[0].toString() + "\n High Chances of positive acc to symptoms " + outputValues[1].toString())

                model.close()
                }



        })

    }
}