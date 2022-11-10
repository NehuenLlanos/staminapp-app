package com.staminapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//@Composable
//fun ListScreen(navController: NavHostController) {
//    val elements = MutableList(100) { it }
//    LazyColumn(modifier = Modifier.background(Color.LightGray)) {
//        items(elements) {
//            Row(modifier = Modifier
//                .padding(4.dp)
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(4.dp))
//                .background(Color.White)
//                .padding(4.dp)
//                .clickable { navController.navigate(Destination.Routine.createRoute(it)) }
//            ) {
//                Text(text = "Element $it")
//            }
//        }
//    }
//}

