package com.example.newsapp.shared.extensions

import android.app.Activity
import com.example.newsapp.R

fun Activity?.applyAnimations(
    type: ActivityAnimationType
) {
    this ?: return

    var enterAnimation = 0
    var exitAnimation = 0

    when (type) {
        ActivityAnimationType.PUSH_OPEN -> {
            enterAnimation = R.anim.activity_push_enter
            exitAnimation = R.anim.activity_push_exit
        }
        ActivityAnimationType.PUSH_CLOSE -> exitAnimation = R.anim.activity_push_exit_pop
    }

    overridePendingTransition(enterAnimation, exitAnimation)
}


enum class ActivityAnimationType {

    PUSH_OPEN,
    PUSH_CLOSE

}
