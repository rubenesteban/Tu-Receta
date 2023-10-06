package com.abeja.tu_receta.data

import android.net.Uri
import com.abeja.tu_receta.model.Response
import com.abeja.tu_receta.repository.AddImageToStorageResponse
import com.abeja.tu_receta.repository.AddImageUrlToFirestoreResponse
import com.abeja.tu_receta.repository.GetImageFromFirestoreResponse
import com.abeja.tu_receta.repository.ImageRepository
import com.abeja.tu_receta.ui.Constants.CREATED_AT
import com.abeja.tu_receta.ui.Constants.IMAGES
import com.abeja.tu_receta.ui.Constants.URL



import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
): ImageRepository {
    val datetime = LocalDateTime.now()
    override suspend fun addImageToFirebaseStorage(imageUri: Uri):AddImageToStorageResponse {
        return try{
            val downloadUrl = storage.reference.child(IMAGES).child("UID+${datetime}")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Response.Success(downloadUrl)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }



    override suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse {


        return try{
            db.collection(IMAGES).document("UID+${datetime}").set(mapOf(
                URL to download,
                CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            Response.Success(true)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun getImageUrlFromFirestore(): GetImageFromFirestoreResponse {
        return try{
            val imageUrl = db.collection(IMAGES).document("UID+${datetime}").get().await().getString(URL)
            Response.Success(imageUrl)

        }
        catch (e: Exception){
            Response.Failure(e)
        }
    }

}
