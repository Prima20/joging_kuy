package com.papb.prima.jogingkuy

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mindorks.paracamera.Camera
import kotlinx.android.synthetic.main.activity_insert_profile.*

class InsertProfileActivity: AppCompatActivity(){

    private lateinit var camera: Camera
    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_profile)

        camera = Camera.Builder()
                .resetToCorrectOrientation(true)//1
                .setTakePhotoRequestCode(Camera.REQUEST_TAKE_PHOTO)//2
                .setDirectory("pics")//3
                .setName("delicious_${System.currentTimeMillis()}")//4
                .setImageFormat(Camera.IMAGE_JPEG)//5
                .setCompression(75)//6
                .build(this)

    }

    fun takePicture(view: View){
        if(!hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !hasPermission(android.Manifest.permission.CAMERA)){

            requestPermissions()
        }else{
            try {
                camera.takePicture()
            }catch (e: Exception){
                Toast.makeText(this.applicationContext,
                        getString(R.string.error_taking_picture),
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(this,
                permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(applicationContext,R.string.permission_message,Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this@InsertProfileActivity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA),PERMISSION_REQUEST_CODE)
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA),PERMISSION_REQUEST_CODE)
            return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        camera.takePicture()
                    } catch (e: Exception) {
                        Toast.makeText(this.applicationContext, getString(R.string.error_taking_picture),
                                Toast.LENGTH_SHORT).show()
                    }
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
                val bitmap = camera.cameraBitmap
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                    //detectDeliciousFoodOnDevice(bitmap)
                } else {
                    Toast.makeText(this.applicationContext, getString(R.string.picture_not_taken),
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        camera.deleteImage()
    }
}