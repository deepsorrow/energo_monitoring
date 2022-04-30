package com.example.energo_monitoring.compose.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.compose.DrawerScreens
import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager
import com.example.energo_monitoring.R

@Composable
fun Drawer(onDestinationClicked: (String) -> Unit) {
    var userName = SharedPreferencesManager.getUsername(LocalContext.current)
    if (userName == null)
        userName = ""

    val screens = listOf(
        DrawerScreens.Checks,
        DrawerScreens.Sync,
        DrawerScreens.Exit,
    )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 48.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(bottom = 15.dp),
                painter = painterResource(id = R.drawable.client_logo),
                contentDescription = "Logo"
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Person icon",
                    modifier = Modifier.size(35.dp),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.h5,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 25.dp)
                )
            }
            Divider()
            screens.forEach { screen ->
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDestinationClicked(
                                screen.route
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = screen.iconId),
                        contentDescription = "Icon",
                        modifier = Modifier.size(35.dp)
                    )
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(start = 25.dp)
                    )
                }
            }
    }
}

@Composable
@Preview
fun PreviewDrawer() {
    Drawer {}
}