package com.karinaksenovskaya.myapplication.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.arkivanov.decompose.defaultComponentContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karinaksenovskaya.myapplication.WeatherApp
import com.karinaksenovskaya.myapplication.presentation.root.DefaultRootComponent
import com.karinaksenovskaya.myapplication.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity1", "Permission granted")
            } else {
                showPermissionExplanation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as WeatherApp).applicationComponent.inject(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkAndRequestPermission()

        setContent {
            RootContent(
                component = rootComponentFactory.create(
                    defaultComponentContext(),
                    fusedLocationClient
                )
            )
        }
    }

    private fun checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MainActivity1", "Permission already granted")
        } else {
            Log.d("MainActivity1", "Requesting permission")
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(this).setTitle("Permission Required")
            .setMessage("We need your location permission to provide accurate weather information.")
            .setPositiveButton("Try Again") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Log.d("MainActivity1", "Permission denied by user")
            }.show()
    }

}