package org.rideshareinfo.rideshareinfo

import android.util.Log
import com.google.firebase.database.*

class FirebaseUser(private val user: User) {

    private val TAG = FirebaseUser::class.java.simpleName

    private val dbRefBaseState: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child(KEY_RIDESHARE_DATA_BASE)
            .child(KEY_USERS_NODE)
            .child(user.name)

    init {
        this.dbRefBaseState.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                Log.d(TAG, "LOGGGING!!! onDataChange")
            }

            override fun onCancelled(databaseError: DatabaseError?) {
                Log.d(TAG, "LOGGGING!!! onDataChange")
            }
        })
    }

    fun updateUserStatus(user: User) {
        dbRefBaseState.child(KEY_STATUS_NODE).setValue(user.status)
        dbRefBaseState.child(KEY_LOCATION_LAT).setValue(user.latLongLocation.first)
        dbRefBaseState.child(KEY_LOCATION_LONG).setValue(user.latLongLocation.second)
        dbRefBaseState.child(KEY_READY_TIME).setValue(user.readyTime)
    }
}
