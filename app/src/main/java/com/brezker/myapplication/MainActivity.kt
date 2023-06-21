package com.brezker.myapplication

//import android.app.DownloadManager.Request
import android.content.Intent
import android.graphics.ColorSpace.Model
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.brezker.myapplication.databinding.ActivityMainBinding
import com.brezker.myapplication.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.btnLogin.setOnClickListener{
            fnLogin();
        }
        setContentView(view)

    }
    fun fnLogin(){
        //Toast.makeText(baseContext, binding.txtEmail.text, Toast.LENGTH_LONG).show();
        //Toast.makeText(baseContext, binding.txtPass.text, Toast.LENGTH_LONG).show();

        val client = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("email", binding.txtEmail.text.toString())
            .add("password", binding.txtPass.text.toString())
            .build()


        val request = Request.Builder()
            //.url("http://yourip:8000/api/login")
            .url("http://192.168.43.228:8000/api/login")
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread{
                    Toast.makeText(baseContext, "Error: " + e.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                //println(response.body?.string())

                var objGson = Gson()
                var respuesta = response.body?.string()
                var objResp = objGson.fromJson(respuesta, Models.RespLogin::class.java)

                if(objResp.token == ""){
                    runOnUiThread {
                        Toast.makeText(baseContext, objResp.error, Toast.LENGTH_LONG).show()
                    }
                } else {
                    /*
                    runOnUiThread {
                        Toast.makeText(baseContext, "Correcto!", Toast.LENGTH_LONG).show()
                    }*/
                    val intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

}