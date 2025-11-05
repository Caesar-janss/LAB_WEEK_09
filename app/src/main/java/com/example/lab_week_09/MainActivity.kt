package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

// -----------------------------------------------------------------------------------------
// Previously we extend AppCompatActivity,
// now we extend ComponentActivity
// -----------------------------------------------------------------------------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Here, we use setContent instead of setContentView
        setContent {
            // Wrap our composable content with the app theme
            LAB_WEEK_09Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call Home() composable to display the main UI
                    Home()
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// 🧩 Home() – The root composable
// This composable manages the states and passes them to HomeContent()
// -----------------------------------------------------------------------------------------
@Composable
fun Home() {
    // 🟢 Create a mutable list of Student using remember
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // 🟢 Create a mutable state for text input
    var inputField by remember { mutableStateOf(Student("")) }

    // 🟢 Call HomeContent() and pass required parameters
    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = { input ->
            inputField = inputField.copy(name = input)
        },
        onButtonClick = {
            if (inputField.name.isNotBlank()) {
                listData.add(inputField)
                inputField = Student("")
            }
        }
    )
}

// -----------------------------------------------------------------------------------------
// 🧩 HomeContent() – displays the actual UI and handles user input
// -----------------------------------------------------------------------------------------
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    // Use LazyColumn for vertical scrolling list
    LazyColumn {
        // Header section: input + button
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.enter_item))

                Spacer(modifier = Modifier.height(8.dp))

                // TextField for user input
                TextField(
                    value = inputField.name,
                    onValueChange = { onInputValueChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeholder = { Text("Enter name here") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Button for adding input to list
                Button(onClick = { onButtonClick() }) {
                    Text(text = stringResource(id = R.string.button_click))
                }
            }
        }

        // Display all list items
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.name)
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// 🧩 Preview for design-time in Android Studio
// -----------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        // Static preview with dummy data (non-interactive)
        val sampleList = mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono"),
            Student("Preview Sample")
        )
        HomeContent(
            listData = sampleList,
            inputField = Student(""),
            onInputValueChange = {},
            onButtonClick = {}
        )
    }
}

// -----------------------------------------------------------------------------------------
// 🧩 Data class Student – represents a student entity
// -----------------------------------------------------------------------------------------
data class Student(
    var name: String
)
