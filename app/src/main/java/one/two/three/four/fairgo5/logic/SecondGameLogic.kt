package one.two.three.four.fairgo5.logic

import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.delay
import one.two.three.four.fairgo5.R
import one.two.three.four.fairgo5.fragments.SecondGamePuzzle

suspend fun SecondGamePuzzle.spin() {
    val bank = valuesMap["bank"]!!.value
    val bet = valuesMap["bet"]!!.value
    val rotation = {valuesMap["rotation"]!!}

    if(!checkIfBankEnough(bank, bet)) return

    val wheelBody = view?.findViewById<ImageView>(R.id.wheel_body)
    if(wheelBody != null) {
        wheelBody.tag = if(wheelBody.tag is String) (wheelBody.tag as String).toInt() else wheelBody.tag
        repeat(50 + SecondGamePuzzle.FOR_RANDOM.random()) {
            wheelBody.tag = if((wheelBody.tag as Int) < 11) wheelBody.tag as Int + 1 else 0
            rotation().value = getRotationById(wheelBody.tag as Int)
            delay(100)
        }
        valuesMap["win"]!!.value = getCashByTag(wheelBody.tag as Int) * valuesMap["bet"]!!.value / 10
        valuesMap["bank"]!!.value += valuesMap["win"]!!.value
    }
    view?.findViewById<Button>(R.id.spin_clicker)?.isEnabled = true
}

fun SecondGamePuzzle.getRotationById(id: Int) = id * 30
fun SecondGamePuzzle.getCashByTag(tag: Int) = when(tag) {
    0 -> 200
    1 -> 2000
    2 -> 300
    5 -> 800
    8 -> 50
    9 -> 100
    10 -> 500
    else -> 0
}