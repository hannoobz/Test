package com.hannoobz.internship.data.repository

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hannoobz.internship.data.model.User
import org.json.JSONException

class UserRepository(private val context: Context) {
    fun getUsers(page: Int, perPage: Int, onSuccess: (List<User>, Int) -> Unit) {
        val url = "https://reqres.in/api/users?page=$page&per_page=$perPage"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    val totalPages = response.getInt("total_pages")
                    val users = mutableListOf<User>()
                    val dataArray = response.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val jsonObject = dataArray.getJSONObject(i)
                        users.add(
                            User(
                                id = jsonObject.getInt("id"),
                                firstName = jsonObject.getString("first_name"),
                                lastName = jsonObject.getString("last_name"),
                                email = jsonObject.getString("email"),
                                avatar = jsonObject.getString("avatar")
                            )
                        )
                    }
                    onSuccess(users, totalPages)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            {_->
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-api-key"] = "reqres-free-v1"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }
}