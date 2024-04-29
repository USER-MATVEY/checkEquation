package com.DobriyanovMP.checkequation

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
    var lastAnswer: Int,
    var lastTrueAnswer: Int,
    var startButtonState: Boolean,
    var equationColor: Int,
    var correctButtonState: Boolean,
    var wrongButtonState: Boolean,
    var nextButtonState: Boolean
): Parcelable