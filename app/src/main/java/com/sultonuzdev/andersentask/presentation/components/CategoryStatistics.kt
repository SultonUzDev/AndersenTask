package com.sultonuzdev.andersentask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sultonuzdev.andersentask.R
import com.sultonuzdev.andersentask.core.ui.theme.AndersenTaskTheme
import com.sultonuzdev.andersentask.core.ui.theme.FloatButtonBlueColor
import com.sultonuzdev.andersentask.core.ui.theme.Spaces
import com.sultonuzdev.andersentask.presentation.Statistics

@Composable
fun CategoryStatistics(
    modifier: Modifier = Modifier,
    statistics: Statistics
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spaces.xs, horizontal = Spaces.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spaces.xs)
    ) {

        Text(
            text = statistics.categoryName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spaces.xs2)
        ) {
            Text(
                text = "${statistics.totalProduct}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = FloatButtonBlueColor
            )
            Text(
                text = stringResource(R.string.products),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(0.7f)
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE0E0E0))
        )

        Text(
            text = stringResource(R.string.top_characters),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        val topCharacter = statistics.topCharacter
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            topCharacter.forEach { (char, count) ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "$char : $count",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(0.7f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Spaces.xs))
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryStatisticsPreview() {
    AndersenTaskTheme {
        CategoryStatistics(
            statistics = Statistics(
                categoryName = "Electronics",
                totalProduct = 10,
                topCharacter = mapOf(
                    'a' to 5,
                    'b' to 3,
                    'c' to 2
                )
            )
        )

    }
}
