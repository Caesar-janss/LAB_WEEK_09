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

//Previously we extend AppCompatActivity,
//now we extend ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            //Here, we wrap our content with the theme
            LAB_WEEK_09Theme {
                //A surface container using the 'background' color from the theme
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

//Here, instead of defining it in an XML file,
//we create a composable function called Home
@Composable
fun Home() {
    //Here, we create a mutable state list of Student
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    //Here, we create a mutable state of Student
    var inputField by remember { mutableStateOf(Student("")) }

    //We call the HomeContent composable
    HomeContent(
        listData,
        inputField,
        { input -> inputField = inputField.copy(name = input) },
        {
            if (inputField.name.isNotBlank()) {
                listData.add(inputField)
                inputField = Student("")
            }
        }
    )
}

//HomeContent is used to display the content of the Home composable
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        //Input form section
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //Here, we call the OnBackgroundTitleText UI Element
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

                //Here, we use TextField to display a text input field
                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { onInputValueChange(it) }
                )

                //Here, we call the PrimaryTextButton UI Element
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click),
                    onClick = onButtonClick
                )
            }
        }

        //List of items section
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Here, we call the OnBackgroundItemText UI Element
                OnBackgroundItemText(text = item.name)
            }
        }

        //Debug text to display list content
        item {
            //Display listData as a string (for debugging purpose)
            Text(
                text = listData.toString(),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

//Preview for development only
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
            onButtonClick = {}
        )
    }
}

//Declare a data class called Student
data class Student(
    var name: String
)
