package org.rideshareinfo.rideshareinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById(R.id.users) as RecyclerView
        list.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter()
        list.adapter = adapter
        adapter.setUsers(listOf(
                User("user1", "driving", 0L, 0.0 to 0.0),
                User("user2", "need a ride", 0L, 0.0 to 0.0)
        ))

        findViewById(R.id.good).setOnClickListener {
            rideSurvayNotification(this)
        }
    }
}

class UserAdapter : RecyclerView.Adapter<UserAdapter.Holder>() {
    private var users = listOf<User>()

    fun setUsers(users: List<User>) {
        this.users = users
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

