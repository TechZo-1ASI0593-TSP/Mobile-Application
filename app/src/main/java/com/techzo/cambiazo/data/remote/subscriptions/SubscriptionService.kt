package com.techzo.cambiazo.data.remote.subscriptions

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Servicio API para gestionar suscripciones y planes.
 */
interface SubscriptionService {

    // ------------------------------------------------------
    // SUBSCRIPTIONS
    // ------------------------------------------------------

    /**
     * Obtiene la suscripción activa de un usuario específico.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("subscriptions/user/{id}")
    suspend fun getUserSubscription(
        @Path("id") userId: Int
    ): Response<SubscriptionDto>

    /**
     * Crea una nueva suscripción para un usuario.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("subscriptions")
    suspend fun createSubscription(
        @Body request: SubscriptionRequestDto
    ): Response<SubscriptionResponseDto>

    /**
     * Actualiza el estado de una suscripción existente.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("subscriptions/status/{subscriptionId}")
    suspend fun updateSubscription(
        @Path("subscriptionId") subscriptionId: Int,
        @Body subscriptionRequest: SubscriptionRequestDto
    ): Response<SubscriptionResponseDto>

    // ------------------------------------------------------
    // PLANS
    // ------------------------------------------------------

    /**
     * Obtiene la lista de todos los planes disponibles.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("plans")
    suspend fun getPlans(): Response<List<PlansDto>>
}
