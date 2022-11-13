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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            UserImage()
            UserNameTitle()
        }
        StatisticsButton(Modifier.padding(vertical = 16.dp))
        PersonalInfoText()
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            GenderTextField(Modifier.fillMaxWidth())
            EMailTextField(Modifier.fillMaxWidth())
            BirthdateTextField(Modifier.fillMaxWidth())
        }
        SignOutButton()
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
        .rotate(90f)
        .clickable { loadImage.launch("image/*") },
        contentScale = ContentScale.Crop)
        // con FillBounds se soluciona pero queda muy feo

//    OutlinedButton(onClick = {loadImage.launch("image/*")}, modifier = Modifier.width(150.dp).padding(10.dp)) {
//        Text(text="Cargar Imagen")
//    }
}

@Composable
fun UserNameTitle(){
    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "dwayne johnson".uppercase(),
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(text = "50 años".uppercase(),
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }

//    Text(text = buildAnnotatedString { withStyle(style= SpanStyle(color = MaterialTheme.colors.primaryVariant,
//                                    fontSize = 24.sp, fontWeight = FontWeight.Bold)){append("DWAYNE JOHNSON")}
//
//        withStyle(style= SpanStyle(color = MaterialTheme.colors.primaryVariant, fontSize = 16.sp)){append("50 años")}
//    })
}

@Composable
fun StatisticsButton(modifier: Modifier){
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = {},
        shape = RoundedCornerShape(50), // = 50% percent
        // or shape = CircleShape
    ) {
        Text(text="Ver estadísticas")
    }
}

@Composable
fun PersonalInfoText(){
    Text(text = "Información Personal",
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
//    Text(buildAnnotatedString { withStyle(style= SpanStyle(color = MaterialTheme.colors.primaryVariant, fontWeight = FontWeight.Bold, fontSize = 30.sp)){append("Información Personal")}})
}


@Composable
fun GenderTextField(modifier: Modifier){
    var expanded by remember { mutableStateOf(false) }
    var genre by remember { mutableStateOf(TextFieldValue("Género")) }
    Box(modifier = modifier
        .wrapContentSize(Alignment.TopStart)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = genre,
            readOnly = true,
            onValueChange = {
                genre = it
            },
            label = { Text(text = "Género", fontWeight = FontWeight.Bold) },
            textStyle = TextStyle(
                MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold
            ),
            trailingIcon = {
                Icon(
                    Icons.Filled.ExpandMore ,
                    contentDescription = null,
                    Modifier.clickable {
                        expanded = true
                    }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                genre = TextFieldValue("Masculino")
                }) {
                Text("Masculino")
            }
            DropdownMenuItem(onClick = {
                genre = TextFieldValue("Femenino")
                }) {
                Text("Femenino")
            }
            DropdownMenuItem(onClick = {
                genre = TextFieldValue("Otro")
                }) {
                Text("Otro")
            }
        }
    }
}

@Composable
fun EMailTextField(modifier: Modifier){
    var email by remember { mutableStateOf(TextFieldValue("therock@gmail.com")) }
    TextField(
        modifier = modifier,
        value = email,
        readOnly = true,
        onValueChange = {
            email = it
        },
        label = { Text(text = "Email", fontWeight = FontWeight.Bold) },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun BirthdateTextField(modifier: Modifier){
    var birthdate by remember { mutableStateOf(TextFieldValue("02/02/2022")) }
    TextField(
        modifier = modifier,
        value = birthdate,
        readOnly = true,
        onValueChange = {
            birthdate = it
        },
        label = { Text(text = "Fecha de nacimiento", fontWeight = FontWeight.Bold) },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun SignOutButton(){
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
        shape = RoundedCornerShape(50), // = 50% percent
        colors = ButtonDefaults.buttonColors(Red),
        // or shape = CircleShape
    ) {
        Text(text="Cerrar sesión")
    }
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