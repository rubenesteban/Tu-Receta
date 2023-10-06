package com.abeja.tu_receta.nav


import androidx.compose.runtime.Composable

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.abeja.tu_receta.screen.*

@Composable
fun MascotaNavigation(){
    val navController = rememberNavController()
    val viewModel: SharedViewModel = viewModel()


    NavHost(navController = navController,

        startDestination = MascotaScreen.SplashScreen.name
    ){
            composable(MascotaScreen.LoginScreen.name){
                LoginScreen(navController = navController)
            }

            composable(MascotaScreen.ImageScreen.name){
                ImageScreen(navController = navController, sharedViewModel = viewModel)
            }

             composable(MascotaScreen.AddDataScreen.name){
                AddDataScreen(navController = navController, sharedViewModel = viewModel())
            }

             composable(MascotaScreen.GetDataScreen.name){
                GetDataScreen(navController = navController, sharedViewModel = viewModel())
            }

            composable(MascotaScreen.SplashScreen.name){
            SplashScreen(navController = navController)
            }

            composable(MascotaScreen.TaskRow.name){

                TaskRow(navController = navController, sharedViewModel = viewModel())
            }


    }
}










