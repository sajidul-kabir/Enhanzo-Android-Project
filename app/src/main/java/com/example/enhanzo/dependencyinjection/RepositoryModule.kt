package com.example.enhanzo.dependencyinjection

import com.example.enhanzo.repositories.EditImageRepository
import com.example.enhanzo.repositories.EditImageRepositoryImpl
import com.example.enhanzo.repositories.SavedImagesRepository
import com.example.enhanzo.repositories.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}