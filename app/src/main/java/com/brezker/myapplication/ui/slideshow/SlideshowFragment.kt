package com.brezker.myapplication.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brezker.myapplication.databinding.FragmentSlideshowBinding
import com.brezker.myapplication.extras.EnfermedadAdapter
import com.brezker.myapplication.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        obtenerDatos()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun obtenerDatos(){
        //var url("http://yourip:8000/api/login")
        var url = "http://192.168.43.228:8000/api/enfermedades"

        var request = Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            //.header("Autorization", "Bearer" + TOKEN)
            .get()
            .build()
        val client = OkHttpClient()
        var gson = Gson()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Ocurrio un error: " + e.message.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var respuesta = response.body?.string()

                println("Respuesta: "+ respuesta)

                activity?.runOnUiThread {
                    var listItems = gson.fromJson(respuesta, Array<Models.Enfermedad>::class.java)
                    val adapter = EnfermedadAdapter(listItems.toMutableList())
                    binding.rvDatosEnfermedad.layoutManager = LinearLayoutManager(context)
                    binding.rvDatosEnfermedad.adapter = adapter
                }
            }
        })
    }
}