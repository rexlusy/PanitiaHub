package com.example.panitiahub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panitiahub.data.ContactItem
import com.example.panitiahub.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    private val repository: ContactRepository = ContactRepository()
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<ContactItem>>(emptyList())
    val contacts: StateFlow<List<ContactItem>> = _contacts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadContacts()
    }

    fun loadContacts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val list = repository.getContacts()
                _contacts.value = list
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Gagal memuat data kontak"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addContact(contact: ContactItem, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            val success = repository.insertContact(contact)
            if (success) {
                _contacts.value = listOf(contact) + _contacts.value
                onSuccess()
            } else {
                _errorMessage.value = "Gagal menambahkan kontak"
            }
            _isLoading.value = false
        }
    }

    fun updateContact(contact: ContactItem, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            val success = repository.updateContact(contact)
            if (success) {
                _contacts.value = _contacts.value.map { if (it.id == contact.id) contact else it }
                onSuccess()
            } else {
                _errorMessage.value = "Gagal memperbarui kontak"
            }
            _isLoading.value = false
        }
    }

    fun deleteContact(id: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            val success = repository.deleteContact(id)
            if (success) {
                _contacts.value = _contacts.value.filter { it.id != id }
                onSuccess()
            } else {
                _errorMessage.value = "Gagal menghapus kontak"
            }
            _isLoading.value = false
        }
    }
}
