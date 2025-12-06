package com.techzo.cambiazo.data.remote.subscriptions

import com.techzo.cambiazo.domain.Benefit
import com.techzo.cambiazo.domain.Plan
import com.techzo.cambiazo.domain.Subscription
import com.techzo.cambiazo.domain.SubscriptionResponse

// ------------------------------------------------------
// SUBSCRIPTION DTOs
// ------------------------------------------------------

/**
 * DTO que representa una suscripción obtenida desde la API.
 */
data class SubscriptionDto(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val state: String,
    val userId: Int,
    val plan: Plan
)

/**
 * Mapeo de SubscriptionDto (capa remota) a Subscription (capa de dominio).
 */
fun SubscriptionDto.toSubscription(): Subscription =
    Subscription(
        id = id,
        startDate = startDate,
        endDate = endDate,
        state = state,
        userId = userId,
        plan = plan
    )

/**
 * DTO utilizado para enviar la creación/actualización de una suscripción.
 */
data class SubscriptionRequestDto(
    val state: String,
    val userId: Int,
    val planId: Int
)

/**
 * DTO de respuesta que retorna la API al operar sobre suscripciones.
 */
data class SubscriptionResponseDto(
    val id: Int,
    val state: String,
    val planId: Int,
    val userId: Int,
    val startDate: String,
    val endDate: String
)

/**
 * Conversión de SubscriptionResponseDto (remoto) a SubscriptionResponse (dominio).
 */
fun SubscriptionResponseDto.toSubscription(): SubscriptionResponse =
    SubscriptionResponse(
        id = id,
        state = state,
        planId = planId,
        userId = userId,
        startDate = startDate,
        endDate = endDate
    )

// ------------------------------------------------------
// PLAN DTOs
// ------------------------------------------------------

/**
 * DTO de planes que viene desde el backend.
 */
data class PlansDto(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val benefits: List<Benefit>
)

/**
 * Conversión de PlansDto (remoto) a Plan (dominio).
 */
fun PlansDto.toPlan(): Plan =
    Plan(
        id = id,
        name = name,
        description = description,
        price = price,
        benefits = benefits
    )

// ------------------------------------------------------
// BENEFITS DTOs
// ------------------------------------------------------

/**
 * DTO de beneficios asociados a un plan.
 */
data class BenefitsDto(
    val id: Int,
    val description: String,
    val planId: String
)
