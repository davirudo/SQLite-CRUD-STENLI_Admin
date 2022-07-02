package com.example.admin_stenli

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: ArrayList<UserModel> = ArrayList()
    private var onClickItem:((UserModel) -> Unit)? = null
    private var onClickUpdateItem:((UserModel) -> Unit)? = null
    private var onClickDeleteItem:((UserModel) -> Unit)? = null

    fun tambahItem(item : ArrayList<UserModel>) {
        this.userList = item
        notifyDataSetChanged()
    }

    //Menread Data dari RecView
    fun setOnclickItem(callback: (UserModel) -> Unit) {
        this.onClickItem = callback
    }

    //Mengupdate Data dari RecView
    fun setOnclickUpdateItem(callback: (UserModel) -> Unit) {
        this.onClickUpdateItem = callback
    }

    //Mendelete Data dari RecView
    fun setOnclickDeleteItem(callback: (UserModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder (
       LayoutInflater.from(parent.context).inflate(R.layout.template_recycle_card, parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bindView(user)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(user) }
        holder.btnEdit.setOnClickListener{ onClickUpdateItem?.invoke(user)}
        holder.btnHapus.setOnClickListener{ onClickDeleteItem?.invoke(user)}

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvID)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btnEdit = view.findViewById<ImageButton>(R.id.btnEdit)
        var btnHapus = view.findViewById<ImageButton>(R.id.btnHapus)

        fun bindView(user: UserModel) {
            id.text = user.id.toString()
            name.text = user.name
            email.text = user.email
        }
    }
}