package com.crud.repository

import com.crud.data.db.entity.SubscriberEntity

interface SubscriberRepository {

    suspend fun insertSubscriber(name: String, birth: String, cpf: String, tel: String): Long

    suspend fun updateSubscriber(id: Long, name: String, birth: String, cpf: String, tel: String)

    suspend fun deleteSubscriber(id: Long)

    suspend fun deleteAllSubscriber()

    suspend fun getAllSubscriber(): List<SubscriberEntity>
}