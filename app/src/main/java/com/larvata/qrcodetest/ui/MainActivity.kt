package com.larvata.qrcodetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.larvata.qrcodetest.R
import com.larvata.qrcodetest.databinding.ActivityMainBinding
import com.larvata.qrcodetest.ui.util.RequestPermission
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    fun checkPermission(){
        if (!RequestPermission.checkPermissions(this)) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_need_permission),
                RequestPermission.requestPermission,
                *RequestPermission.permissions
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, getString(R.string.toast_permission_done), Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).
            setRationale(resources.getString(R.string.dialog_permission_redirect_to_setting)).
            setPositiveButton(resources.getString(R.string.dialog_permission_redirect_to_setting_positive)).
            setNegativeButton(resources.getString(R.string.dialog_permission_redirect_to_setting_negative)).
            build().
            show()
        }
    }
}