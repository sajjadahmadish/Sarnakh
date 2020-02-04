package project.utils

import com.google.android.material.floatingactionbutton.FloatingActionButton

fun FloatingActionButton.rotateFab(rotate: Boolean): Boolean {
    animate().setDuration(200)
        .rotation(if (rotate) 135f else 0f)
    return rotate
}

