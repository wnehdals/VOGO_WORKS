package com.example.vogoworks.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vogoworks.R
import com.example.vogoworks.common.appbar.BaseAppBar
import com.example.vogoworks.common.progressdialog.IProgressDialog
import com.example.vogoworks.common.progressdialog.ProgressDialog

open class ActivityBase : AppCompatActivity(), IProgressDialog {
    private var progressDialog: ProgressDialog? = null
    private var baseAppBar: BaseAppBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    fun setBaseAppBar(title: String = "") {
        if(supportActionBar == null)
            throw IllegalStateException("Can not found supportActionBar")

        baseAppBar = BaseAppBar(this, supportActionBar)
        baseAppBar?.setUpActionBar()
        setAppBarTitle(title)
    }
    fun setAppBarTitle(title: String) {
        baseAppBar?.setUpActionBar()
        if(!title.isNullOrEmpty()) {
            baseAppBar?.setTitle(title)
        }
    }
    fun setAppBarColor(color: String) {
        baseAppBar?.setBackgroundColor(color)
    }
    fun appBarLeftButtonClicked(callback: (View) -> Unit) {
        baseAppBar?.leftButtonClickListener = callback
    }
    fun appBarRightButtonClicked(callback: (View) -> Unit) {
        baseAppBar?.rightButtonClickListener = callback
    }
    fun setBackKey() {
        baseAppBar?.setLeftButtonDrawable(R.drawable.ic_chevron_left)
        appBarLeftButtonClicked {
            onBackPressed()
        }
    }

    fun showFailToastMessage(message: String = "") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = ProgressDialog(this, "")
        progressDialog?.show()
    }

    override fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}
