package com.example.kongsikereta.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.kongsikereta.data.Rides
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

    suspend fun registerUser(
        uri: Uri,
        driver: UserDriver,
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        val imageDownloadUri = FirebaseStorageUtil.uploadImage(uri, driver.ic, context)
        val userDataDownloadUri = FirebaseStorageUtil.uploadUserData(
            JsonHelper.createJsonFile(
                context,
                driver.ic,
                driver
            ),
            driver.ic,
        )

        Log.i("downloadUri", userDataDownloadUri.toString())

        if (imageDownloadUri != null && userDataDownloadUri != null) {
            val driverData = hashMapOf(
                "ic" to driver.ic,
                "password" to driver.password,
                "profilePicUrl" to imageDownloadUri,
                "userDataUrl" to userDataDownloadUri,
            )
            Log.i("driverData", driverData.toString())
            driversCollection.document(driver.ic).set(driverData).addOnSuccessListener {
                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                callback(true)
            }
        }
    }

    suspend fun signIn(
        userId: String,
        password: String,
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        var isSuccess = false
        val userDocRef = driversCollection.document(userId)
        var userDoc: UserDriverInfo? = null
        userDocRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val user = document.toObject(UserDriverInfo::class.java)
                if (password == document.getString("password")) {
                    Log.i("currentUserRepository", user.toString())
                    userDoc = user
                    isSuccess = true
                } else {
                    isSuccess = false
                }
            } else {
                isSuccess = false
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Incorrect Credentials", Toast.LENGTH_LONG).show()
            isSuccess = false
            it.printStackTrace()
        }.await()
        if(userDoc != null){
            parseUserData(userDoc!!, context)
            callback(isSuccess)
        }
    }

    suspend fun createRide(rides: Rides, context: Context, callback: (Boolean) -> Unit) {
        val rideInfo = hashMapOf(
            "date" to rides.date,
            "time" to rides.time,
            "origin" to rides.origin,
            "destination" to rides.destination,
            "fare" to rides.fare,
            "userId" to rides.userId
        )
        rideCollection.document().set(rideInfo).addOnSuccessListener {
            Toast.makeText(context, "Successfully Created Ride", Toast.LENGTH_LONG).show()
            callback(true)
        }.addOnFailureListener {
            it.printStackTrace()
            callback(false)
        }.await()
    }

    suspend fun fetchRides(userId: String?): List<Rides> {
        return if (userId == null) {
            val snapshot = rideCollection.get().await()
            snapshot.toObjects(Rides::class.java)
        } else {
            val snapshot = rideCollection.whereEqualTo("userId", userId).get().await()
            snapshot.toObjects(Rides::class.java)
        }
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

        Log.i("parsedUserData", currentUserDriver.toString())
    }
}