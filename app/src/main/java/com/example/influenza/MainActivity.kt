package com.example.influenza
import android.os.Bundle
import android.util.Log

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
import com.example.influenza.ml.Influenza
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.lang.Float.intBitsToFloat
import java.nio.ByteBuffer
import java.nio.ByteOrder

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
//        val symptomCheckboxes: List<CheckBox> =  arrayListOf(
//            binding.cbHeadache,
//            binding.cbDiarrhea,
//            binding.cbDysentry,
//            binding.cbSeizures,
//            binding.cbNausea,
//            binding.cbVomiting,
//            binding.cbAbdomenpain,
//            binding.cbFever,
//            binding.cbChills,
//            binding.cbBreathless,
//            binding.cbCough,
//            binding.cbThroat,
//            binding.cbJaundice,
//            binding.cbUrinedark,
//            binding.cbRedeyes,
//            binding.cbDischargeeye,
//            binding.cbSwelleyes)

//        val headache: CheckBox = findViewById(R.id.cb_Headache)
//        val diarrhea: CheckBox = findViewById(R.id.cb_Diarrhea)
//        val dysentery: CheckBox = findViewById(R.id.cb_Dysentry)
//        val seizures: CheckBox = findViewById(R.id.cb_Seizures)
//        val nausea: CheckBox = findViewById(R.id.cb_Nausea)
//        val vomitting: CheckBox = findViewById(R.id.cb_Vomiting)
//        val abdominalpain: CheckBox = findViewById(R.id.cb_abdomenpain)
//        val fever: CheckBox = findViewById(R.id.cb_Fever)
//        val breathless: CheckBox = findViewById(R.id.cb_Breathless)
//        val cough: CheckBox = findViewById(R.id.cb_cough)
//        val sorethroat: CheckBox = findViewById(R.id.cb_throat)
//        val jaundice: CheckBox = findViewById(R.id.cb_jaundice)
//        val urine: CheckBox = findViewById(R.id.cb_urinedark)
//        val redeye: CheckBox = findViewById(R.id.cb_redeyes)
//        val dischargeye: CheckBox = findViewById(R.id.cb_dischargeeye)
//        val swelleye: CheckBox = findViewById(R.id.cb_swelleyes)

        button.setOnClickListener(View.OnClickListener {



            //breathlessness	cough	sore_throat	jaundice	dark_urine	redness_eyes	discharge_eyes	swelling _eyes
//            val headache: CheckBox = findViewById(R.id.cb_Headache)
//            val diarrhea: CheckBox = findViewById(R.id.cb_Diarrhea)
//            val dysentery: CheckBox = findViewById(R.id.cb_Dysentry)
//            val seizures: CheckBox = findViewById(R.id.cb_Seizures)
//            val nausea: CheckBox = findViewById(R.id.cb_Nausea)
//            val vomitting: CheckBox = findViewById(R.id.cb_Vomiting)
//            val abdominalpain: CheckBox = findViewById(R.id.cb_abdomenpain)
//            val fever: CheckBox = findViewById(R.id.cb_Fever)
//            val breathless: CheckBox = findViewById(R.id.cb_Breathless)
//            val cough: CheckBox = findViewById(R.id.cb_cough)
//            val sorethroat: CheckBox = findViewById(R.id.cb_throat)
//            val jaundice: CheckBox = findViewById(R.id.cb_jaundice)
//            val urine: CheckBox = findViewById(R.id.cb_urinedark)
//            val redeye: CheckBox = findViewById(R.id.cb_redeyes)
//            val dischargeye: CheckBox = findViewById(R.id.cb_dischargeeye)
//            val swelleye: CheckBox = findViewById(R.id.cb_swelleyes)

//            val symptomCheckboxes: List<CheckBox> =  arrayListOf(
//                binding.cbHeadache,
//                binding.cbDiarrhea,
//                binding.cbDysentry,
//                binding.cbSeizures,
//                binding.cbNausea,
//                binding.cbVomiting,
//                binding.cbAbdomenpain,
//                binding.cbFever,
//                binding.cbChills,
//                binding.cbBreathless,
//                binding.cbCough,
//                binding.cbThroat,
//                binding.cbJaundice,
//                binding.cbUrinedark,
//                binding.cbRedeyes,
//                binding.cbDischargeeye,
//                binding.cbSwelleyes)

            //var val_headache= 0.0f

            // Set an OnCheckedChangeListener for each CheckBox
            //symptomCheckboxes.forEachIndexed { index, checkBox ->
              //  checkBox.setOnCheckedChangeListener { _, isChecked ->
                //    symptoms[index] = if (isChecked) 1 else 0
                //} }
            //headache.setOnCheckedChangeListener { _, isChecked -> val_headache= 1.0f  }
//            { view ->
//                if ((view as CheckBox).isChecked) {
//                    symptoms[0]= 1.0f
//                } else {
//                    symptoms[0]=0.0f
//                } }

            // AGE stored as float with age var
            //
            var ed1: EditText = binding.editTextAge
            var age: Float = ed1.text.toString().toFloat()


            //GENDER
            //selectedGender = if (radioFemale.isChecked) { 0 } else {1}

            // Listen for changes in the RadioGroup
            //radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
              //  selectedGender = when (checkedId) {
                ///    R.id.radioButtonFemale -> 0  // Female selected
                   // R.id.radioButtonMale -> 1    // Male selected
                    //else -> 0 }}                  // Default or none selected (shouldn't happen)


            //    //Sorting the Symptoms for transferring to model
//            Log.d("AppDebug", "selectedGender: $selectedGender")
//            Log.d("AppDebug", "age: $age")
//            Log.d("AppDebug", "headache: $headache_val")
//            Log.d("AppDebug", "diarrhea: $diarrhea_val")
//            Log.d("AppDebug", "dysentry: $dysentry_val")
//            Log.d("AppDebug", "seizure: $seizures_val")
//            Log.d("AppDebug", "nasusea: $nausea_val")
//            Log.d("AppDebug", "vomiting: $vomiting_val")
//            Log.d("AppDebug", "abdomen pain: $abdomenpain_val")
//            Log.d("AppDebug", "fever: $fever_val")
//            Log.d("AppDebug", "chills: $chills_val")
//            Log.d("AppDebug", "breathless: $breathless_val")
//            Log.d("AppDebug", "cough: $cough_val")
//            Log.d("AppDebug", "sore throat: $sorethroat_val")
//            Log.d("AppDebug", "jaundice: $jaundice_val")
//            Log.d("AppDebug", "dark urine: $darkurine_val")
//            Log.d("AppDebug", "red eye: $rednesseye_val")
//            Log.d("AppDebug", "discharge eye: $discahrgeeye_val")
//            Log.d("AppDebug", "swelling eye: $swellingeye_val")



            //gender	age	headache	diarrhea	dysentery	seizures	nausea	vomiting	abdominal_pain	fever	chills	breathlessness	cough	sore_throat	jaundice	dark_urine	redness_eyes	discharge_eyes	swelling _eyes

            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4*19)
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(selectedGender))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(age))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(headache_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(diarrhea_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(dysentry_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(seizures_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(nausea_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(vomiting_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(abdomenpain_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(fever_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(chills_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(breathless_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(cough_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(sorethroat_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(jaundice_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(darkurine_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(rednesseye_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(discahrgeeye_val))
            byteBuffer.putInt(java.lang.Float.floatToRawIntBits(swellingeye_val))



            byteBuffer.position(0) // Reset position to beginning of buffer
            val floatValues = mutableListOf<Float>()
            while (byteBuffer.hasRemaining()) {
                val floatValue = byteBuffer.float
                floatValues.add(floatValue) }

            floatValues.forEachIndexed { index, floatValue -> //FOR DISPLAYING the values to Logcat
                Log.d("App Debug", "Float value at index $index: $floatValue") }


            val model = Influenza.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 19), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer


// Convert float to raw int bits

// Convert raw int bits to int


// Now intValue holds the integer representation of the float value
            println("Int value: $outputFeature0")
          //  Log.d("App Debug","output is ${intValue}")
         // Log.d("App Debug","Output is${outputFeature0}")



// Releases model resources if no longer used.
            model.close()

            //Log.d("App Debug","Output from model ${result}")

            //val tv: TextView = findViewById(R.id.textViewResult)


            //tv.text = "Negative acc to symptoms ${outputValues[0]}\n High Chances of positive acc to symptoms ${outputValues[1]}"
            //tv.setText("Negative acc to symptoms " + outputValues[0].toString() + "\n High Chances of positive acc to symptoms " + outputValues[1].toString())

            model.close()
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
