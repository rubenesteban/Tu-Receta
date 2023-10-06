package com.abeja.tu_receta.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
fun TaskRow (navController: NavController,
             sharedViewModel: SharedViewModel,
             viewModel: LoginViewModel = hiltViewModel()
             ) {


    var tuID: String by remember { mutableStateOf("") }
    var lati: String by remember { mutableStateOf("") }
    var logt: String by remember { mutableStateOf("") }
    var phone: String by remember { mutableStateOf("") }
    var phoneInt: Long by remember { mutableStateOf(0) }
    var cuant: String by remember { mutableStateOf("") }




   // val item by sharedViewModel.datosbd().collectAsState(emptyList())


    val context = LocalContext.current

    val scope = rememberCoroutineScope()



    Scaffold(

    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),

            modifier = Modifier.padding(padding)
        ) {

            // Tasks
            item { TaskOata(sharedViewModel = SharedViewModel(), viewModel = LoginViewModel())}
            //item { NoteItem(note = item) }


        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: List<UserData>){

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp)
    ){
        note.forEach {
            item {
                Notelist(note = it)
            }
        }
    }

}


@Composable
fun Notelist(note: UserData){

    Card(
        modifier = Modifier.padding(6.dp),
    ){
        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
        ) {

        }
        Text(text = note.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text =  note.profession,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight =  15.sp
        )

    }

}


@Composable
fun TaskOata(sharedViewModel: SharedViewModel, viewModel: LoginViewModel  ) {

    val scope = rememberCoroutineScope()
    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }
    var key: String by remember { mutableStateOf("") }


    var qua: String by remember { mutableStateOf("") }



    val context = LocalContext.current
    suspend fun loadWeather() {

        key = viewModel.autin()

    }

    LaunchedEffect(Unit) {

        loadWeather()
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            androidx.compose.material.Text(
                text = "tortilla",
                style = MaterialTheme.typography.body1
            )

        }
        androidx.compose.material3.Button(
            modifier = Modifier
                .padding(start = 10.dp)
                .width(100.dp),
            onClick = {
                sharedViewModel.retrieveImage(
                    userID = key,
                    context = context
                ) { data ->
                    name = data.url


                }
            }
        ) {
            androidx.compose.material3.Text(text = "Get Data")
        }

        NetworkImage(
            url = name,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 3f)
        )

    }
}


@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}