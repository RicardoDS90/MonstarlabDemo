package com.monstarlab.demo.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.monstarlab.demo.theme.SecondaryText

@Composable
fun SimpleDialog(
    openDialog: Boolean,
    message: String,
    onDismiss: () -> Unit,
) {
    if (openDialog) {
        Dialog(onDismissRequest = {
            onDismiss()
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = 10.dp)
            ) {
                Column(modifier = Modifier.padding(all = 16.dp)) {
                    Text(text = message, color = SecondaryText)
                }
            }
        }
    }
}