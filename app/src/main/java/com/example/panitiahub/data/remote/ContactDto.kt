package com.example.panitiahub.data.remote

import com.example.panitiahub.data.ContactItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    @SerialName("id")
    val id: String,
    @SerialName("contact_type")
    val contactType: String,
    @SerialName("name")
    val name: String,
    @SerialName("division")
    val division: String? = null,
    @SerialName("role")
    val role: String,
    @SerialName("institution")
    val institution: String? = null,
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String = "kontak@panitiahub.id"
) {
    fun toContactItem(): ContactItem = ContactItem(
        id = id,
        contactType = contactType,
        name = name,
        division = division,
        role = role,
        institution = institution,
        phone = phone,
        email = email
    )

    companion object {
        fun fromContactItem(item: ContactItem): ContactDto = ContactDto(
            id = item.id,
            contactType = item.contactType,
            name = item.name,
            division = item.division,
            role = item.role,
            institution = item.institution,
            phone = item.phone,
            email = item.email
        )
    }
}
