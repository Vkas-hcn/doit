package com.todo.tomato.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.todo.tomato.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.ad_dialog_loading)
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent))
    }

    fun showLoading() {
        if (!isShowing) {
            show()
        }
    }
    fun hideLoading() {
        if (isShowing) {
            dismiss()
        }
    }
}
