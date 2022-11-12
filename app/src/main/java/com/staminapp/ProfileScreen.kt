package com.staminapp

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background),
    ) {
        Row(modifier = Modifier
            .wrapContentWidth(Alignment.Start)
            .fillMaxWidth(1f)) {
            UserImage()
            UserNameTitle(Modifier.padding(10.dp))
        }
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(1f)
            .padding(10.dp)) {
            StatisticsButton(Modifier.width(150.dp)
                .padding(10.dp)
                .weight(1f))
        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)) {
            PersonalInfoText()

        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)) {
            GenderTextField(Modifier.weight(1f))
        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)) {
            EMailTextField(Modifier.weight(1f))
        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)) {
            BirthdateTextField(Modifier.weight(1f))
        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)
            .align(Alignment.CenterHorizontally)) {
            SignOutButton(
                Modifier
                    .weight(1f)
                    .width(200.dp))
        }
    }
}

@Composable
fun UserImage(){
    val context = LocalContext.current
    val myImage: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),android.R.mipmap.sym_def_app_icon)
    val result= remember {
        mutableStateOf<Bitmap>(myImage)
    }
    val loadImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
       //if(Build.VERSION.SDK_INT < 29){
            result.value= MediaStore.Images.Media.getBitmap(context.contentResolver,it)
        //}
        //else{
        //    val source = ImageDecoder.createSource(context.contentResolver,it)
        //    result.value= ImageDecoder.decodeBitmap(source)
        //}
    }
    Image(result.value.asImageBitmap(), contentDescription = "User Image", modifier = Modifier
        .size(150.dp)
        .padding(10.dp)
        .clip(CircleShape)
        .fillMaxHeight()
        .fillMaxWidth()
        .clickable { loadImage.launch("image/*") }, contentScale = ContentScale.FillWidth)                       // con FillBounds se soluciona pero queda muy feo

//    OutlinedButton(onClick = {loadImage.launch("image/*")}, modifier = Modifier.width(150.dp).padding(10.dp)) {
//        Text(text="Cargar Imagen")
//    }
}

@Composable
fun UserNameTitle(modifier: Modifier){
    Text(modifier = modifier, text = buildAnnotatedString { withStyle(style= SpanStyle(color = MaterialTheme.colors.primaryVariant, fontSize = 40.sp, fontWeight = FontWeight.Bold)){append("DWAYNE JOHNSON")}
        withStyle(style= SpanStyle(color = MaterialTheme.colors.primaryVariant, fontSize = 30.sp)){append("50 años")}
    })
}

//@Composable
//fun HeightTextField(modifier: Modifier){
//    var height by remember { mutableStateOf(TextFieldValue("")) }
//    TextField(
//        modifier = modifier,
//        value = height,
//        onValueChange = {
//            height = it
//        },
//        label = { Text(text = "Altura") },
//        textStyle = TextStyle(
//            MaterialTheme.colors.primaryVariant,
//            fontWeight = FontWeight.Bold
//        ),
//    )
//}
//
//@Composable
//fun WeightTextField(modifier: Modifier){
//    var weight by remember { mutableStateOf(TextFieldValue("")) }
//    TextField(
//        modifier = modifier,
//        value = weight,
//        onValueChange = {
//            weight = it
//        },
//        label = { Text(text = "Peso") },
//        textStyle = TextStyle(
//            MaterialTheme.colors.primaryVariant,
//            fontWeight = FontWeight.Bold
//        ),
//    )
//}

@Composable
fun GenderTextField(modifier: Modifier){
    var gender by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        value = gender,
        onValueChange = {
            gender = it
        },
        label = { Text(text = "Género") },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun EMailTextField(modifier: Modifier){
    var email by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        value = email,
        readOnly = true,
        onValueChange = {
            email = it
        },
        label = { Text(text = "Email") },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun BirthdateTextField(modifier: Modifier){
    var birthdate by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        value = birthdate,
        readOnly = true,
        onValueChange = {
            birthdate = it
        },
        label = { Text(text = "Fecha de nacimiento") },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun StatisticsButton(modifier: Modifier){
    OutlinedButton(onClick = {}, modifier = modifier,
        shape = RoundedCornerShape(50), // = 50% percent
        // or shape = CircleShape
        ) {
        Text(text="Ver estadísticas")
    }
}

@Composable
fun SignOutButton(modifier: Modifier){
    OutlinedButton(onClick = {}, modifier = modifier,
        shape = RoundedCornerShape(50), // = 50% percent
        colors = ButtonDefaults.buttonColors(Red),
        // or shape = CircleShape
    ) {
        Text(text="Cerrar sesión")
    }
}


@Composable
fun PersonalInfoText(){
    Text(buildAnnotatedString { withStyle(style= SpanStyle(color = MaterialTheme.colors.primaryVariant, fontWeight = FontWeight.Bold, fontSize = 30.sp)){append("Información Personal")}})
}