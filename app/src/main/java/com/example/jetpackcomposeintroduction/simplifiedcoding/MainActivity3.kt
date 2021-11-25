package com.example.jetpackcomposeintroduction.simplifiedcoding

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeintroduction.R
import com.example.jetpackcomposeintroduction.ui.ui.theme.JetpackComposeIntroductionTheme
import java.util.regex.Pattern

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserList()
        }
    }
}

@Composable
fun UserCardNew(user: User) {
    Card(elevation = 12.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.yash),
                contentDescription = "",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter
            )

            Column() {
                Text(text = user.name, modifier = Modifier.padding(10.dp))
                LinkifyText(text = user.about, modifier = Modifier.padding(10.dp))
                Button(onClick = {
                }) {
                    Text(text = "View Profile")
                }
            }
        }
    }
}

data class User(
    val name: String,
    val about: String
)

val users = listOf(
    User("Yash Bhardwaj", "www.facebook.com"),
    User("Shalu Sharma", "hehe, check this link: http://www.manmatters.com/"),
    User("Arpit jain", "hehe, check this link: http://www.     manmatters.com/"),
    User("Preksha jain", "hehe, check this link: http://  www.manmatters.com/"),
    User("Rohit Kumar", "hehe, check this link: http: // www. manmatters. com /"),
    User("Sourabh Nayak", "hehe, check this link: "),
    User("Ankush Agrawal", "hehe, check this link: manmatters.com"),
    User("Shubham Mesharam", "You can find the awesome healt products on http://manmatters.com"),
)

@Composable
fun UserList(){
    /*Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for (i in 1..10){
            UserCardNew()
        }
    }*/
    LazyColumn {
        items(users){  user ->
            UserCardNew(user)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    UserList()
}

private val urlPattern: Pattern = Pattern.compile(
    "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
    Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
)

fun extractUrls(text: String): List<LinkInfos> {
    val matcher = urlPattern.matcher(text)
    var matchStart: Int
    var matchEnd: Int
    val links = arrayListOf<LinkInfos>()

    while (matcher.find()) {
        matchStart = matcher.start(1)
        matchEnd = matcher.end()

        var url = text.substring(matchStart, matchEnd)
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://$url"

        links.add(LinkInfos(url, matchStart, matchEnd))
    }
    return links
}


@Composable
fun LinkifyText(text: String, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }
    val linksList = extractUrls(text)
    val annotatedString = buildAnnotatedString {
        append(text)
        linksList.forEach {
            addStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                start = it.start,
                end = it.end
            )
            addStringAnnotation(
                tag = "URL",
                annotation = it.url,
                start = it.start,
                end = it.end
            )
        }
    }
    Text(text = annotatedString, style = MaterialTheme.typography.body1, modifier = modifier.pointerInput(Unit) {
        detectTapGestures { offsetPosition ->
            layoutResult.value?.let {
                val position = it.getOffsetForPosition(offsetPosition)
                annotatedString.getStringAnnotations(position, position).firstOrNull()
                    ?.let { result ->
                        if (result.tag == "URL") {
                            uriHandler.openUri(result.item)
                        }
                    }
            }
        }
    },
        onTextLayout = { layoutResult.value = it }
    )
}