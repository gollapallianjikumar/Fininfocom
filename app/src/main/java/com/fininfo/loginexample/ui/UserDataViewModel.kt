package com.fininfo.loginexample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fininfo.loginexample.ui.model.UserData
import io.realm.Realm
import io.realm.Sort

class UserDataViewModel :ViewModel() {

    private val users=MutableLiveData<List<UserData>>()
    private val usersByName=MutableLiveData<List<UserData>>()
    private val usersByAge=MutableLiveData<List<UserData>>()
    private val usersByCity=MutableLiveData<List<UserData>>()
    private var realm: Realm = Realm.getDefaultInstance()

    init {
        getAllNotes()
    }

    private fun getAllNotes(): MutableLiveData<List<UserData>> {
        val list = MutableLiveData<List<UserData>>()
        val userData = realm.where(UserData::class.java).findAll()
        list.value = userData?.subList(0, userData.size)
        users.postValue(list.value)
        return list
    }

    private fun getAllNotesByName(): MutableLiveData<List<UserData>> {
        val list = MutableLiveData<List<UserData>>()
        val userData = realm.where(UserData::class.java).findAll().sort("name",Sort.ASCENDING)
        list.value = userData?.subList(0, userData.size)
        usersByName.postValue(list.value)
        return list
    }

    private fun getAllNotesByAge(): MutableLiveData<List<UserData>> {
        val list = MutableLiveData<List<UserData>>()
        val userData = realm.where(UserData::class.java).findAll().sort("age",Sort.ASCENDING)
        list.value = userData?.subList(0, userData.size)
        usersByAge.postValue(list.value)
        return list
    }

    private fun getAllNotesByCity(): MutableLiveData<List<UserData>> {
        val list = MutableLiveData<List<UserData>>()
        val userData = realm.where(UserData::class.java).findAll().sort("city",Sort.ASCENDING)
        list.value = userData?.subList(0, userData.size)
        usersByCity.postValue(list.value)
        return list
    }



    fun getUsers(): LiveData<List<UserData>> {
        return users
    }

    fun getUsersByName():LiveData<List<UserData>>
    {
        getAllNotesByName()
        users.postValue(null)
        return usersByName
    }

    fun getUsersByAge():LiveData<List<UserData>>
    {
        getAllNotesByAge()
        users.postValue(null)
        return usersByAge
    }

    fun getUsersByCity():LiveData<List<UserData>>
    {
        getAllNotesByCity()
        users.postValue(null)
        return usersByCity
    }
}