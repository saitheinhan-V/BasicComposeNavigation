package com.my.basiccomposenavigation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.basiccomposenavigation.Destination

@Composable
fun TabRows(
    tabList: List<Destination>,
    onTabSelected: (Destination) -> Unit,
    currentTab: Destination
) {

    Surface(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.selectableGroup()
        ) {
            tabList.forEach() { item ->
                TabItem(title = item.route,
                    icon = item.icon,
                    selected = currentTab == item,
                    onSelected = { onTabSelected(item) })
            }
        }
    }
}