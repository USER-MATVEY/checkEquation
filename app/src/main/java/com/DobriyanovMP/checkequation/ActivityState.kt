package com.DobriyanovMP.calculateEquasion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ActivityState(
    var allEquationsCount: Int,
    var correctEquationsCount : Int,
    var wrongEquationsCount: Int,
    var lastFirstOperand: Int,
    var lastSecondOperand: Int,
    var lastOperation: String,
    var startButtonState: Boolean,
    var equationColor: Int
): Parcelable