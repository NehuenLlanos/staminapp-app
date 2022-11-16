package com.staminapp.ui.profile

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.staminapp.ui.execute.ExecuteViewModel
import com.staminapp.util.getExecuteViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.util.decodeBase64Image
import com.staminapp.util.getProfileViewModelFactory
import java.sql.Date
import java.time.LocalDateTime

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = getProfileViewModelFactory())
) {
    val iuState = viewModel.uiState
    if (!iuState.isFetching && iuState.currentUser == null) {
        viewModel.getCurrentUser()
    }
    if (iuState.currentUser != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                UserImage(iuState)
                UserNameTitle(iuState)
            }
            //        StatisticsButton(Modifier.padding(vertical = 16.dp))
            PersonalInfoText()
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                GenderTextField(Modifier.fillMaxWidth(), iuState)
                EMailTextField(Modifier.fillMaxWidth(), iuState)
                BirthdateTextField(Modifier.fillMaxWidth(), iuState)
            }
            SignOutButton(viewModel, navController = navController)
        }
    }
}



@Composable
fun UserImage(iuState: ProfileUiState){

    Image(
        modifier = Modifier
        .size(150.dp)
        .padding(10.dp)
        .clip(CircleShape)
        .fillMaxHeight()
        .fillMaxWidth(),
        bitmap = decodeBase64Image(iuState.currentUser!!.image).asImageBitmap(),
        contentDescription = "Imagen de Rutina",
        contentScale = ContentScale.Crop
    )

//    val context = LocalContext.current
//    val myImage: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),android.R.mipmap.sym_def_app_icon)
//    val result= remember {
//        mutableStateOf<Bitmap>(myImage)
//    }
//    val loadImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
//       //if(Build.VERSION.SDK_INT < 29){
//            result.value= MediaStore.Images.Media.getBitmap(context.contentResolver,it)
//        //}
//        //else{
//        //    val source = ImageDecoder.createSource(context.contentResolver,it)
//        //    result.value= ImageDecoder.decodeBitmap(source)
//        //}
//    }
//    Image(result.value.asImageBitmap(), contentDescription = "User Image", modifier = Modifier
//        .size(150.dp)
//        .padding(10.dp)
//        .clip(CircleShape)
//        .fillMaxHeight()
//        .fillMaxWidth()
//        .rotate(90f)
//        .clickable { loadImage.launch("image/*") },
//        contentScale = ContentScale.Crop)
        // con FillBounds se soluciona pero queda muy feo

//    OutlinedButton(onClick = {loadImage.launch("image/*")}, modifier = Modifier.width(150.dp).padding(10.dp)) {
//        Text(text="Cargar Imagen")
//    }
}

@Composable
fun UserNameTitle(iuState: ProfileUiState){
    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = iuState.currentUser!!.firstName,
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(text = iuState.currentUser.birthdate!!.year.toString() + " " +"Años",
//        LocalDateTime.now().minusYears(
//            LocalDateTime.of(
//                , iuState.currentUser.birthdate!!.month,
//                iuState.currentUser.birthdate!!.day, iuState.currentUser.birthdate!!.hours,
//                iuState.currentUser.birthdate!!.minutes).year.toLong()
//        ).toString(),
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
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
}


@Composable
fun GenderTextField(modifier: Modifier, iuState: ProfileUiState){
    Box(modifier = modifier
        .wrapContentSize(Alignment.TopStart)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value =
                if (iuState.currentUser!!.gender == "male") {
                    "Masculino"
                } else if (iuState.currentUser!!.gender == "female") {
                    "Femenino"
                } else {
                    "Otro"
                }
            ,
            enabled = false,
            readOnly = true,
            onValueChange = { },
            label = { Text(text = "Género", fontWeight = FontWeight.Bold) },
            textStyle = TextStyle(
                MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold
            ),
//            trailingIcon = {
//                Icon(
//                    Icons.Filled.ExpandMore ,
//                    contentDescription = null,
//                    Modifier.clickable {
//                        expanded = true
//                    }
//                )
//            }
        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            DropdownMenuItem(onClick = {
//                genre = TextFieldValue("Masculino")
//                }) {
//                Text("Masculino")
//            }
//            DropdownMenuItem(onClick = {
//                genre = TextFieldValue("Femenino")
//                }) {
//                Text("Femenino")
//            }
//            DropdownMenuItem(onClick = {
//                genre = TextFieldValue("Otro")
//                }) {
//                Text("Otro")
//            }
//        }
    }
}

@Composable
fun EMailTextField(modifier: Modifier, iuState: ProfileUiState){
    TextField(
        modifier = modifier,
        value = iuState.currentUser!!.username,
        enabled = false,
        readOnly = true,
        onValueChange = {
            iuState.currentUser.username = it
        },
        label = { Text(text = "Email", fontWeight = FontWeight.Bold) },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun BirthdateTextField(modifier: Modifier, iuState: ProfileUiState) {
    TextField(
        modifier = modifier,
        value = iuState.currentUser!!.birthdate!!.date.toString() + "/" +
                iuState.currentUser!!.birthdate!!.month.toString() + "/" +
                iuState.currentUser!!.birthdate!!.year.toString(),
        enabled = false,
        readOnly = true,
        onValueChange = {

        },
        label = { Text(text = "Fecha de nacimiento", fontWeight = FontWeight.Bold) },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun SignOutButton(viewModel: ProfileViewModel, navController: NavController) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { viewModel.logout(navController) },
        shape = RoundedCornerShape(50), // = 50% percent
        colors = ButtonDefaults.buttonColors(Red),
    ) {
        Text(text="Cerrar sesión")
    }
}
