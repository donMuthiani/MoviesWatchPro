package com.muthiani.movieswatchpro.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.ui.intro.ButtonUi

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_button),
                contentDescription = "Google Icon",
                tint = Color.Unspecified)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in with Google", color = Color.Black)
        }
    }
}

@Composable
fun SignInButtons() {
    Column(modifier = Modifier.fillMaxWidth()) {
        ButtonUi(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            text = "Sign In",
            textColor = Color.White,
            backgroundColor = Color.Black) {
        }

        Spacer(modifier = Modifier.size(24.dp))

        GoogleSignInButton {

        }
    }
}