package com.sultonuzdev.andersentask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.sultonuzdev.andersentask.core.ui.theme.ComponentSize
import com.sultonuzdev.andersentask.core.ui.theme.FloatButtonBlueColor
import com.sultonuzdev.andersentask.core.ui.theme.Radius
import com.sultonuzdev.andersentask.core.ui.theme.Spaces

@Composable
fun ImagePager(
    modifier: Modifier = Modifier,
    imagesSize: Int,
    imageSource: String?,
    currentPage: Int,
    onPageChanged: (Int) -> Unit
) {
    val pageState = rememberPagerState(
        initialPage = currentPage,
        pageCount = { imagesSize }
    )

    LaunchedEffect(pageState.settledPage) {
        if (pageState.settledPage != currentPage) {
            onPageChanged(pageState.settledPage)
        }
    }


    Column(modifier = modifier.fillMaxWidth()) {
            HorizontalPager(state = pageState) {
            CustomImageAsync(
                imageSource = imageSource,
                modifier = Modifier
                    .padding(Spaces.xs)
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .clip(
                        RoundedCornerShape(Radius.m)
                    ),
                contentScale = ContentScale.FillBounds
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spaces.xs),
        horizontalArrangement = Arrangement.spacedBy(Spaces.xs2, alignment = Alignment.CenterHorizontally)
    ) {
        repeat(imagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(if (page == pageState.currentPage) ComponentSize.activeDotSize else ComponentSize.inActiveDotSize)
                    .clip(RoundedCornerShape(Radius.m))
                    .background(if (page == pageState.currentPage) FloatButtonBlueColor else Color.LightGray)
                    .padding(Spaces.xs2),
            )
        }

    }


}