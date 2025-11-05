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
import com.example.lab_week_09.ui.theme.*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

//Main composable
@Composable
fun Home() {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }
    var inputField by remember { mutableStateOf(Student("")) }
    var isFinished by remember { mutableStateOf(false) }

    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = { input -> inputField = inputField.copy(name = input) },
        onSubmitClick = {
            if (inputField.name.isNotBlank()) {
                listData.add(inputField)
                inputField = Student("")
            }
        },
        onFinishClick = {
            isFinished = true
        },
        isFinished = isFinished
    )
}

//Display UI and list content
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onFinishClick: () -> Unit,
    isFinished: Boolean
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

                //Input field
                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { onInputValueChange(it) }
                )

                //Button Row: Submit + Finish
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //Submit button (Blue)
                    DynamicTextButton(
                        text = stringResource(id = R.string.button_click),
                        color = BlueCustom,
                        onClick = onSubmitClick
                    )
                    //Finish button (Green)
                    DynamicTextButton(
                        text = "Finish",
                        color = GreenCustom,
                        onClick = onFinishClick
                    )
                }
            }
        }

        //List of names (hidden if finished)
        if (!isFinished) {
            items(listData) { item ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OnBackgroundItemText(text = item.name)
                }
            }
        } else {
            //Show summary when finished
            item {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleText(
                        text = "Final List:",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    for (student in listData) {
                        ItemText(text = student.name, color = GreenCustom)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OnBackgroundItemText(text = "Process finished successfully ✅")
                }
            }
        }

        //Debug output at bottom
        item {
            Text(
                text = listData.toString(),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        val sampleList = mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono"),
            Student("Tinky Winky")
        )
        HomeContent(
            listData = sampleList,
            inputField = Student(""),
            onInputValueChange = {},
            onSubmitClick = {},
            onFinishClick = {},
            isFinished = false
        )
    }
}

//Data class
data class Student(
    var name: String
)
