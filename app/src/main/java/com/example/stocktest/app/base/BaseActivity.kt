package com.example.stocktest.app.base

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity(@LayoutRes private val layoutId: Int) : DaggerAppCompatActivity(layoutId) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (
                v is EditText ||
                v is TextInputEditText
            ) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}