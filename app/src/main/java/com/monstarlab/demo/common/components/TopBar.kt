package com.monstarlab.demo.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monstarlab.demo.R
import com.monstarlab.demo.theme.SecondaryText

@Composable
fun TopBar(onClose: () -> Unit, onLater: () -> Unit) {
    TopAppBar(elevation = 0.dp, backgroundColor = MaterialTheme.colors.background) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = SecondaryText
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onLater,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    color = SecondaryText,
                    text = stringResource(id = R.string.later_button),
                    style = TextStyle(fontSize = 18.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}