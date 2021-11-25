package com.example.jetpackcomposeintroduction.simplifiedcoding

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeintroduction.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Title()
        }

    }
}

/*
@Composable
fun Greeting(name: String) {
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Text(text = "Hello $name!")
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeIntroductionTheme {
        Greeting("Android")
    }
}*/

@Composable
fun Title() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        Text(
            text = "Exam Warriors",
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            color = colorResource(id = R.color.purple_700),
            modifier = Modifier.clickable {
                Toast.makeText(context, "Title Clicked", Toast.LENGTH_LONG).show()
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Title()
}
