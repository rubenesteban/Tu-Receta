package com.abeja.tu_receta.di

import com.abeja.tu_receta.data.ImageRepositoryImpl
import com.abeja.tu_receta.Repository.ImageRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore


import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule{
    @Provides
    fun provideFirebaseStore() = Firebase.storage

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideImageRepository(
        storage: FirebaseStorage,
        db: FirebaseFirestore
    ): ImageRepository = ImageRepositoryImpl(
        storage = storage,
        db = db
    )
}

