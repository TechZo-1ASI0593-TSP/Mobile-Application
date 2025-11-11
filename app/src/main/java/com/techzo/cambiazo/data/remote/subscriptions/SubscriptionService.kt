package com.techzo.cambiazo.data.remote.subscriptions

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Servicio remoto para gestionar suscripciones y planes desde la API de CambiaZo.
 */
interface SubscriptionService {

    // ------------------------------------------------------
    // SUBSCRIPTIONS (SUSCRIPCIONES)
    // ------------------------------------------------------

    /**
     * Obtiene la suscripción activa asociada a un usuario específico.
     *
     * @param userId identificador del usuario.
     * @return [Response] que envuelve la [SubscriptionDto] retornada por el backend.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("subscriptions/user/{id}")
    suspend fun getUserSubscription(
        @Path("id") userId: Int
    ): Response<SubscriptionDto>

    /**
     * Crea una nueva suscripción para un usuario en base al plan seleccionado.
     *
     * @param request cuerpo de la petición con el estado, usuario y plan.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("subscriptions")
    suspend fun createSubscription(
        @Body request: SubscriptionRequestDto
    ): Response<SubscriptionResponseDto>

    /**
     * Actualiza el estado de una suscripción existente.
     *
     * @param subscriptionId identificador de la suscripción a actualizar.
     * @param subscriptionRequest DTO con los nuevos datos de la suscripción.
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("subscriptions/status/{subscriptionId}")
    suspend fun updateSubscription(
        @Path("subscriptionId") subscriptionId: Int,
        @Body subscriptionRequest: SubscriptionRequestDto
    ): Response<SubscriptionResponseDto>

    // ------------------------------------------------------
    // PLANS (PLANES)
    // ------------------------------------------------------

    /**
     * Recupera la lista completa de planes disponibles en el sistema.
     *
     * @return [Response] con una lista de [PlansDto].
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("plans")
    suspend fun getPlans(): Response<List<PlansDto>>
}
