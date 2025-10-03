package com.techzo.cambiazo.data.remote.donations

data class Ong(
    val id: Int,
    val name: String,
    val type: String,
    val aboutUs: String,
    val missionAndVision: String,
    val supportForm: String,
    val address: String,
    val email: String,
    val phone: String,
    val logo: String,
    val website: String,
    val schedule: String,
    val categoryOngId: CategoryOng,
    val projects: List<Project>,
    val accountNumbers: List<AccountNumber>
)

data class CategoryOng(
    val id: Int,
    val name: String,
    val categoryId: Int
)

data class Project(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val name: String,
    val description: String,
    val ongId: Int
)

data class AccountNumber(
    val id: Int,
    val accountNumber: String,
    val bankName: String,
    val currency: String,
    val ongId: Int
)