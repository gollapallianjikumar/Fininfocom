package com.fininfo.loginexample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fininfo.loginexample.R
import com.fininfo.loginexample.ui.model.UserData
import kotlinx.android.synthetic.main.adapter_layout.view.*

class DataAdapter(private val userData:ArrayList<UserData>) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {


    class DataViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        fun bind(user:UserData)
        {
            itemView.nameTv.text=user.name
            itemView.ageTv.text= user.age.toString()
            itemView.cityTv.text=user.city
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_layout,parent,false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(userData[position])

    override fun getItemCount(): Int =userData.size

    fun addData(list: ArrayList<UserData>)
    {
        userData.addAll(list)
    }
}