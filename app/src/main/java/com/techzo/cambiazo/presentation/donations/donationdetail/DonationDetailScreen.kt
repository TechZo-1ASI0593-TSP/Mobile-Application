package com.techzo.cambiazo.presentation.donations.donationdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techzo.cambiazo.common.components.ButtonIconHeaderApp
import com.techzo.cambiazo.common.components.MainScaffoldApp
import com.techzo.cambiazo.common.components.TextTitleHeaderApp

@Composable
fun DonationDetailScreen(
    back: () -> Unit,
    ongDetailsViewModel: DonationDetailViewModel = hiltViewModel(),
){
    val ongState  = ongDetailsViewModel.ong.value

    MainScaffoldApp(
        paddingCard = PaddingValues(top = 20.dp),
        contentsHeader = {
            Column(
                Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ButtonIconHeaderApp(Icons.Filled.ArrowBack, onClick = { back() })
                TextTitleHeaderApp("ONGs")
            }
        }){
        val ong = ongState.data
        when {
            ongState.isLoading -> {
                CircularProgressIndicator(

                )
            }
            ong!= null -> {
                Text(ong.name)
            }
            else -> {
                Text(ongState.message)
            }
        }

    }
}