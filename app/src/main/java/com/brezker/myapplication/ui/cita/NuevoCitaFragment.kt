package com.brezker.myapplication.ui.cita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.brezker.myapplication.EnvUrl
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
//private var idDoctor: Int = 0
private var idDoctor: Int? = null

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
        obtenerDoctor()
        if(json_cita != null) {
            var gson = Gson()
            var objCita = gson.fromJson(json_cita, Models.Cita::class.java)

            id_cita = objCita.id
            binding.edtIdenfermedad.setText(objCita.id_enfermedad)
            binding.edtIdpaciente.setText(objCita.id_paciente)
            //binding.edtIddoctor.setText(objCita.id_doctor)
            binding.edtConsultorio.setText(objCita.consultorio)
            binding.edtDomicilioc.setText(objCita.domicilio)
            binding.edtFecha.setText(objCita.fecha)
            println(objCita.id_doctor)

            val doctorValues = ArrayList<String>()
            //val listaDoctores: Array<Models.Doctor> = obtenerDoctor()
            var countd=0
            /*for (item in listaDoctores) {
                countd=countd+1
                if (item==objCita.id_doctor) {
                    countd=countd-1
                    println("HEllo World "+countd.toString())
                    binding.spiDoctor.setSelection(countd)
                }
            }*/
        }
        /*binding.spiDoctor.{
            //obtenerDoctor()
            //println(obtenerDoctor().toString())
            println("hola")
        }*/
        binding.btnGuardar.setOnClickListener{
            guardarDatos()

        }
        binding.btnEliminar.setOnClickListener{
            eliminarDatos()
        }
        return  view
    }
    private fun obtenerListaDoctores() {
        // ... Código para obtener la lista de doctores ...

        val doctorValues = ArrayList<String>()

        val adapter = ArrayAdapter<String>(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            doctorValues
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spiDoctor.adapter = adapter
    }

    fun guardarDatos() {
        val client = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("id", id_cita.toString())
            .add("id_enfermedad", binding.edtIdenfermedad.text.toString())
            .add("id_paciente", binding.edtIdpaciente.text.toString())
            //.add("id_doctor", binding.edtIddoctor.text.toString())
            .add("consultorio", binding.edtConsultorio.text.toString())
            .add("domicilio", binding.edtDomicilioc.text.toString())
            .add("fecha", binding.edtFecha.text.toString())
            .add("id_doctor",idDoctor.toString())
            .build()

        val request = Request.Builder()
            //.url("http://yourip:8000/api/paciente")
            .url("http://"+EnvUrl.UrlVal+":8000/api/cita")
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
            .url("http://"+EnvUrl.UrlVal+":8000/api/paciente")
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

    private fun obtenerDoctor() {
        val url = "http://" + EnvUrl.UrlVal + ":8000/api/doctores"

        val request = Request.Builder().url(url).get().build()
        val client = OkHttpClient()
        val objGson = Gson()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejar error en caso de fallo
            }

            override fun onResponse(call: Call, response: Response) {
                val respuesta = response.body?.string()

                val listaDoctores = objGson.fromJson(respuesta, Array<Models.Doctor>::class.java)

                if (listaDoctores != null) {
                    var selectedPosition = -1

                    val adapter = object : ArrayAdapter<Models.Doctor>(
                        requireActivity().baseContext,
                        android.R.layout.simple_spinner_item,
                        listaDoctores
                    ) {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val view = super.getView(position, convertView, parent)
                            val doctor = listaDoctores[position]
                            val displayText = "ID: "+"${doctor.id}"
                            //val displayText = "${doctor.id} ${doctor.nombre}"
                            (view as TextView).text = displayText
                            return view
                        }

                        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val view = super.getDropDownView(position, convertView, parent)
                            val doctor = listaDoctores[position]
                            //val displayText = "${doctor.id} ${doctor.nombre}"
                            val displayText = "${doctor.nombre}"
                            (view as TextView).text = displayText
                            return view
                            //binding.spiDoctor.setSelection(4)
                        }
                    }

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    activity?.runOnUiThread {
                        binding.spiDoctor.adapter = adapter

                        var gson = Gson()
                        var objCita = gson.fromJson(json_cita, Models.Cita::class.java)
                        //println(json_cita.toString())
                        if (json_cita != null) {
                            for (i in listaDoctores.indices) {
                                if (objCita.id_doctor == listaDoctores[i].id.toString()) {
                                    selectedPosition = i
                                    binding.spiDoctor.setSelection(selectedPosition)
                                    break
                                }
                            }
                        }

                        binding.spiDoctor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                                val selectedDoctor = listaDoctores[position]
                                idDoctor = selectedDoctor.id
                                println(idDoctor)
                            }

                            override fun onNothingSelected(adapterView: AdapterView<*>) {
                                // Manejar caso de no selección si es necesario
                            }
                        }
                    }
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