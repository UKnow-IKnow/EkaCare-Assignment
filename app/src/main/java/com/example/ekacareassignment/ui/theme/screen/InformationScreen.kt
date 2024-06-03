package com.example.ekacareassignment.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ekacareassignment.InformationViewModel
import com.example.ekacareassignment.R
import com.example.ekacareassignment.utils.DatePicker
import com.example.ekacareassignment.model.Information
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(viewModel: InformationViewModel) {
    val context = LocalContext.current
    val allInformation by viewModel.allInformation.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Information App") },
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = { showDialog = true },
            content = { Icon(Icons.Default.Add, contentDescription = "Add") })
    }, content = { padding ->
        if (allInformation.isEmpty()) {
            EmptyInformationText()
        } else {
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(allInformation) { info ->
                    InformationCard(info)
                }
            }
        }
    })

    if (showDialog) {
        AddInformationDialog(onDismiss = { showDialog = false }, onSave = { info ->
            viewModel.saveInformation(info)
            showDialog = false
            Toast.makeText(context, "Information saved", Toast.LENGTH_SHORT).show()
        })
    }
}

@Composable
fun InformationCard(info: Information) {
    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Name: ${info.fullName}", style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Age: ${info.age}", style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "DOB: ${info.dateOfBirth}", style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Address: ${info.address}", style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddInformationDialog(
    onDismiss: () -> Unit, onSave: (Information) -> Unit
) {
    var date by remember {
        mutableStateOf<LocalDate?>(null)
    }
    var dateString by remember { mutableStateOf("") }
    var ageString by remember { mutableStateOf("") }
    val information = remember {
        mutableStateOf(
            Information(
                fullName = "", age = 0, dateOfBirth = "", address = ""
            )
        )
    }
    val context = LocalContext.current
    var showError by remember { mutableStateOf(false) }


    fun saveInformation() {
        if (information.value.fullName.isNotEmpty() && ageString.isNotEmpty() && dateString.isNotEmpty() && information.value.address.isNotEmpty()) {
            onSave(information.value)
            showError = false
        } else {
            showError = true
            Toast.makeText(context, "Enter All Information", Toast.LENGTH_SHORT).show()
        }
    }

    AlertDialog(onDismissRequest = onDismiss, title = { Text("Add Information") }, text = {
        Column {
            OutlinedTextField(value = information.value.fullName,
                onValueChange = { information.value = information.value.copy(fullName = it) },
                shape = RoundedCornerShape(8.dp),
                label = { Text("Enter Name") })
            OutlinedTextField(
                value = ageString,
                onValueChange = {
                    ageString = it
                    information.value = information.value.copy(age = it.toIntOrNull() ?: 0)
                },
                shape = RoundedCornerShape(8.dp),
                label = { Text("Enter Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = information.value.dateOfBirth,
                onValueChange = {},
                label = { Text("Enter Date of Birth") },
                trailingIcon = {
                    IconButton(onClick = {
                        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                            date = LocalDate.of(year, month+1, day)
                            dateString =
                                date.toString() // Update the dateString with the selected date
                            information.value = information.value.copy(dateOfBirth = dateString)
                        }
                        DatePicker().openDatePicker(
                            context = context,
                            onDateSetListener = listener,
                            date = date ?: LocalDate.now(),
                        )
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Calendar Icon"
                        )
                    }
                },
                readOnly = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(value = information.value.address,
                onValueChange = { information.value = information.value.copy(address = it) },
                shape = RoundedCornerShape(8.dp),
                label = { Text("Address") }
            )
            if (showError) {
                Text(
                    text = "Please fill in all fields",
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }, confirmButton = {
        Button(onClick = { saveInformation() }) {
            Text("Save")
        }
    }, dismissButton = {
        Button(
            onClick = onDismiss
        ) {
            Text("Cancel")
        }
    })
}

@Composable
fun EmptyInformationText() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "No information available.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}