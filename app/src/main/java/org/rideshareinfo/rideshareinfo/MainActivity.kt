package org.rideshareinfo.rideshareinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter

    private val dbRefBaseState: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child(KEY_RIDESHARE_DATA_BASE)
            .child(KEY_USERS_NODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById(R.id.users) as RecyclerView
        list.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter()
        list.adapter = userAdapter
        userAdapter.users = listOf(
                User("user1", "driving"),
                User("user2", "need a ride")
        )

        findViewById(R.id.driving).setOnClickListener {
            val user = FirebaseUser(userAdapter.users[0].copy(status = "driving"))
            user.updateUserStatus()
        }

        findViewById(R.id.ride).setOnClickListener {
            val user = FirebaseUser(userAdapter.users[0].copy(status = "need a ride"))
            user.updateUserStatus()
        }

        findViewById(R.id.good).setOnClickListener {
            val user = FirebaseUser(userAdapter.users[0].copy(status = "don't need a ride"))
            user.updateUserStatus()
        }

        findViewById(R.id.before).setOnClickListener {
            askUserStatusNotification(this)
        }

        findViewById(R.id.after).setOnClickListener {
            rideSurvayNotification(this)
        }
    }

    override fun onStart() {
        super.onStart()
        dbRefBaseState.addValueEventListener(allUserEventListener)
    }

    override fun onStop() {
        super.onStop()
        dbRefBaseState.removeEventListener(allUserEventListener)
    }

    val allUserEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            val out = ArrayList<User>()
            dataSnapshot?.children?.forEach {
                out.add(User(
                        name = it.key as String,
                        status = it.child(KEY_STATUS_NODE).value as String
                ))
            }
            userAdapter.users =  out
        }

        override fun onCancelled(databaseError: DatabaseError?) {

        }
    }
}

class UserAdapter : RecyclerView.Adapter<UserAdapter.Holder>() {
    var users: List<User> = emptyList()
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = 2

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name) as TextView
        val status: TextView = itemView.findViewById(R.id.status) as TextView

        fun bind(user: User) {
            name.text = user.name
            status.text = user.status
        }
    }
}

