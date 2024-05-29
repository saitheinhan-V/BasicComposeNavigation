package com.my.basiccomposenavigation.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.my.basiccomposenavigation.ui.model.MainAction
import com.my.basiccomposenavigation.ui.model.MainForm
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun Overview(
    vm: MainViewModel
) {
    val mainForm = vm.mainForm.collectAsState()
    OverviewContent(
        form = mainForm.value,
        onAction = vm::onActionMain
    )

}

@Composable
fun OverviewContent(
    form: MainForm = MainForm(),
    onAction: (MainAction) -> Unit
) {
    OverviewScreen(
        onAccountClick = {
            onAction(
                MainAction.ClickAccount
            )
        },
        onBillClick = {
            onAction(
                MainAction.ClickBill
            )
        },
        onValueChanged = { s ->
            onAction(
                MainAction.ChangeAmount(amount = s)
            )
        },
        onDetailClick = {
            onAction(
                MainAction.ClickDetail
            )
        },
        mainForm = form,
        amount = form.amount
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalCoilApi::class)
@Composable
fun OverviewScreen(
    onAccountClick: () -> Unit,
    onBillClick: () -> Unit,
    onValueChanged: (String) -> Unit,
    onDetailClick: () -> Unit,
    mainForm: MainForm = MainForm(),
    amount: String = ""
) {

    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val storagePermissionState = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.my.basiccomposenavigation.provider", file
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    var galleryImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val pickImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            it?.let{
                galleryImageUri = it
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val storagePermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
//                    vm.onDeclinePermission(storage)
                }
            }
        )


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        Text(text = "This is overview")
        TextButton(
            onClick = onAccountClick
        ) {
            Text(
                text = "Go To Account"
            )
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount,
            onValueChange = onValueChanged,
            label = { Text("Enter amount") },
            keyboardOptions = KeyboardOptions.Default,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        )
        TextButton(
            onClick = onBillClick
        ) {
            Text(
                text = "Go To Bill"
            )
        }
        TextButton(
            onClick = onDetailClick
        ) {
            Text(
                text = "Go To Details"
            )
        }
        TextButton(
            onClick = {

                cameraPermissionState.launchPermissionRequest()
//                LaunchedEffect(cameraPermissionState) {
//                    if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
//                        // Show rationale if needed
//                    } else {
//                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//                    }
//                }
            }
        ) {
            Text(
                text = "Open Camera"
            )
        }
//        if (cameraPermissionState.status.isGranted) {
//            Text(
//                text = "Camera permission granted"
//            )
//        }
        Button(onClick = {
//            val permissionCheckResult =
//                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//                cameraLauncher.launch(uri)
//            } else {
//                // Request a permission
//                permissionLauncher.launch(Manifest.permission.CAMERA)
//            }
            if(cameraPermissionState.status.isGranted){
                cameraLauncher.launch(uri)
            }else{
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text(text = "Capture Image From Camera")
        }
        if (capturedImageUri.path?.isNotEmpty() == true) {
            Image(

                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .height(100.dp)
                    .width(100.dp),
                painter = rememberImagePainter(capturedImageUri),
//                painter = painterResource(id = capturedImageUri)
                contentDescription = null
            )
        }
        Button(
            onClick = {
                if (storagePermissionState.status.isGranted) {
                    pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }else{
                    storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        ){
            Text(text = "Choose Image")
        }
        if(galleryImageUri.path?.isNotEmpty() == true){
            Image(

                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .height(100.dp)
                    .width(100.dp),
                painter = rememberImagePainter(galleryImageUri),
//                painter = painterResource(id = capturedImageUri)
                contentDescription = null
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
}



@Preview
@Composable
fun PreviewOverview() {
    val vm: MainViewModel = hiltViewModel()
//    Overview(vm = vm)
//    OverviewScreen(onAccountClick = { }, onBillClick = { }, onValueChanged = {})
}