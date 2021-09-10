package com.example.imagefilters.dependencyinjection

import com.example.imagefilters.repositories.EditImageRepository
import com.example.imagefilters.repositories.EditImageRepositoryImpl
import com.example.imagefilters.repositories.SavedImagesRepository
import com.example.imagefilters.repositories.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}