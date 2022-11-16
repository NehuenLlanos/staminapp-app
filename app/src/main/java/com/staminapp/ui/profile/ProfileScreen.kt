package com.staminapp.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.util.decodeBase64Image
import com.staminapp.util.getProfileViewModelFactory

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = getProfileViewModelFactory())
) {
    val uiState = viewModel.uiState
    if (!uiState.isFetching && uiState.currentUser == null) {
        viewModel.getCurrentUser()
    }
    if (uiState.currentUser != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                UserImage(uiState)
                UserNameTitle(uiState)
            }
            PersonalInfoText()
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                GenderTextField(Modifier.fillMaxWidth(), uiState)
                EMailTextField(Modifier.fillMaxWidth(), uiState)
                BirthdateTextField(Modifier.fillMaxWidth(), uiState)
            }
            SignOutButton(viewModel, navController = navController)
        }
    }
}



@Composable
fun UserImage(uiState: ProfileUiState) {
    Image(
        modifier = Modifier
            .size(150.dp)
            .padding(10.dp)
            .clip(CircleShape)
            .fillMaxHeight()
            .fillMaxWidth(),
        bitmap = decodeBase64Image(uiState.currentUser!!.image).asImageBitmap(),
        contentDescription = "Imagen de Usuario",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun UserNameTitle(uiState: ProfileUiState){
    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            uiState.currentUser!!.firstName.uppercase(),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h2,
            lineHeight = 44.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(
            uiState.currentUser.birthdate!!.year.toString() + " " +"años",
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun PersonalInfoText(){
    Text(text = "Información Personal",
        color = MaterialTheme.colors.primaryVariant,
        style = MaterialTheme.typography.h3,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}


@Composable
fun GenderTextField(modifier: Modifier, uiState: ProfileUiState){
    Box(modifier = modifier
        .wrapContentSize(Alignment.TopStart)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value =
                if (uiState.currentUser!!.gender == "male") {
                    "Masculino"
                } else if (uiState.currentUser.gender == "female") {
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
        )
    }
}

@Composable
fun EMailTextField(modifier: Modifier, uiState: ProfileUiState){
    TextField(
        modifier = modifier,
        value = uiState.currentUser!!.username,
        enabled = false,
        readOnly = true,
        onValueChange = {},
        label = { Text(text = "Email", fontWeight = FontWeight.Bold) },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun BirthdateTextField(modifier: Modifier, uiState: ProfileUiState) {
    TextField(
        modifier = modifier,
        value = uiState.currentUser!!.birthdate!!.date.toString() + "/" +
                uiState.currentUser.birthdate!!.month.toString() + "/" +
                uiState.currentUser.birthdate!!.year.toString(),
        enabled = false,
        readOnly = true,
        onValueChange = {},
        label = { Text(text = "Fecha de nacimiento", fontWeight = FontWeight.Bold) },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun SignOutButton(viewModel: ProfileViewModel, navController: NavController) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { viewModel.logout(navController) },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.error),
    ) {
        Text(text="Cerrar sesión")
    }
}
