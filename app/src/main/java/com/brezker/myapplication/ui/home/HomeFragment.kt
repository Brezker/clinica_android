package com.brezker.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brezker.myapplication.R
import com.brezker.myapplication.databinding.FragmentHomeBinding
import com.brezker.myapplication.extras.Models
import com.brezker.myapplication.extras.PacienteAdapter
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabNuevoPaciente.setOnClickListener {
            var navController = findNavController()
            navController.navigate(R.id.nav_nuevo_paciente)
        }

        obtenerDatos()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun obtenerDatos(){
        //var url("http://yourip:8000/api/login")
        var url = "http://192.168.43.228:8000/api/pacientes"

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
                    var listItems = gson.fromJson(respuesta, Array<Models.Paciente>::class.java)
                    val adapter = PacienteAdapter(listItems.toMutableList())
                    binding.rvDatosPaciente.layoutManager = LinearLayoutManager(context)
                    binding.rvDatosPaciente.adapter = adapter
                }
            }
        })
    }
}