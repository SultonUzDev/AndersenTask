package com.sultonuzdev.andersentask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sultonuzdev.andersentask.R
import com.sultonuzdev.andersentask.core.ui.theme.Radius
import com.sultonuzdev.andersentask.core.ui.theme.Spaces


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {

    Row(
        modifier = modifier
            .background(
                color = Color.LightGray.copy(0.4f),
                shape = RoundedCornerShape(Radius.s)
            )
            .padding(horizontal = Spaces.m),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spaces.xs),
    ) {
        Icon(
            imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search), tint = Color.Gray
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = Spaces.xs)
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black
                )
            )
            if (searchQuery.isEmpty()) {
                Text(
                    text = "Search", color = Color.Gray, style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }


}
