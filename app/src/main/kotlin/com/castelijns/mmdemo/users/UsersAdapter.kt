package com.castelijns.mmdemo.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.models.User
import kotlinx.android.synthetic.main.row_user.view.*

class UsersAdapter(
        private val dataSet: List<User>
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private var itemClickListener: ItemClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = dataSet[position]
        holder.itemView.apply {
            tv_name.text = user.name
            tv_username.text = user.username
            tv_email.text = user.email
        }

        holder.itemView.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.onClick(user, holder.itemView.tv_username, holder.itemView.tv_email)
            }
        }

        // For shared element transitions.
        ViewCompat.setTransitionName(holder.itemView.tv_username, String.format(
                "%s.%s", "username", user.username))
        ViewCompat.setTransitionName(holder.itemView.tv_email, String.format(
                "%s.%s", "email", user.email))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

}

interface ItemClickListener {
    fun onClick(user: User, tvUsername: TextView, tvEmail: TextView)
}
