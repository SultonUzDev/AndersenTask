package com.sultonuzdev.andersentask.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import com.sultonuzdev.andersentask.R
import com.sultonuzdev.andersentask.core.ui.theme.IconSize
import com.sultonuzdev.andersentask.core.ui.theme.Spaces
import com.sultonuzdev.andersentask.core.utils.ImageLoader.loadImageFromInternet

@Composable
fun CustomImageAsync(
    modifier: Modifier = Modifier,
    imageSource: Any?,
    placeholder: Int = R.drawable.ic_place_holder,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val resource = LocalResources.current


    LaunchedEffect(imageSource) {
        if (imageSource is String) {
            bitmap = loadImageFromInternet(imageSource)
        }
        if (imageSource is Int) {
            bitmap = BitmapFactory.decodeResource(resource, imageSource)
        }
    }

    if (bitmap == null) {
        Box(
            modifier = modifier
                .background(Color.LightGray)
                .padding(Spaces.xs),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = placeholder),
                contentDescription = contentDescription,
                modifier = Modifier.requiredSizeIn(minWidth = IconSize.m, minHeight =  IconSize.m),
                alpha = alpha,
                colorFilter = colorFilter)
        }
    } else {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            colorFilter = colorFilter
        )

    }


}