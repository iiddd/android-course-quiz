package com.iiddd.quiz.common.extension

import android.app.Activity
import android.content.Intent

object ActivityExtension {

    fun Activity.setUpIntent(activityClass: Class<*>) = Intent(this, activityClass)
}