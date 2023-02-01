package com.fininfo.loginexample

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fininfo.loginexample.ui.UserDataViewModel
import com.fininfo.loginexample.ui.adapter.DataAdapter
import com.fininfo.loginexample.ui.model.UserData
import com.google.gson.Gson
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    lateinit var adapter: DataAdapter
    lateinit var realm: Realm
    lateinit var viewModel: UserDataViewModel
    var size: Int = 0
    lateinit var userData:ArrayList<UserData>
    lateinit var listUsers:List<UserData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()
        userData= ArrayList()
        listUsers = ArrayList()
        getDataFromViewModel()
        setupUI()
        val preferences1: SharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        val loginStatus: Boolean = preferences1.getBoolean("loginStatus", false)
        if (!loginStatus) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }

    fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DataAdapter(
            arrayListOf()
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    fun renderList(apiUser: ArrayList<UserData>) {
        adapter.addData(apiUser)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val preferences1: SharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
                val editor: Editor = preferences1.edit()
                editor.putBoolean("loginStatus", false)
                editor.apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                true
            }
            R.id.sort_by_name -> {
                userData.clear()
                viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
                viewModel.getUsersByName().observe(this) {
                    listUsers = ArrayList()
                    listUsers= it
                    userData.addAll(listUsers)
                    renderList(userData)
                }
                true
            }
            R.id.sort_by_age -> {
                userData.clear()
                viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
                viewModel.getUsersByAge().observe(this) {
                    listUsers = ArrayList()
                    listUsers= it
                    userData.addAll(listUsers)
                    renderList(userData)
                }
                true
            }
            R.id.sort_by_city -> {
                userData.clear()
                viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
                viewModel.getUsersByCity().observe(this) {
                    listUsers = ArrayList()
                    listUsers= it
                    userData.addAll(listUsers)
                    renderList(userData)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun dataInsert(list: List<UserData>) {
        try {
            realm.executeTransaction(object : Realm.Transaction {
                override fun execute(realm: Realm) {
                    realm.copyToRealm(list)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun dataConversion() {
        val json: String
        val inputStream: InputStream = resources.openRawResource(R.raw.rawdata)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer)
        val userData: List<UserData> = Gson().fromJson(json, Array<UserData>::class.java).asList()
        Log.w("userData", "User" + userData)
        dataInsert(userData)
    }

    fun getDataFromViewModel() {
        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        viewModel.getUsers().observe(this)
        {
            listUsers=it
            userData.addAll(listUsers)
            size=userData.size
            if (size == 0) {
                dataConversion()
            }else{
                renderList(userData)
            }
        }
    }
}