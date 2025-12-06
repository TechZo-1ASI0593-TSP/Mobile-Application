package com.techzo.cambiazo.presentation.profile.subscription

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.techzo.cambiazo.MainActivity
import com.techzo.cambiazo.common.Constants
import com.techzo.cambiazo.common.components.ButtonApp
import com.techzo.cambiazo.common.components.ButtonIconHeaderApp
import com.techzo.cambiazo.common.components.DialogApp
import com.techzo.cambiazo.common.components.MainScaffoldApp
import com.techzo.cambiazo.common.components.SubTitleText
import com.techzo.cambiazo.common.components.TextTitleHeaderApp
import com.techzo.cambiazo.domain.Plan

/**
 * Pantalla que muestra otros planes disponibles para cambiar tu suscripción.
 */
@Composable
fun PlansScreen(
    viewModel: SubscriptionViewModel = hiltViewModel(),
    back: () -> Unit = {},
    goToMySubscription: () -> Unit = {},
    onPlanClick: (String) -> Unit,
    activity: FragmentActivity? = null
) {
    val state = viewModel.state.value
    val subscription by viewModel.subscription

    // Mostramos solo los planes distintos al actual
    val availablePlans: List<Plan> =
        state.data?.filter { it.id != subscription.plan.id } ?: emptyList()

    var showCancelDialog by remember { mutableStateOf(false) }

    MainScaffoldApp(
        paddingCard = PaddingValues(top = 20.dp),
        contentsHeader = {
            Column(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // IMPORTANTE: usar parámetro posicional o 'iconVector', NO 'icon'
                ButtonIconHeaderApp(
                    Icons.Filled.ArrowBack,
                    onClick = { back() }
                )
                TextTitleHeaderApp("Suscripción")
            }
        },
        content = {
            Column(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 5.dp
                )
            ) {
                SubTitleText("Otros planes de suscripción")
                Spacer(modifier = Modifier.height(10.dp))

                availablePlans
                    .reversed()
                    .forEach { plan ->
                        SubscriptionPlanCard(
                            plan = plan,
                            onPlanClick = { onPlanClick(it) },
                            showCancelDialog = { showCancelDialog = true },
                            activity = activity
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
            }
        }
    )

    if (showCancelDialog) {
        DialogApp(
            message = "¿Estás seguro de que deseas anular tu suscripción?",
            description = "Perderás acceso a los beneficios exclusivos. Puedes reactivarla cuando quieras.",
            labelButton1 = "Cancelar suscripción",
            labelButton2 = "Mantener suscripción",
            onDismissRequest = { showCancelDialog = false },
            onClickButton1 = {
                viewModel.cancelSubscription()
                showCancelDialog = false
                goToMySubscription()
            },
            onClickButton2 = { showCancelDialog = false }
        )
    }
}

/**
 * Tarjeta individual de plan disponible.
 */
@Composable
fun SubscriptionPlanCard(
    plan: Plan,
    viewModel: SubscriptionViewModel = hiltViewModel(),
    onPlanClick: (String) -> Unit,
    showCancelDialog: () -> Unit,
    activity: FragmentActivity? = null
) {
    val actualPlanName = viewModel.subscription.value.plan.name

    val backgroundColor = when (plan.id) {
        1 -> Color.Gray
        2 -> Color.Black
        else -> Color(0xFFFFD146)
    }
    val iconColor = if (plan.id == 3) Color.Black else Color.White

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 10.dp
                )
        ) {

            // Encabezado con icono + nombre del plan
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Diamond,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = iconColor
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = plan.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    color = Color.Black
                )

                if (plan.id == 3) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Recomendado",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                Color(0xFFFFD146),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(horizontal = 8.dp)
                            .height(18.dp)
                            .offset(y = (-3).dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Beneficios del plan
            plan.benefits.forEach { benefits ->
                Text(
                    modifier = Modifier
                        .padding(start = 2.dp, bottom = 2.dp)
                        .padding(horizontal = 5.dp),
                    text = "• ${benefits.description}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            // Aviso especial para bajar a Lite (plan id = 1)
            if (plan.id == 1) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            Color(0xFFE1E1E1),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Al cambiarte a Lite, tu Plan $actualPlanName se cancelará.",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                color = Color(0xFFF2F2F2),
                thickness = 1.5.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Precio + botón de acción
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {

                Row(verticalAlignment = Alignment.Bottom) {
                    if (plan.price == 0.0) {
                        Text(
                            text = "Gratis",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    } else {
                        Text(
                            text = "$${plan.price}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = " c/mes",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFBFBFBF)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                val buttonText = if (plan.id == 1) "Cancelar Plan" else "Seleccionar"

                Box(modifier = Modifier.width(170.dp)) {
                    ButtonApp(
                        text = buttonText,
                        bgColor = Color.Black,
                        fColor = Color.White,
                        bColor = Color.Black,
                        onClick = {
                            if (activity != null) {
                                viewModel.startOrder(activity, plan.price, plan.id)
                            }

                            // Lógica anterior (por si quieres volver):
                            // if (plan.id == 1) {
                            //     showCancelDialog()
                            // } else {
                            //     onPlanClick(plan.id.toString())
                            // }
                        }
                    )
                }
            }
        }
    }
}
