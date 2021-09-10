package com.example.enhanzo.dependencyinjection

import com.example.enhanzo.viewmodels.EditImageViewModel
import com.example.enhanzo.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
    viewModel { SavedImagesViewModel(savedImagesRepository = get()) }
}