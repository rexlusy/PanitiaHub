package com.example.panitiahub.data.repository

import com.example.panitiahub.data.ContactItem
import com.example.panitiahub.data.SampleContactData
import com.example.panitiahub.data.remote.ContactDto
import com.example.panitiahub.data.remote.supabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository {
    private val client = supabaseClient

    suspend fun getContacts(): List<ContactItem> = withContext(Dispatchers.IO) {
        try {
            val response = client.from("event_contacts").select {
                order(column = "created_at", order = Order.DESCENDING)
            }.decodeList<ContactDto>()
            if (response.isNotEmpty()) {
                response.map { it.toContactItem() }
            } else {
                SampleContactData.initialContacts
            }
        } catch (e: Exception) {
            e.printStackTrace()
            SampleContactData.initialContacts
        }
    }

    suspend fun insertContact(contact: ContactItem): Boolean = withContext(Dispatchers.IO) {
        try {
            val dto = ContactDto.fromContactItem(contact)
            client.from("event_contacts").insert(dto)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun updateContact(contact: ContactItem): Boolean = withContext(Dispatchers.IO) {
        try {
            val dto = ContactDto.fromContactItem(contact)
            client.from("event_contacts").update(dto) {
                filter {
                    eq("id", dto.id)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteContact(id: String): Boolean = withContext(Dispatchers.IO) {
        try {
            client.from("event_contacts").delete {
                filter {
                    eq("id", id)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
