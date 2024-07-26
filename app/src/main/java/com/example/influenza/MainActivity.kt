package com.example.influenza
import android.R.id.input
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.influenza.databinding.ActivityMainBinding
import com.example.influenza.ml.Influenza
import com.google.flatbuffers.ByteBufferUtil
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.TensorFlowLite
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import kotlin.reflect.typeOf


class MainActivity : AppCompatActivity() {
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private var selectedGender = 0f


    private lateinit var chk_headache: CheckBox
    private var headache_val =0f

    private lateinit var chk_diarrhea: CheckBox
    private var diarrhea_val =0f

    private lateinit var chk_dysentry: CheckBox
    private var dysentry_val =0f

    private lateinit var chk_seizures: CheckBox
    private var seizures_val =0f

    private lateinit var chk_nausea: CheckBox
    private var nausea_val =0f

    private lateinit var chk_vomiting: CheckBox
    private var vomiting_val =0f

    private lateinit var chk_abdomenpain: CheckBox
    private var abdomenpain_val =0f

    private lateinit var chk_fever: CheckBox
    private var fever_val =0f

    private lateinit var chk_chills: CheckBox
    private var chills_val =0f

    private lateinit var chk_breathless: CheckBox
    private var breathless_val =0f

    private lateinit var chk_cough: CheckBox
    private var cough_val =0f

    private lateinit var chk_sorethroat: CheckBox
    private var sorethroat_val =0f

    private lateinit var chk_jaundice: CheckBox
    private var jaundice_val =0f

    private lateinit var chk_darkurine: CheckBox
    private var darkurine_val =0f

    private lateinit var chk_rednesseye: CheckBox
    private var rednesseye_val =0f

    private lateinit var chk_dischargeeye: CheckBox
    private var discahrgeeye_val =0f

