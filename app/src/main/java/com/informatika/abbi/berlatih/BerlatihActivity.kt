package com.informatika.abbi.berlatih

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.data.BerlatihAsetData.soal
import com.informatika.abbi.data.BerlatihData
import com.informatika.abbi.databinding.ActivityBerlatihBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class BerlatihActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBerlatihBinding

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("questions")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerlatihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuestions()

        /*collection.limit(1).get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val images = document.getString("question")
                    val options = document.get("options") as ArrayList<String>
                    val answer = document.getString("answer")
                    val optionA = options[0]
                    val optionB = options[1]
                    val optionC = options[2]
                    val optionD = options[3]
                    val image = soal.getValue(key = "$images")
                    Glide.with(this)
                        .load(image)
                        .into(binding.imgQuestion)
                    binding.btnAnswerA.text = optionA
                    binding.btnAnswerB.text = optionB
                    binding.btnAnswerC.text = optionC
                    binding.btnAnswerD.text = optionD

                    binding.btnAnswerA.setOnClickListener{
                        Log.d(TAG, "onCreate: $optionA == $answer")
                        if(optionA == answer) {
                            binding.btnAnswerA.text = "Benar"
                        }else{
                            binding.btnAnswerA.text = "Salah"
                        }
                    }
                    binding.btnAnswerB.setOnClickListener{
                        if(optionB == answer)
                            binding.btnAnswerB.text = "Benar"
                        else
                            binding.btnAnswerB.text = "Salah"
                    }
                    binding.btnAnswerC.setOnClickListener{
                        if(binding.btnAnswerC.text == answer)
                            binding.btnAnswerC.setBackgroundColor(Color.GREEN)
                        else
                            binding.btnAnswerC.setBackgroundColor(Color.RED)
                    }
                    binding.btnAnswerD.setOnClickListener{
                        if(binding.btnAnswerD.text == answer)
                            binding.btnAnswerD.setBackgroundColor(Color.GREEN)
                        else
                            binding.btnAnswerD.setBackgroundColor(Color.RED)
                    }

                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Exception",e )
            }
*/
        binding.btnNext.setOnClickListener{
            finish();
            startActivity(Intent(this, this@BerlatihActivity::class.java));
        }
    }

    private fun getQuestions(){
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

                    val random = (-1 until listQuestions.size).random()
                    val getRandomList = listQuestions[random]
                    val imageData = soal.getValue("${getRandomList.images}")
                    Glide.with(this@BerlatihActivity).load(imageData).into(binding.imgQuestion)
                    binding.btnAnswerA.text = getRandomList.options?.get(0).toString()
                    binding.btnAnswerB.text = getRandomList.options?.get(1).toString()
                    binding.btnAnswerC.text = getRandomList.options?.get(2).toString()
                    binding.btnAnswerD.text = getRandomList.options?.get(3).toString()
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
                TODO("Not yet implemented")
            }

        })
    }
}
