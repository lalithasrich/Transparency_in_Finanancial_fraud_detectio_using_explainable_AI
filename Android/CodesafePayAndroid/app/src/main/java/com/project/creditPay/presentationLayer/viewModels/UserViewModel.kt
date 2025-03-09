package com.project.creditPay.presentationLayer.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.project.creditPay.dataLayer.models.DataClass
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.dataLayer.response.RetrofitPoint
import com.project.creditPay.responsiveLayer.models.CardDetails
import com.project.creditPay.responsiveLayer.models.Users
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow

class UserViewModel(
    private val shared: SharedPreferences,
) : ViewModel() {

    private val user = MutableStateFlow(ArrayList<Users>())
    private val newArray = MutableStateFlow(ArrayList<Users>())
    val users get() = newArray
    private val transaction = MutableStateFlow(ArrayList<DataClass>())
    val transactions get() = loaderOfText
    private val loaderOfText = MutableStateFlow(ArrayList<DataClass>())

    private val firebase = Firebase.firestore


    private val profilePick = MutableStateFlow<String?>(null)
    val pickLoader = profilePick
    private val name = MutableStateFlow(shared.getString("name", ""))
    val nameLoader = name
    private val loader = MutableStateFlow(LoadersResults())
    val _loader = loader
    private val cards = MutableStateFlow<ArrayList<CardDetails>>(arrayListOf())
    val getMyCards = cards
    private val stateOf = MutableStateFlow(false)
    val statePoint get() = stateOf
    private val stateOfTrans = MutableStateFlow(false)
    val stateOfTransGetter get() = stateOfTrans
    private val successText = MutableStateFlow("")
    val successTextPoint = successText

    init {
        val id = shared.getString("id", "")
        firebase.collection("userOfBank").addSnapshotListener { value, _ ->
            value?.toObjects(Users::class.java)?.let {
                Log.i("TestPointer", "$it")
                it.forEach { users1 ->
                    if (users1 != null) {
                        if (users1.id != id) {
                            user.value.add(users1)
                        }
                    }
                }
            }
        }
        getMyCards()
        transaction()
    }

    fun addCard(
        cardName: String,
        cardNumber: String,
        expiry: String,
        cvv: String,
        sessionTime: String,
    ) {
        loader.value = LoadersResults(condition = true)

        viewModelScope.async {
            firebase.collection("CollectionOfCards").where(
                Filter.equalTo("cardNumber", cardNumber)
            ).where(
                Filter.equalTo("userId", shared.getString("id", ""))
            ).get().addOnSuccessListener {
                if (it.documents.isEmpty()) {
                    firebase.collection("CollectionOfCards").add(
                        hashMapOf(
                            "cardName" to cardName,
                            "cardNumber" to cardNumber,
                            "expiry" to expiry,
                            "cvv" to cvv,
                            "sessionTime" to sessionTime,
                            "userId" to shared.getString("id", ""),
                            "balance" to "0"
                        )
                    ).addOnSuccessListener {
                        loader.value = LoadersResults("Success", false)
                    }.addOnFailureListener {
                        loader.value = LoadersResults("${it.message}", false)
                    }
                } else {
                    loader.value = LoadersResults(
                        "You already added this Card",
                        true
                    )

                }
            }.addOnFailureListener {
                loader.value = LoadersResults("${it.message}", false)


            }
        }.start()


    }

    private fun getMyCards() {
        viewModelScope.async {
            firebase.collection("CollectionOfCards").where(
                Filter.equalTo("userId", shared.getString("id", ""))
            ).addSnapshotListener { value, error ->
                value?.toObjects(CardDetails::class.java)?.let {
                    cards.value = it as ArrayList
                }
            }

        }.start()
    }

    fun updateAmount(docId: String, amount: String, currentBalance: Int) {
        loader.value = LoadersResults(condition = true)
        viewModelScope.async {
            firebase.collection("CollectionOfCards").document(docId).update(
                "balanceOf", currentBalance + amount.toInt()
            ).addOnSuccessListener {
                loader.value = LoadersResults("Updated", condition = false)
            }.addOnFailureListener {
                loader.value = LoadersResults("${it.message}", condition = false)
            }
            firebase.collection(
                "TransactionsTable"
            ).add(
                hashMapOf(
                    "transType" to true,
                    "transactionName" to "Self",
                    "time" to "${System.currentTimeMillis()}",
                    "state" to "Completed",
                    "amount" to amount,
                    "to" to shared.getString("id", "").toString()
                )
            ).addOnSuccessListener {
                loader.value = LoadersResults("Transaction Updated", condition = false)

            }


        }.start()

    }


    fun transaction() {
        try {
            val first = firebase.collection("TransactionsTable").where(
                Filter.equalTo("from", "${shared.getString("id", "")}")
            )
            val second = firebase.collection("TransactionsTable")
                .where(
                    Filter.equalTo(
                        "to", "${shared.getString("id", "")}"
                    )
                )

            first.addSnapshotListener { value, _ ->
                value?.toObjects(DataClass::class.java)?.let {
                    it.forEach { its ->
                        if (its != null) {
                            val trans =
                                transaction.value.find { it.transactionID == its.transactionID }
                            if (trans == null) {
                                transaction.value.add(its)
                            }
                        }
                    }
                }
            }
            second.addSnapshotListener { value, _ ->
                value?.toObjects(DataClass::class.java)?.let {
                    it.forEach { its ->
                        if (its != null) {
                            val trans =
                                transaction.value.find { it.transactionID == its.transactionID }
                            if (trans == null) {
                                transaction.value.add(its)
                            }
                        }
                    }
                }
            }


        } catch (e: Exception) {
            Log.i("TEstBug", "${e.message}")
        }

    }

    fun searchMe(string: String) {
        val array = arrayListOf<Users>()
        user.value.forEach {
            if (it.name.toString().contains(string)) {
                array.add(it)
            } else if (it.mobile.toString().contains(string)) {
                array.add(it)
            }
        }
        newArray.value = array
    }

    fun transferMoney(money: CardDetails?, users: Users, amount: Int?) {
        firebase.collection("CollectionOfCards").where(
            Filter.equalTo(
                "userId", "${users.id}"
            )
        ).get().addOnSuccessListener {
            if (it.documents.isEmpty()) {
                loader.value = LoadersResults("No Accounts", false)
            } else {
                it.documents[0].toObject(CardDetails::class.java)?.let {
                    val updating = it.balanceOf!! + amount!!
                    firebase.collection("CollectionOfCards").document(
                        it.id.toString()
                    ).update("balanceOf", updating).addOnSuccessListener {
                        val minus = money?.balanceOf!! - amount
                        firebase.collection("CollectionOfCards").document(
                            money.id.toString()
                        ).update("balanceOf", minus).addOnSuccessListener {
                            firebase.collection("TransactionsTable").add(
                                hashMapOf(
                                    "transType" to false,
                                    "transaction Name" to "Online",
                                    "time" to "${System.currentTimeMillis()}",
                                    "state" to "Completed",
                                    "amount" to "$amount",
                                    "from" to shared.getString(
                                        "id",
                                        ""
                                    ).toString(),
                                    "to" to users.id.toString()
                                )
                            ).addOnSuccessListener {
                                loader.value = LoadersResults(
                                    "Transaction Success",
                                    false
                                )
                            }
                        }
                    }

                }
            }
        }

        /*
          */
    }

    fun finMe(name: String) {
        val array = ArrayList<DataClass>()
        when (name) {
            "Debit" -> {
                transaction.value.forEach {
                    if (it.to != shared.getString("id", "")) {
                        array.add(it)
                    }
                }
            }

            "Credit" -> {
                transaction.value.forEach {
                    if (it.to == shared.getString("id", "")) {
                        array.add(it)
                    }
                }
            }

            else -> {
                array.addAll(transaction.value)
            }
        }

        loaderOfText.value = array

    }

    val retrofit = RetrofitPoint.api
    fun detect(
        income: String,
        customerAge: String,
        housingStatus: String,
        phoneMobileValid: String,
        bankMonthsCount: String,
        keepAliveSession: String,
        result:(String)->Unit
    ) {
        loader.value = LoadersResults(message = null, condition = true)
        viewModelScope.async {
            async {
                try {
                    retrofit.checkDetails(
                        income = income,
                        customer_age = customerAge,
                        housing_status = housingStatus,
                        phone_mobile_valid = phoneMobileValid,
                        bank_months_count = bankMonthsCount,
                        keep_alive_session = keepAliveSession
                    )
                } catch (e: Exception) {
                    Log.i("kdjfk", "${e.message}")
                    null
                }


            }.await().let {
                it?.body()?.Predicted?.let {

                    if(it=="1"){
                        result("Fraud Transaction")
                    }else if(it=="0"){
                        result("Not a Fraud Transaction")
                    }
                    loader.value = LoadersResults(message = null, condition = false)
                    return@async
                }

                LoadersResults(message = "", condition = false)

            }
        }.start()
    }


    companion object {
        fun getSharedViewModel(
            shared: SharedPreferences,
        ) = object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle,
            ): T {
                return UserViewModel(
                    shared
                ) as T
            }
        }
    }
}