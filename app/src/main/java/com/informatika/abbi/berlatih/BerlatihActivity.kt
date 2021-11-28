package com.informatika.abbi.berlatih

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.informatika.abbi.data.BerlatihAsetData.soal
import com.informatika.abbi.data.BerlatihData
import com.informatika.abbi.databinding.ActivityBerlatihBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class BerlatihActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBerlatihBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerlatihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuestions()

        binding.btnNext.setOnClickListener{
            finish();
            startActivity(Intent(this, this@BerlatihActivity::class.java));
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
                    binding.tvQuestion.visibility = View.VISIBLE
                    Log.d("This", "onSuccess: $listQuestions, random = $random")

                    binding.btnAnswerA.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerA.text){
                            binding.btnAnswerA.text = "Benar"
                        }else{
                            binding.btnAnswerA.text = "Salah"
                        }
                    }
                    binding.btnAnswerB.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerB.text){
                            binding.btnAnswerB.text = "Benar"
                        }else{
                            binding.btnAnswerB.text = "Salah"
                        }
                    }
                    binding.btnAnswerC.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerC.text){
                            binding.btnAnswerC.text = "Benar"
                        }else{
                            binding.btnAnswerC.text = "Salah"
                        }
                    }
                    binding.btnAnswerD.setOnClickListener{
                        if(getRandomList.answer == binding.btnAnswerD.text){
                            binding.btnAnswerD.text = "Benar"
                        }else{
                            binding.btnAnswerD.text = "Salah"
                        }
                    }

                }catch (e: Exception){
                    Toast.makeText(this@BerlatihActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Toast.makeText(this@BerlatihActivity, "${error.toString()} status: $statusCode", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
