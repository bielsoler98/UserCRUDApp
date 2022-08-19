package com.senyor_o.usercrudapp.presentation.common_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun UserCard(
    name: String,
    birthdate: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onClick()
            },
        elevation = 10.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(15.dp)
        ) {
            val (iconRef, nameRef, birthdayRef) = createRefs()
            Image(
                imageVector = Icons.Default.Face,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop, // crop the image if it's not a square
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.Gray, CircleShape)   // add a border (optional)
                    .padding(8.dp)
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(nameRef) {
                            top.linkTo(parent.top)
                            start.linkTo(iconRef.end)
                            end.linkTo(parent.end)
                        },
                    text  = name,
                    fontWeight = FontWeight.Bold
                )

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(birthdayRef) {
                        top.linkTo(nameRef.bottom)
                        start.linkTo(iconRef.end)
                        end.linkTo(parent.end)
                    },
                text = birthdate
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    UserCard(
        name = "Miquel",
        birthdate = "01/01/2001",
        onClick = {}
    )
}