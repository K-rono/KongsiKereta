package com.example.kongsikereta.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.kongsikereta.data.UserDriver
import com.example.kongsikereta.data.UserDriverInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.io.File
import java.sql.Driver
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val driversCollection = firestore.collection("drivers")
    private val rideCollection = firestore.collection("rides")

    var currentUserDriver: HashMap<String, File?>? = null

    suspend fun registerUser(uri: Uri, driver: UserDriver, context: Context) {
        val imageDownloadUri = FirebaseStorageUtil.uploadImage(uri, driver.ic, context)
        val userDataDownloadUri = FirebaseStorageUtil.uploadUserData(
            JsonHelper.createJsonFile(
                context,
                driver.ic,
                driver
            ),
            driver.ic,
        )

        if (imageDownloadUri != null && userDataDownloadUri != null) {
            val driverData = hashMapOf(
                "ic" to driver.ic,
                "password" to driver.password,
                "profilePicUrl" to imageDownloadUri,
                "userDataUrl" to userDataDownloadUri,
            )

            driversCollection.document(driver.ic).set(driverData).addOnSuccessListener {
                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
            }
        }
    }

    suspend fun signIn(
        userId: String,
        password: String,
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        val userDocRef = driversCollection.document(userId)
        var userDoc: UserDriverInfo? = null
        userDocRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val user = document.toObject(UserDriverInfo::class.java)
                if (password == document.getString("password")) {
                    userDoc = user
                    callback(true)
                } else {
                    callback(false)
                }
            } else {
                callback(false)
            }
        }.addOnFailureListener {
            callback(false)
            it.printStackTrace()
        }.await()

    }

    private suspend fun parseUserData(
        userDoc: UserDriverInfo,
        context: Context
    ) {
        val profilePicFile =
            FirebaseStorageUtil.fetchUserData(userDoc.profilePicUrl, userDoc.ic, "png", context)
        val userDataFile =
            FirebaseStorageUtil.fetchUserData(userDoc.userDataUrl, userDoc.ic, "json", context)

        currentUserDriver = hashMapOf(
            "profilePic" to profilePicFile,
            "userData" to userDataFile
        )
    }
}