package com.abeja.tu_receta.screen

import android.net.Uri

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.abeja.tu_receta.componets.ProgressBar
import com.abeja.tu_receta.model.Response.Success
import com.abeja.tu_receta.model.Response.Loading
import com.abeja.tu_receta.model.Response.Failure




@Composable
fun AddImageToStorage(
    viewModel: ImageViewModel = hiltViewModel(),
    addImageToDatabase : (downloadUrl: Uri)->Unit
){
    when (val addImageToStorageResponse = viewModel.addImageToStorageResponse){
        is Loading -> ProgressBar()
        is Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl){
                addImageToDatabase(downloadUrl)
            }

        }
        is Failure -> print(addImageToStorageResponse.e)
    }
}