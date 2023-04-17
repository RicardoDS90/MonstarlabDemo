package com.monstarlab.demo.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    title: String,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        shape = RoundedCornerShape(5.dp),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {

        if (icon != null) {
            Icon(
                modifier = Modifier.padding(start = 40.dp, end = 5.dp).size(35.dp),
                painter = painterResource(id = icon),
                contentDescription = "Button Text Icon"
            )
    }
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = title,
            style = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold),
            color = Color.Black
        )
    }
}