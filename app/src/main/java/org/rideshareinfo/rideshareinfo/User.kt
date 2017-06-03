package org.rideshareinfo.rideshareinfo

import com.google.firebase.database.DataSnapshot

data class User(
        val name: String,
        val status: String) {
    companion object {
        fun fromFirebase(snapshot: DataSnapshot): User {
            return User(
                    name = snapshot.key,
                    status = snapshot.child("status").value as String
            )
        }
    }
}
