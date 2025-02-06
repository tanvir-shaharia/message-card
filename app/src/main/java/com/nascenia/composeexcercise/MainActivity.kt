package com.nascenia.composeexcercise

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nascenia.composeexcercise.ui.theme.ComposeExcerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeExcerciseTheme {
                MainScreen()
            }
        }
    }
}

data class Message(val author: String, val body: String)


@Composable
fun MessageCard(msg: Message, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.tanvir),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = msg.author,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = msg.body,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun MessageList(messages: List<Message>, onItemClick: (Message) -> Unit) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message, onClick = {
                onItemClick(message)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Message Card",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Not Implement it !", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
                            contentDescription = "More Options"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val messages = List(20) {
                when (it % 4) {
                    0 -> Message("Tanvir Shaharia", "Software Engineer")
                    1 -> Message("John Doe", "Android Developer")
                    2 -> Message("Jane Smith", "iOS Developer")
                    else -> Message("Alice Johnson", "Flutter Engineer")
                }
            }
            MessageList(messages = messages, onItemClick = { message ->
                Toast.makeText(context, message.body, Toast.LENGTH_SHORT).show()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageListPreview() {
    ComposeExcerciseTheme {
        val sampleMessages = List(4) { Message("John Doe", "Android Developer") }
        MessageList(messages = sampleMessages) {}
    }
}
