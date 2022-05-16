package com.example.vogoworks.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.vogoworks.common.progressdialog.IProgressDialog
import com.example.vogoworks.common.progressdialog.ProgressDialog

open class BaseFragment : Fragment(), IProgressDialog {
    private var progressDialog: ProgressDialog? = null
    protected var callBack: OnBackPressedCallback? = null
    protected var backPressedTime: Long = 0


    override fun showProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = ProgressDialog(requireContext(), "")
        progressDialog?.show()
    }

    override fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        callBack?.remove()
        super.onDetach()
    }

}
