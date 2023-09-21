package one.two.three.four.fairgo5.logic

import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.allViews
import kotlinx.coroutines.delay
import one.two.three.four.fairgo5.R
import one.two.three.four.fairgo5.fragments.FirstGamePuzzle
import one.two.three.four.fairgo5.fragments.FirstGamePuzzle.Companion.POKIES_IMAGES
import one.two.three.four.fairgo5.fragments.GamePuzzle

suspend fun FirstGamePuzzle.spin() {
    // 1
    val bank = valuesMap["bank"]!!.value
    val bet = valuesMap["bet"]!!.value
    if(!checkIfBankEnough(bank, bet)) return
    var pokies = view?.allViews?.filter {
        it is ImageView && it.parent is LinearLayout
    }?.map {
        it as ImageView
    }?.toList()
    if(pokies != null) {
        pokies = getOrderedPokies(pokies)
        repeat(20) {
            mixPokies(pokies)
            delay(200)
        }
        val columns = takeColumns(pokies)
        val stringBuilder = StringBuilder("Columns\n")
        for(column in columns) {
            for(c in column) {
                stringBuilder.append("${c.tag}, ")
            }
            stringBuilder.appendLine()
        }
        Log.i("Spin logic", stringBuilder.toString())
        var cash = 0
        for(pokyImage in POKIES_IMAGES) {
            var contains = false
            for(column in columns) {
                if(column.any {
                    it.tag == pokyImage
                    }) {

                    Log.i("Spin logic", "Contains $pokyImage")
                    contains = true
                }
                else {
                    contains = false
                    break
                }
            }
            if(contains) {
                cash += cashFromPoky(pokyImage)
            }
        }
        cash *= bet / 10
        valuesMap["win"]!!.value = cash
        valuesMap["bank"]!!.value += cash
    }
    else {
        Log.i("Spin", "Pokies is null...")
    }
    view?.findViewById<Button>(R.id.spin_clicker)?.isEnabled = true
}

fun FirstGamePuzzle.mixPokies(pokies: List<ImageView>) {
    val topToCenter = listOf(
        pokies[5] to pokies[10],
        pokies[6] to pokies[11],
        pokies[7] to pokies[12],
        pokies[8] to pokies[13],
        pokies[9] to pokies[14],
    )
    val centerToBottom = listOf(
        pokies[0] to pokies[5],
        pokies[1] to pokies[6],
        pokies[2] to pokies[7],
        pokies[3] to pokies[8],
        pokies[4] to pokies[9],
    )

    for(ttc in topToCenter) {
        ttc.second.tag = ttc.first.tag
        if(ttc.second.tag != null) {
            ttc.second.setImageResource(ttc.second.tag as Int)
        }
    }
    for(ttc in centerToBottom) {
        ttc.second.tag = ttc.first.tag
        if(ttc.second.tag != null) {
            ttc.second.setImageResource(ttc.second.tag as Int)
        }
    }
    for(poky in pokies.take(5)) {
        val tag = getRandomPoky()
        poky.tag = tag
        poky.setImageResource(tag)
    }
}

fun FirstGamePuzzle.getRandomPoky() = POKIES_IMAGES.random()

fun FirstGamePuzzle.getOrderedPokies(pokies: List<ImageView>): List<ImageView> {
    val pokiesList = mutableListOf<ImageView>()
    for(i in 0..<pokies.size / 5) {
        pokiesList.add(pokies[i])
        pokiesList.add(pokies[i+3])
        pokiesList.add(pokies[i+6])
        pokiesList.add(pokies[i+9])
        pokiesList.add(pokies[i+12])
    }
    return pokiesList
}

fun FirstGamePuzzle.takeColumns(pokies: List<ImageView>): List<List<ImageView>> {
    val resultList = mutableListOf<List<ImageView>>()
    for(i in 0..<pokies.size / 3) {
        resultList.add(listOf(pokies[i], pokies[i+5], pokies[i+10]))
    }
    return resultList
}

fun FirstGamePuzzle.cashFromPoky(poky: Int) = when(poky) {
    R.drawable.poky_01 -> 10
    R.drawable.poky_02 -> 25
    R.drawable.poky_03 -> 50
    R.drawable.poky_04 -> 75
    R.drawable.poky_05 -> 100
    R.drawable.poky_06 -> 200
    R.drawable.poky_07 -> 500
    R.drawable.poky_08 -> 750
    R.drawable.poky_09 -> 1000
    else -> 0
}

fun GamePuzzle.checkIfBankEnough(bank: Int, bet: Int): Boolean {
    if(bank < bet) {
        view?.findViewById<Button>(R.id.spin_clicker)?.isEnabled = true
        return false
    }
    valuesMap["bank"]!!.value -= bet
    return true
}