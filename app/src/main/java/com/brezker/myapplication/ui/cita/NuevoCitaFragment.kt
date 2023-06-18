package com.brezker.myapplication.ui.cita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.brezker.myapplication.R
import com.brezker.myapplication.databinding.FragmentNuevoCitaBinding
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "json_cita"
private const val ARG_PARAM2 = "param2"
private var id_cita: Int = 0
private lateinit var binding: FragmentNuevoCitaBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoCitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoCitaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var json_cita: String? = null
    private var param2: String? = null

    private var _binding: FragmentNuevoCitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_cita = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_nuevo_cita, container, false)
        _binding = FragmentNuevoCitaBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_cita != null) {
            var gson = Gson()
            var objCita = gson.fromJson(json_cita, Models.Cita::class.java)

            id_cita = objCita.id
            binding.edtIdenfermedad.setText(objCita.id_enfermedad)
            binding.edtIdpaciente.setText(objCita.id_paciente)
            binding.edtIddoctor.setText(objCita.id_doctor)
            binding.edtConsultorio.setText(objCita.consultorio)
            binding.edtDomicilioc.setText(objCita.domicilio)
            binding.edtFecha.setText(objCita.fecha)
        }

        binding.btnGuardar.setOnClickListener{
            guardarDatos()
        }
        binding.btnEliminar.setOnClickListener{
            eliminarDatos()
        }
        return  view
    }

    fun guardarDatos() {
        val client = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("id", id_cita.toString())
            .add("id_enfermedad", binding.edtIdenfermedad.text.toString())
            .add("id_paciente", binding.edtIdpaciente.text.toString())
            .add("id_doctor", binding.edtIddoctor.text.toString())
            .add("consultorio", binding.edtConsultorio.text.toString())
            .add("domicilio", binding.edtDomicilioc.text.toString())
            .add("fecha", binding.edtFecha.text.toString())
            .build()

        val request = Request.Builder()
            //.url("http://yourip:8000/api/paciente")
            .url("http://192.168.100.21:8000/api/cita")
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Ocurrio un error: " + e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var respuesta = response.body?.string()
                println(respuesta)
                activity?.runOnUiThread {
                    activity?.onBackPressed()
                }
            }
        })
    }

    fun eliminarDatos(){
        val client = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("id", id_cita.toString())
            .build()

        val request = Request.Builder()
            //.url("http://yourip:8000/api/paciente")
            .url("http://192.168.0.7:8000/api/cita/delete")
            .post(formBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Ocurrio un error: " + e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var respuesta = response.body?.string()
                println(respuesta)
                activity?.runOnUiThread {
                    activity?.onBackPressed()
                }
            }
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevoCitaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoCitaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}