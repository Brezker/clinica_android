package com.brezker.myapplication.extras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brezker.myapplication.R
import com.google.gson.Gson

class PacienteAdapter (private val dataSet: MutableList<Models.Paciente>) :
    RecyclerView.Adapter<PacienteAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtNombre: TextView
        val txtNss: TextView

        init {
            // Define click listener for the ViewHolder's View.
            txtNombre = view.findViewById(R.id.txtNombre)
            txtNss = view.findViewById(R.id.txtNSS)
        }
    }

    var mRecyclerView: RecyclerView? = null


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView!!)
        mRecyclerView = recyclerView
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item_paciente, viewGroup, false)

        return ViewHolder(view)
    }

    fun removeItem(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.txtNombre.text = dataSet[position]?.nombre
        viewHolder.txtNss.text = dataSet[position]?.nss

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}