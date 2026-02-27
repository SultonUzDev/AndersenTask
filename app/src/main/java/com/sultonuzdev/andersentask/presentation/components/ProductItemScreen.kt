package com.sultonuzdev.andersentask.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.sultonuzdev.andersentask.core.ui.theme.ComponentSize
import com.sultonuzdev.andersentask.core.ui.theme.GreenishSurface
import com.sultonuzdev.andersentask.core.ui.theme.Radius
import com.sultonuzdev.andersentask.core.ui.theme.Spaces
import com.sultonuzdev.andersentask.domain.model.Product


@Composable
fun ProductItemScreen(
    modifier: Modifier = Modifier,
    product: Product
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(Radius.m),
        colors = CardDefaults.elevatedCardColors(
            containerColor = GreenishSurface,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(Spaces.xs),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spaces.xs)
        ) {
            CustomImageAsync(
                imageSource = product.image,
                modifier = Modifier
                    .height(ComponentSize.appBarHeight)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(Radius.m)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spaces.xs2)
            ) {

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold, color = Color.Black
                    )
                )
                Text(
                    text = product.subtitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }

    }

}