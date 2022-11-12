package com.staminapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.staminapp.R

val CeraPro = FontFamily(
        Font(R.font.cera_pro_100, FontWeight.W100, FontStyle.Normal),
        Font(R.font.cera_pro_300, FontWeight.W300, FontStyle.Normal),
        Font(R.font.cera_pro_500, FontWeight.W500, FontStyle.Normal),
        Font(R.font.cera_pro_600, FontWeight.W600, FontStyle.Normal),
        Font(R.font.cera_pro_700, FontWeight.W700, FontStyle.Normal),
        Font(R.font.cera_pro_900, FontWeight.W900, FontStyle.Normal),
        Font(R.font.cera_pro_100_it, FontWeight.W100, FontStyle.Italic),
        Font(R.font.cera_pro_300_it, FontWeight.W300, FontStyle.Italic),
        Font(R.font.cera_pro_500_it, FontWeight.W500, FontStyle.Italic),
        Font(R.font.cera_pro_600_it, FontWeight.W600, FontStyle.Italic),
        Font(R.font.cera_pro_700_it, FontWeight.W700, FontStyle.Italic),
        Font(R.font.cera_pro_900_it, FontWeight.W900, FontStyle.Italic),
    )

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W900,
        fontStyle = FontStyle.Italic,
        fontSize = 48.sp
    ),
    h2 = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W900,
        fontStyle = FontStyle.Italic,
        fontSize = 40.sp
    ),
    h3 = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W900,
        fontStyle = FontStyle.Italic,
        fontSize = 30.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W700,
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Italic,
        fontSize = 20.sp
    ),
//    button = TextStyle(
//        fontFamily = CeraPro,
//        fontWeight = FontWeight.W700,
//        fontStyle = FontStyle.Normal,
//        fontSize = 14.sp,
//    ),
    caption = TextStyle(
        fontFamily = CeraPro,
        fontWeight = FontWeight.W100,
        fontStyle = FontStyle.Normal,
        fontSize = 10.sp,
    ),
)