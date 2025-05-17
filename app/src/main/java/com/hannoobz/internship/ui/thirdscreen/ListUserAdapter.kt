package com.hannoobz.internship.ui.thirdscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannoobz.internship.R
import com.hannoobz.internship.data.model.User

class ListUserAdapter(private val context: Context,private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentUser = listUser[position]
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, currentUser)
        }

        holder.tvName.text = buildString {
            append(currentUser.firstName)
            append(" ")
            append(currentUser.lastName)
        }
        holder.tvEmail.text = currentUser.email
        Glide.with(context)
            .load(currentUser.avatar)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgPhoto)
    }


    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, user: User)
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.ivProfilePhoto)
        val tvName: TextView = itemView.findViewById(R.id.tvUserCardName)
        val tvEmail: TextView = itemView.findViewById(R.id.tvUserCardEmail)
    }
}