package com.example.enhanzo.dependencyinjection

import com.example.enhanzo.repositories.EditImageRepository
import com.example.enhanzo.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
}