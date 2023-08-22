package com.example.mobappdevcoursework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobappdevcoursework.model.Published

public class SurveyAdapter() : RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder>() {
    private var list: ArrayList<Published> = ArrayList()
    private lateinit var surveyInterface: SurveyInterface;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SurveyViewHolder (LayoutInflater.from(parent.context). inflate(R.layout.activity_moduleview, parent, false), surveyInterface)

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val list = list[position];
        holder.name.text = list.surTitle;
        holder.name.text = list.date;
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addItems(items: ArrayList<Published>) {
        this.list = items
        notifyDataSetChanged()
    }


    class SurveyViewHolder (itemView: View, surveyListener: SurveyInterface) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.ModuleName);
        val date = itemView.findViewById<TextView>(R.id.EndDate);
        val buttonStart = itemView.findViewById<Button>(R.id.buttonStartQuestion);

        init {
            buttonStart.setOnClickListener{
                surveyListener.onSurveySelect(name.text.toString())
            }
        }
    }

    interface SurveyInterface{
        fun onSurveySelect(surTitle: String);
    }

    fun setOnItemClick(listener : SurveyInterface){
        surveyInterface = listener;
    }

}