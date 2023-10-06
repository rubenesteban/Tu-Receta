package com.abeja.tu_receta.screen

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*


import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.abeja.tu_receta.util.Constants.ALL_IMAGES
import kotlinx.coroutines.launch
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.abeja.tu_receta.screen.AbrirGaleria
import com.abeja.tu_receta.R



@Composable
fun ImageScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: ImageViewModel = hiltViewModel(),
    loginviewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }
    var key: String by remember { mutableStateOf("") }
    var image: String by remember { mutableStateOf("") }


    var qua: String by remember { mutableStateOf("") }



    val context = LocalContext.current
    fun please(h:String): String {
        h.replace("\n","\n-" )
        return h

    }

    suspend fun loadWeather() {
        userID = please(age)
        key = loginviewModel.autin()

    }

    LaunchedEffect(Unit) {

        loadWeather()
    }



    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){imageUri->
        imageUri?.let{
            viewModel.addImageToStorage(imageUri)
        }

    }




    Scaffold(
        content = { padding->

            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
            ) {

                GetImageFromDatabase(
                    createImageContent = { imageUrl->
                        ImageContent(imageUrl)
                        image = imageUrl
                    }
                )
                fruitItem (
                    sharedViewModel  = SharedViewModel(),
                    viewModel = hiltViewModel(),
                    image = image

                )
                AbrirGaleria(
                    openGallery = {
                        galleryLauncher.launch(ALL_IMAGES)
                    }
                )
            }


        },

        scaffoldState = scaffoldState
    )

    AddImageToStorage(
        addImageToDatabase = {downloadUrl->
            viewModel.addImageToDatabase(downloadUrl)
        }
    )


    fun showSnackBar() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = "Imagen correctamente agregada",
            actionLabel = "Mostrar"
        )
        if (result == SnackbarResult.ActionPerformed){
            viewModel.getImageFromDatabase()

        }
    }


    AddImageToDatabase(
        showSnackBar = { isImageAddedToDatabase->
            if (isImageAddedToDatabase){
                showSnackBar()
            }

        }
    )




}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun fruitItem (
    sharedViewModel: SharedViewModel,
    viewModel: LoginViewModel = hiltViewModel(),
    image:String

) {


    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }
    var key: String by remember { mutableStateOf("") }


    var qua: String by remember { mutableStateOf("") }
    var imge: Boolean by remember { mutableStateOf(false ) }




    suspend fun loadWeather() {

        key = viewModel.autin()

    }

    LaunchedEffect(Unit) {

        loadWeather()
    }
    val context = LocalContext.current
    val newOrder = stringResource(R.string.cotopaxi)
    val orderSummary = stringResource(R.string.cotopaxi4)



    // main Layout
    Column(modifier = Modifier.fillMaxSize()) {
        // back button

        // add data Layout
        Column(
            modifier = Modifier
                .padding(start = 60.dp, end = 60.dp, bottom = 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // userID

            // Name
            androidx.compose.material3.OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    androidx.compose.material3.Text(text = "Title")
                }
            )
            // Profession
            androidx.compose.material3.OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = profession,
                onValueChange = {
                    profession = it
                },
                label = {
                    androidx.compose.material3.Text(text = "Preparedness")
                }
            )
            // Age
            androidx.compose.material3.OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = age,
                onValueChange = {
                    age = it

                },

                label = {
                    androidx.compose.material3.Text(text = "Ingredient")
                }

            )

            androidx.compose.material3.OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = qua,
                onValueChange = {
                    qua = it

                },
                label = {
                    androidx.compose.material3.Text(text = "Quantity")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            androidx.compose.material3.OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userID,
                onValueChange = {
                    userID = it
                },
                label = {
                    androidx.compose.material3.Text(text = "Price")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // save Button
            if(image != null){
                androidx.compose.material3.Button(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    onClick = {
                        val userData = UserData(
                            userID = key,
                            name = name,
                            profession = profession,
                            age = age,
                            url = image,
                            qua = qua,
                            gol = userID
                        )

                        sharedViewModel.saveData(userData = userData, context = context)

                    }
                ) {
                    androidx.compose.material3.Text(text = "Save")
                }
            }

            androidx.compose.material3.Button(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                onClick = {
                    shareOrder(context, subject = newOrder, summary = orderSummary)
                }
            ) {
                androidx.compose.material3.Text(text = "Leave us your information")
            }



        }
    }
}


private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}