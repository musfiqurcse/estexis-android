package com.travelhugai.travelplanner.util

import android.content.Context
import android.widget.Toast
import com.extexis.core.android.util.UiText


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(uiText: UiText) {
    Toast.makeText(this, uiText.asString(this), Toast.LENGTH_SHORT).show()
}
