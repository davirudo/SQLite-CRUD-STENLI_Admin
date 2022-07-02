package com.example.admin_stenli

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardActivity : AppCompatActivity() {

    private lateinit var etNamaUser: EditText
    private lateinit var etEmailUser: EditText
    private lateinit var btnTambah: ImageButton
    private lateinit var btnLihat: ImageButton
    private lateinit var btnEdit: ImageButton
    private lateinit var btnHapus: ImageButton

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: UserAdapter? = null
    private var user: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()

        initView()
        initRecycleView()
        sqLiteHelper = SQLiteHelper(this)

        btnTambah.setOnClickListener{tambahUser()}
        btnLihat.setOnClickListener{lihatUser()}

        adapter?.setOnclickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()

            //Update
            etNamaUser.setText(it.name)
            etEmailUser.setText(it.email)
            user = it
        }

        adapter?.setOnclickUpdateItem {
            updateUser()
        }

        adapter?.setOnclickDeleteItem {
            deleteUser(it.id)
        }
    }

    private fun lihatUser() {
        val userList = sqLiteHelper.getAllUser()
        Log.e("pppp", "${userList.size}")

        adapter?.tambahItem(userList)
    }

    private fun tambahUser() {
        val name = etNamaUser.text.toString()
        val email = etEmailUser.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Harap isi kolom yang kosong", Toast.LENGTH_SHORT).show()
        } else {
            val user = UserModel(name = name, email = email)
            val status = sqLiteHelper.insertUser(user)
            //mengecek sukses atau tidak dibagian penambahan
            if (status > -1) {
                Toast.makeText(this, "User telah ditambahkan!", Toast.LENGTH_SHORT).show()
                clearEditText()
                lihatUser()
            } else {
                Toast.makeText(this, "Penambahan gagal dilakukan :(", Toast.LENGTH_SHORT).show()
            }

        }
    }

    //mengupdate User
    private fun updateUser() {
        val name = etNamaUser.text.toString()
        val email = etEmailUser.text.toString()

        //check bahwa data berubah atau tidak
        if (name == user?.name && email == user?.email) {
            Toast.makeText(this, "Data tidak berubah", Toast.LENGTH_SHORT).show()
            return
        }

        if (user != null) return
        val user = UserModel(id = user!!.id, name = name, email = email)
        val status = sqLiteHelper.editUserById(user)
        if(status > -1) {
            clearEditText()
            lihatUser()
        } else {
            Toast.makeText(this, "Data tidak berhasil dirubah :(", Toast.LENGTH_SHORT).show()
        }
    }

    //mendelete User
    private fun deleteUser(id: Int) {
        //Peringatan yakin untuk dihapus
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Yakin ingin menghapus User ini ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Ya") {
            dialog, _ ->
            sqLiteHelper.hapusUserById(id)
            lihatUser()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {
            dialog, _ -> dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    //menghapus inputan di editText
    private fun clearEditText() {
        etNamaUser.setText("")
        etEmailUser.setText("")
        etNamaUser.requestFocus()
    }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        etNamaUser = findViewById(R.id.etNamaUser)
        etEmailUser = findViewById(R.id.etEmailUser)
        btnTambah = findViewById(R.id.btnTambah)
        btnLihat = findViewById(R.id.btnLihat)
        btnEdit = findViewById(R.id.btnEdit)
        btnHapus = findViewById(R.id.btnHapus)
        recyclerView = findViewById(R.id.rvDashboard)
    }

}