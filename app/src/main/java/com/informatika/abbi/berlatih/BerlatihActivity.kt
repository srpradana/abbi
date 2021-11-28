package com.informatika.abbi.berlatih

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.informatika.abbi.R
import com.informatika.abbi.data.BerlatihAsetData.soal
import com.informatika.abbi.data.BerlatihData
import com.informatika.abbi.databinding.ActivityBerlatihBinding
import com.informatika.abbi.databinding.CustomCorrectAnswerDialogBinding
import com.informatika.abbi.databinding.CustomWrongAnswerDialogBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.conn.ConnectTimeoutException
import org.json.JSONArray

class BerlatihActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBerlatihBinding
    private lateinit var correctBinding: CustomCorrectAnswerDialogBinding
    private lateinit var wrongBinding: CustomWrongAnswerDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerlatihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuestions()

        binding.btnClose.setOnClickListener{
            finish()
        }
        binding.btnNext.setOnClickListener{
            finish()
            val intent = Intent(this, this@BerlatihActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getQuestions(){
        binding.pbBerlatih.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://abbi-api.herokuapp.com/api/questions"
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val listQuestions = ArrayList<BerlatihData>()
                val result = String(responseBody)
                Log.d("ThisIS", "onSuccess: $result")
                try{
                    val jsonArray = JSONArray(result)

                    for(i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val answer = jsonObject.getString("answer")
                        val images = jsonObject.getString("question")
                        val options = ArrayList<String>()
                        val optionsArr = jsonObject.getJSONArray("options")
                        for(j in 0 until optionsArr.length()){
                            options.add(optionsArr.getString(j))
                        }
                        listQuestions.add(
                            BerlatihData(
                            answer,
                            images,
                            options
                        ) )
                        Log.d("This", "onSuccess: $listQuestions")
                    }

                    val random = (0 until listQuestions.size).random()
                    val getRandomList = listQuestions[random]
                    val imageData = soal.getValue("${getRandomList.images}")
                    binding.pbBerlatih.visibility = View.GONE
                    Glide.with(this@BerlatihActivity).load(imageData).into(binding.imgQuestion)
                    binding.btnAnswerA.text = getRandomList.options?.get(0).toString()
                    binding.btnAnswerB.text = getRandomList.options?.get(1).toString()
                    binding.btnAnswerC.text = getRandomList.options?.get(2).toString()
                    binding.btnAnswerD.text = getRandomList.options?.get(3).toString()
                    allViewVisible()
                    Log.d("This", "onSuccess: $listQuestions, random = $random")

                    binding.btnAnswerA.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerA.text){
                            showAlertDialogCorrect(getRandomList.answer.toString())
                        }else{
                            showAlertDialogWrong(getRandomList.answer.toString())
                            binding.btnAnswerA.setBackgroundColor(Color.RED)
                        }
                        buttonInQuiz(false)
                    }
                    binding.btnAnswerB.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerB.text){
                            showAlertDialogCorrect(getRandomList.answer.toString())
                            binding.btnAnswerB.setBackgroundColor(Color.GREEN)
                        }else{
                            showAlertDialogWrong(getRandomList.answer.toString())
                            binding.btnAnswerB.setBackgroundColor(Color.RED)
                        }
                        buttonInQuiz(false)
                    }
                    binding.btnAnswerC.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerC.text){
                            showAlertDialogCorrect(getRandomList.answer.toString())
                            binding.btnAnswerC.setBackgroundColor(Color.GREEN)
                        }else{
                            showAlertDialogWrong(getRandomList.answer.toString())
                            binding.btnAnswerC.setBackgroundColor(Color.RED)
                        }
                        buttonInQuiz(false)
                    }
                    binding.btnAnswerD.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerD.text){
                            showAlertDialogCorrect(getRandomList.answer.toString())
                            binding.btnAnswerD.setBackgroundColor(Color.GREEN)
                        }else{

                            showAlertDialogWrong(getRandomList.answer.toString())
                            binding.btnAnswerD.setBackgroundColor(Color.RED)
                        }
                        buttonInQuiz(false)
                    }

                }catch (e: Exception){
                    Toast.makeText(this@BerlatihActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                if (error is ConnectTimeoutException) {
                    Toast.makeText(
                        this@BerlatihActivity,
                        "Periksa Kembali Koneksi Internet Anda",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@BerlatihActivity,
                        "${error.toString()} status: $statusCode",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    @SuppressLint("SetTextI18n")
    fun showAlertDialogCorrect(answer: String){
        correctBinding = CustomCorrectAnswerDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this@BerlatihActivity)
        builder.setView(correctBinding.root)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

        correctBinding.tvJawabanBenar.text = "Jawaban benar: $answer"

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 2500)
    }

    @SuppressLint("SetTextI18n")
    fun showAlertDialogWrong(answer: String){
        wrongBinding = CustomWrongAnswerDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this@BerlatihActivity)
        builder.setView(wrongBinding.root)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

        wrongBinding.tvJawabanBenar.text = "Jawaban benar: $answer"

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 2000)
    }

    fun buttonInQuiz(state: Boolean){
        with(binding) {
            btnAnswerA.isEnabled = state
            btnAnswerB.isEnabled = state
            btnAnswerC.isEnabled = state
            btnAnswerD.isEnabled = state
            btnNext.isEnabled = !state
        }
    }

    fun allViewVisible(){
        with(binding){
            btnAnswerA.visibility = View.VISIBLE
            btnAnswerB.visibility = View.VISIBLE
            btnAnswerC.visibility = View.VISIBLE
            btnAnswerD.visibility = View.VISIBLE
            tvQuestion.visibility = View.VISIBLE
            btnClose.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }
    }
}
