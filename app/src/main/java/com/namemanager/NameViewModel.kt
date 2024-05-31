package com.namemanager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NameViewModel(private val repository: NameRepository) : ViewModel() {
    val allNames: LiveData<List<Name>> = repository.allNames.asLiveData()
    fun insert(name: Name) = viewModelScope.launch {
        repository.insert(name)
    }
}

class NameViewModelFactory(private val repository: NameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
