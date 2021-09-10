package com.example.imagefilters.dependencyinjection

import com.example.imagefilters.viewmodels.EditImageViewModel
import com.example.imagefilters.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
    viewModel { SavedImagesViewModel(savedImagesRepository = get()) }
}