package com.abeja.tu_receta.screen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abeja.tu_receta.model.Response
import com.abeja.tu_receta.Repository.ImageRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repo: ImageRepository
): ViewModel() {
    var addImageToStorageResponse by mutableStateOf<Response<Uri>>(Response.Success(null))
        private set

    var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Response.Success(null))
        private set

    var addImageFromDatabaseResponsse by mutableStateOf<Response<String>>(Response.Success(null))
        private set

    fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
        addImageToStorageResponse = Response.Loading
        addImageToStorageResponse = repo.addImageToFirebaseStorage(imageUri)

    }


    fun addImageToDatabase(downloadUrl: Uri) = viewModelScope.launch {
        addImageToDatabaseResponse = Response.Loading
        addImageToDatabaseResponse = repo.addImageUrlToFirestore(downloadUrl)

    }


    fun getImageFromDatabase() = viewModelScope.launch {
        addImageFromDatabaseResponsse = Response.Loading
        addImageFromDatabaseResponsse = repo.getImageUrlFromFirestore ()
        Log.d("MascotaFeliz", "Creado url" )   }


}