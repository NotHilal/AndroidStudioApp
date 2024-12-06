package com.example.appmobileplatform

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

val USERNAME_KEY = stringPreferencesKey("user_name")
val PROFILE_PIC_URI_KEY = stringPreferencesKey("profile_pic_uri")

suspend fun saveUsername(context: Context, username: String) {
    context.dataStore.edit { preferences ->
        preferences[USERNAME_KEY] = username
    }
}

fun getUsername(context: Context): Flow<String> {
    return context.dataStore.data
        .map { preferences ->
            preferences[USERNAME_KEY] ?: "User"
        }
}

suspend fun saveProfilePicUri(context: Context, uri: String) {
    context.dataStore.edit { preferences ->
        preferences[PROFILE_PIC_URI_KEY] = uri
    }
}

fun getProfilePicUri(context: Context): Flow<String> {
    return context.dataStore.data
        .map { preferences ->
            preferences[PROFILE_PIC_URI_KEY] ?: ""
        }
}