    private lateinit var chk_swellingeye: CheckBox
    private var swellingeye_val =0f

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        enableEdgeToEdge()
        radioFemale = findViewById<RadioButton>(R.id.radioButtonFemale)
        radioMale = findViewById<RadioButton>(R.id.radioButtonMale)
        radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)

        chk_headache = findViewById<CheckBox>(R.id.cb_Headache)
        chk_diarrhea = findViewById<CheckBox>(R.id.cb_Diarrhea)
        chk_dysentry = findViewById<CheckBox>(R.id.cb_Dysentry)
        chk_seizures = findViewById<CheckBox>(R.id.cb_Seizures)
        chk_nausea = findViewById<CheckBox>(R.id.cb_Nausea)
        chk_vomiting = findViewById<CheckBox>(R.id.cb_Vomiting)
        chk_abdomenpain = findViewById<CheckBox>(R.id.cb_abdomenpain)
        chk_fever = findViewById<CheckBox>(R.id.cb_Fever)
        chk_chills = findViewById<CheckBox>(R.id.cb_chills)
        chk_breathless = findViewById<CheckBox>(R.id.cb_Breathless)
        chk_cough = findViewById<CheckBox>(R.id.cb_cough)
        chk_sorethroat = findViewById<CheckBox>(R.id.cb_throat)
        chk_jaundice = findViewById<CheckBox>(R.id.cb_jaundice)
        chk_darkurine = findViewById<CheckBox>(R.id.cb_urinedark)
        chk_rednesseye = findViewById<CheckBox>(R.id.cb_redeyes)
        chk_dischargeeye = findViewById<CheckBox>(R.id.cb_dischargeeye)
        chk_swellingeye = findViewById<CheckBox>(R.id.cb_swelleyes)


        headache_val = if (chk_headache.isChecked) 1f else 0f
        diarrhea_val = if (chk_diarrhea.isChecked) 1f else 0f
        dysentry_val = if (chk_dysentry.isChecked) 1f else 0f
        seizures_val = if (chk_seizures.isChecked) 1f else 0f
        nausea_val = if (chk_nausea.isChecked) 1f else 0f
        vomiting_val = if (chk_vomiting.isChecked) 1f else 0f
        abdomenpain_val = if (chk_abdomenpain.isChecked) 1f else 0f
        fever_val = if (chk_fever.isChecked) 1f else 0f
        chills_val = if (chk_chills.isChecked) 1f else 0f
        breathless_val = if (chk_breathless.isChecked) 1f else 0f
        cough_val = if (chk_cough.isChecked) 1f else 0f
        sorethroat_val = if (chk_sorethroat.isChecked) 1f else 0f
        jaundice_val = if (chk_jaundice.isChecked) 1f else 0f
        darkurine_val = if (chk_darkurine.isChecked) 1f else 0f
        rednesseye_val = if (chk_rednesseye.isChecked) 1f else 0f
        discahrgeeye_val = if (chk_dischargeeye.isChecked) 1f else 0f
        swellingeye_val = if (chk_swellingeye.isChecked) 1f else 0f

        selectedGender = if (radioFemale.isChecked) 0f else 1f


        var button: Button = findViewById<Button>(R.id.buttonSubmit)


        button.setOnClickListener(View.OnClickListener {





            // AGE stored as float with age var
            //
            var ed1: EditText = binding.editTextAge
            var age: Float = ed1.text.toString().toFloat()






            val model = Influenza.newInstance(this)
// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 19), DataType.FLOAT32)

            inputFeature0.loadArray(floatArrayOf(selectedGender.toFloat(),age.toFloat(),headache_val.toFloat(),diarrhea_val.toFloat(),
                dysentry_val.toFloat(),seizures_val.toFloat(),nausea_val.toFloat(),vomiting_val.toFloat(),abdomenpain_val.toFloat(),
                fever_val.toFloat(),chills_val.toFloat(),breathless_val.toFloat(),cough_val.toFloat(),sorethroat_val.toFloat(),
                jaundice_val.toFloat(),darkurine_val.toFloat(),rednesseye_val.toFloat(),discahrgeeye_val.toFloat(),swellingeye_val.toFloat()))

                //inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.

            val outputs = model.process(inputFeature0)
            //val outputFeature0 = outputs.outputFeature0AsTensorBuffer



           // val outputFeature0 = outputs.outputFeature0AsTensorBuffer.getFloatValue(0)
           val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
            


            var result=outputFeature0[0]


            val negPercent = String.format("%.2f", result * 100)
            val posPercent = String.format("%.2f", (1.0 - result) * 100)


            val tv: TextView = findViewById(R.id.textViewResult)

            //if (posPercent.toFloat()>75f && posPercent.toFloat()<90f) { tv.text="Chance of Influenza A: " +posPercent+"%" + "\nYou May have Mild Respiratory illness" }

            if(posPercent.toFloat()>75f){tv.text= "Chance of Influenza A: " +posPercent+"%" +"\nPlease get tested for Influenza A !!"}

            else{tv.text="Chance of Influenza A: " +posPercent+"%" +"\nYou are just having a bad day!!!\nGet rest & Eat Good!!!"}


          //     Log.d("Output Debug","Negative:" + negPercent + "%" )
            //   Log.d("Output Debug","Positive:" + posPercent + "%")


            //val tv: TextView = findViewById(R.id.textViewResult)
//            tv.text = "Negative: ${negPercent}%\n Positive: ${posPercent}%"

        })


        radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
            selectedGender = when (checkedId) {
                R.id.radioButtonFemale -> 0f
                R.id.radioButtonMale -> 1f
                else -> 0f}}

        chk_headache.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { headache_val = 1f } else
            { headache_val = 0f } }

        chk_diarrhea.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { diarrhea_val = 1f } else
            { diarrhea_val = 0f } }

        chk_dysentry.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { dysentry_val = 1f } else
            { dysentry_val = 0f } }

        chk_seizures.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { seizures_val = 1f } else
            { seizures_val = 0f } }

        chk_nausea.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { nausea_val = 1f } else
            { nausea_val = 0f } }

        chk_vomiting.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { vomiting_val = 1f } else
            { vomiting_val = 0f } }

        chk_abdomenpain.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { abdomenpain_val = 1f } else
            { abdomenpain_val = 0f }}

        chk_fever.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { fever_val = 1f } else
            { fever_val = 0f }}

        chk_chills.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { chills_val = 1f } else
            { chills_val = 0f }}

        chk_breathless.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { breathless_val = 1f } else
            { breathless_val = 0f }}

        chk_cough.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { cough_val = 1f } else
            { cough_val = 0f }}

        chk_sorethroat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { sorethroat_val = 1f } else
            { sorethroat_val = 0f }}

        chk_jaundice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { jaundice_val = 1f } else
            { jaundice_val = 0f }}

        chk_darkurine.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { darkurine_val = 1f } else
            { darkurine_val = 0f }}

        chk_rednesseye.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { rednesseye_val = 1f } else
            { rednesseye_val = 0f }}

        chk_dischargeeye.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { discahrgeeye_val = 1f } else
            { discahrgeeye_val = 0f }}

        chk_swellingeye.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { swellingeye_val = 1f } else
            { swellingeye_val = 0f }}


    }}
