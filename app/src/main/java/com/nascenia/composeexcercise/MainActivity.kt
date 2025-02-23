package com.nascenia.composeexcercise

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.nascenia.composeexcercise.ui.theme.ComposeExcerciseTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExcerciseTheme {
                MainScreen { message -> showNotification(message) }
            }
        }
    }

    private fun showNotification(message: Message) {
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(R.drawable.chat_unread)
            .setContentTitle(message.author)
            .setContentText(message.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
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
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = msg.author, fontSize = 18.sp)
            Text(text = msg.body, fontSize = 12.sp)
        }
    }
}

@Composable
fun MessageList(messages: List<Message>?, showNotifications: (Message) -> Unit) {
    LazyColumn {
        if (messages == null) {
            // Show shimmer placeholders while loading
            items(10) { ShimmerMessageCard() }
        } else {
            // Show actual messages
            items(messages) { message ->
                MessageCard(msg = message, onClick = { showNotifications(message) })
            }
        }
    }
}

@Composable
fun ShimmerMessageCard() {
    val shimmerBrush = rememberShimmerBrush()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(shimmerBrush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(15.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )
        }
    }
}

@Composable
fun rememberShimmerBrush(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, 0f),
        end = Offset(translateAnim + 1000f, 0f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(showNotifications: (Message) -> Unit) {
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
                        context.startActivity(Intent(context,MainActivity2::class.java))
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
                            contentDescription = "More Options"
                        )
                    }
                }
            )
        }
    ){ paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            var messages by remember { mutableStateOf<List<Message>?>(null) }

            // Simulate API delay before loading actual messages
            LaunchedEffect(Unit) {
                delay(2000) // Show shimmer for 2 seconds
                messages = List(20) {
                    when (it % 4) {
                        0 -> Message("Tanvir Shaharia", "Software Engineer")
                        1 -> Message("John Doe", "Android Developer")
                        2 -> Message("Jane Smith", "iOS Developer")
                        else -> Message("Alice Johnson", "Flutter Engineer")
                    }
                }
            }

            MessageList(messages = messages, showNotifications = showNotifications)
        }
    }
}
