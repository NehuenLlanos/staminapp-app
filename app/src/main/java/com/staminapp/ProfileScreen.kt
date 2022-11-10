package com.staminapp

import android.media.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Profile screen")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            val painter = painterResource(id = R.drawable.guy1)
//            Image(
//                painter = painter,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//            )
            Text(text = "Alex", fontSize = 30.sp)
        }
        Text(text = "User description and information")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Update profile")
        }
    }
}