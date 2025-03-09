package com.project.creditPay.presentationLayer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.responsiveLayer.RetrofitC
import com.project.creditPay.responsiveLayer.models.Users
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.OkHttpClient

class CommonViewModel : ViewModel() {
    private val progress = MutableStateFlow(LoadersResults())
    val loader = progress
    private val textLoader = MutableStateFlow<String?>(null)
    val textChanger = textLoader
    private val users = MutableStateFlow<ArrayList<Users>?>(null)
    val detailsOfLogin = users
    fun signUp(name: String, mobile: String, password: String) {

        progress.value = LoadersResults(
            condition = true
        )
        viewModelScope.async {
            val hashMap = hashMapOf(
                "name" to name,
                "mobile" to mobile,
                "password" to password
            )

            fireBase.collection("userOfBank").where(Filter.equalTo("mobile", mobile)).get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        fireBase.collection("userOfBank").add(hashMap).addOnFailureListener {
                            progress.value = LoadersResults(
                                message = it.message, condition = false
                            )
                        }.addOnSuccessListener {
                            progress.value = LoadersResults(
                                message = "Success", condition = false
                            )
                        }
                    } else {
                        progress.value = LoadersResults(
                            message = "Try with another mobile number", condition = false
                        )

                    }
                }.addOnFailureListener {
                    progress.value = LoadersResults(
                        message = it.message, condition = false
                    )
                }


        }.start()
    }

    private val fireBase = Firebase.firestore

    fun logIn(
        mobile: String,
        password: String,
    ) {
        progress.value = LoadersResults(message = null, condition = true)
        viewModelScope.async {
            fireBase.collection("userOfBank").where(
                Filter.equalTo("mobile", mobile)
            ).where(
                Filter.equalTo("password", password)
            ).get().addOnFailureListener {
                progress.value = LoadersResults(
                    message = it.message.toString(),
                    condition = false
                )
            }.addOnSuccessListener {
                var name: String? = null
                if (it.documents.isEmpty()) {
                    name = "Invalid user"
                } else {
                    val toObjects = it.toObjects(Users::class.java) as ArrayList
                    Log.i("sdfssdfdfdf", "$toObjects")

                    users.value=toObjects
                }
                progress.value = LoadersResults(
                    condition = false, message = name
                )
            }

        }.start()

    }

    private val retrofit = RetrofitC().api

}