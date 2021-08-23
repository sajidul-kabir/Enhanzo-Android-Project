package com.example.imagefilters.dependencyinjection

import com.example.imagefilters.repositories.EditImageRepository
import com.example.imagefilters.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
}