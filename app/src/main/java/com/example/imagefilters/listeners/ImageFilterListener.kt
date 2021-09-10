package com.example.imagefilters.listeners

import com.example.imagefilters.data.ImageFilter

interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}