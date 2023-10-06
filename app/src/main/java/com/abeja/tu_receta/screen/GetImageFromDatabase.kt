package com.abeja.tu_receta.screen


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.abeja.tu_receta.componets.ProgressBar
import com.abeja.tu_receta.model.Response.Loading
import com.abeja.tu_receta.model.Response.Success
import com.abeja.tu_receta.model.Response.Failure


@Composable
fun GetImageFromDatabase(
    viewModel: ImageViewModel = hiltViewModel(),
    createImageContent: @Composable (imageUrl: String)-> Unit
){
    when (val getImageFromDatabaseResponse = viewModel.addImageFromDatabaseResponsse){
        is Loading -> ProgressBar()
        is Success -> getImageFromDatabaseResponse.data?.let { imageUrl->
            createImageContent(imageUrl)

        }
        is Failure -> print(getImageFromDatabaseResponse.e)
    }


}