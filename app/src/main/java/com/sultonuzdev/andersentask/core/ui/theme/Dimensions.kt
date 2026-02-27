package com.sultonuzdev.andersentask.core.ui.theme

import androidx.compose.ui.unit.dp

private const val GRID_UNIT_SIZE = 4

private object Multiplier {
    const val NONE = 0
    const val XS2 = 1
    const val XS = 2
    const val S = 3
    const val M = 4
    const val L = 5
    const val XL = 6
    const val XL2 = 7
    const val XL3 = 8
    const val XL4 = 9
    const val XL5 = 10
    const val XL6 = 11
    const val XL7 = 12
    const val XL8 = 13
    const val XL9 = 14
}

object Spaces {
    val none = (Multiplier.NONE * GRID_UNIT_SIZE).dp
    val xs2  = (Multiplier.XS2  * GRID_UNIT_SIZE).dp
    val xs   = (Multiplier.XS   * GRID_UNIT_SIZE).dp
    val s    = (Multiplier.S    * GRID_UNIT_SIZE).dp
    val m    = (Multiplier.M    * GRID_UNIT_SIZE).dp
    val l    = (Multiplier.L    * GRID_UNIT_SIZE).dp
    val xl   = (Multiplier.XL   * GRID_UNIT_SIZE).dp
    val xl2  = (Multiplier.XL2  * GRID_UNIT_SIZE).dp
    val xl3  = (Multiplier.XL3  * GRID_UNIT_SIZE).dp
    val xl4  = (Multiplier.XL4  * GRID_UNIT_SIZE).dp
    val xl5  = (Multiplier.XL5  * GRID_UNIT_SIZE).dp
    val xl6  = (Multiplier.XL6  * GRID_UNIT_SIZE).dp
    val xl7  = (Multiplier.XL7  * GRID_UNIT_SIZE).dp
    val xl8  = (Multiplier.XL8  * GRID_UNIT_SIZE).dp
    val xl9  = (Multiplier.XL9  * GRID_UNIT_SIZE).dp
}

object Radius {
    val none   = 0.dp
    val xs     = 4.dp
    val s      = 8.dp
    val m      = 12.dp
    val l      = 16.dp
    val xl     = 24.dp
}

object IconSize {
    val s  = 16.dp
    val m  = 24.dp
    val l  = 32.dp
    val xl = 48.dp
}

object ComponentSize {
    val activeDotSize = 8.dp
    val inActiveDotSize = 7.dp
    val thumbnailS  = 48.dp
    val thumbnailM  = 72.dp
    val thumbnailL  = 96.dp
    val buttonHeight = 48.dp
    val searchBarHeight = 56.dp
    val appBarHeight = 64.dp
}