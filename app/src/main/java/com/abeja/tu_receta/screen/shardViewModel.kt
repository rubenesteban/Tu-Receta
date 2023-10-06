package com.abeja.tu_receta.screen

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch




class SharedViewModel() : ViewModel() {


    val _uiState = MutableStateFlow(UserData())
    val uiState: StateFlow<UserData> = _uiState.asStateFlow()

    fun saveData(
        userData: UserData,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {



        Log.d("MascotaFeliz", "saveData")
        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userData.userID)
        Log.d("MascotaFeliz", "saveData 1")

        try {
            fireStoreRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun retrieveData(
        userID: String,
        context: Context,
        data: (UserData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    // for getting single or particular document
                    if (it.exists()) {
                        val userData = it.toObject<UserData>()!!
                        data(userData)
                    } else {
                        Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun retrieveImage(
        userID: String,
        context: Context,
        data: (UserImage) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("images")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    // for getting single or particular document
                    if (it.exists()) {
                        val userImage = it.toObject<UserImage>()!!
                        data(userImage)
                    } else {
                        Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(
        userID: String,
        context: Context,
        navController: NavController,
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully deleted data", Toast.LENGTH_SHORT)
                        .show()
                    navController.popBackStack()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun datosbd(): Flow<List<UserData>> = callbackFlow{
        val db = Firebase.firestore
            .collection("user")


        db.get().addOnSuccessListener { result ->
            val notes = mutableListOf<UserData>()
            for (document in result) {
                Log.d("MascotaFeliz", "${document.id} => ${document.data}")
                Log.d(TAG, "${document.id} => ${document.data}")
                val note = document.toObject(UserData::class.java)
                note?.userID = document.id
                note?.let {notes.add(it)}
            }

        }
            .addOnFailureListener { exception ->
                Log.d("MascotaFeliz", "Error getting documents: ", exception)
                Log.d(TAG, "Error getting documents: ", exception)
            }



    }



    fun getAllUserDocuments(
        context: Context,
        data: (UserData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")

        try {
            fireStoreRef.get()
                .addOnSuccessListener { result ->
                    for(document in result){
                        // for getting single or particular document
                        if (document.exists()) {
                            val userData = document.toObject<UserData>()!!
                            data(userData)
                            //reduceQuantityChenking( userData.userID, userData.name, userData.profession, userData.qua.toInt(), userData.age)
                            Log.d("MascotaFeliz", "Loqueando con ")

                        } else {
                            Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }






}