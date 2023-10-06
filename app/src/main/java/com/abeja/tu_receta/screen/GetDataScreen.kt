package com.abeja.tu_receta.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var image: String by remember { mutableStateOf("") }


    Scaffold(
    )
    { paddingValues ->
        ImageContent(imageUrl = image, modifier = Modifier.padding(paddingValues))
        Column(modifier = Modifier.fillMaxSize()) {
            // back button
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

            }


        }


    }


    // main Layout
}




@Composable
fun ImageContent(
    imageUrl: String,
    modifier: Modifier = Modifier
){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
            ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 64.dp)
                .clip(CircleShape)
                .width(128.dp)
                .height(128.dp)
        )

        /// CourseDescriptionBody( name, profession, age, qua)


    }
}



