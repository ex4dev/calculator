package dev.ex4.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.math.BigDecimal
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var text: TextView
    var operationType: Char? = null;
    var justSolved = true

    var additionChar by Delegates.notNull<Char>()
    var subtractionChar by Delegates.notNull<Char>()
    var negativeChar = '-' // Do not change this
    var multiplicationChar by Delegates.notNull<Char>()
    var divisionChar by Delegates.notNull<Char>()

    // Fix imprecise floating-point math
    operator fun BigDecimal.plus(a: BigDecimal) = this.add(a)
    operator fun BigDecimal.minus(a: BigDecimal) = this.subtract(a)
    operator fun BigDecimal.times(a: BigDecimal) = this.multiply(a)
    operator fun BigDecimal.div(a: BigDecimal) = this.divide(a)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById<TextView>(R.id.equationText)

        additionChar = resources.getString(R.string.add)[0]
        subtractionChar = resources.getString(R.string.subtract)[0]
        multiplicationChar = resources.getString(R.string.multiply)[0]
        divisionChar = resources.getString(R.string.divide)[0]
    }


    ////////////////////
    // CALCULATOR FUNCTIONS
    ////////////////////
    fun clear(view: View) {
        text.text = "0"
        operationType = null
        justSolved = true
    }
    fun negative(view: View) {
        addToText(negativeChar)
    }
    fun add(view: View) {
        setOperation(additionChar)
    }

    fun subtract(view: View) {
        setOperation(subtractionChar)
    }

    fun multiply(view: View) {
        setOperation(multiplicationChar)
    }

    fun divide(view: View) {
        setOperation(divisionChar)
    }

    fun one(view: View) {
        addToText('1')
    }

    fun two(view: View) {
        addToText('2')
    }

    fun three(view: View) {
        addToText('3')
    }

    fun four(view: View) {
        addToText('4')
    }

    fun five(view: View) {
        addToText('5')
    }

    fun six(view: View) {
        addToText('6')
    }

    fun seven(view: View) {
        addToText('7')
    }

    fun eight(view: View) {
        addToText('8')
    }

    fun nine(view: View) {
        addToText('9')
    }

    fun zero(view: View) {
        addToText('0')
    }

    fun decimal(view: View) {
        addToText('.')
    }

    fun solve(view: View) {
        try {
            solve()
        } catch (e: Exception) {
            val toast = Toast.makeText(view.context, "Error solving: ${e.message}", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

    }

    private fun solve() {
        if (operationType == null) throw NumberFormatException("No operation selected.")
        var solution: BigDecimal?
        val split = text.text.split("$operationType")
        if (split.size < 2 || split[1].isEmpty()) throw java.lang.NumberFormatException("Invalid equation.")
        val firstNum = split[0].toBigDecimal()
        val secondNum = split[1].toBigDecimal()
        when (operationType) {
            additionChar -> {
                solution = firstNum + secondNum
            }
            subtractionChar -> {
                solution = firstNum - secondNum
            }
            multiplicationChar -> {
                solution = firstNum * secondNum
            }
            divisionChar -> {
                solution = firstNum / secondNum
            } else -> {
                solution = null
            }
        }
        text.text = "$solution"
        justSolved = true
    }

    fun del(view: View) {
        val textSeq = text.text
        if (textSeq.isNotEmpty())
        text.text = (textSeq.toString().substring(0, textSeq.length - 1))
    }

    private fun addToText(number: Char) {
        if (justSolved && (number != additionChar && number != subtractionChar && number != multiplicationChar && number != divisionChar)) {
            text.text = ""
        }
        justSolved = false
        /* if (text.text.length > 10) { // Character limit
            val toast = Toast.makeText(applicationContext, "You have reached the character limit!", Toast.LENGTH_SHORT);
            toast.show()
            return
        } */
        text.append(number.toString())
    }

    fun setOperation(operationType: Char) {
        try {
            // If there is already an equation, solve that one first
            for (char in text.text) {
                if (char == additionChar || char == subtractionChar || char == multiplicationChar || char == divisionChar) {
                    solve()
                }
            }
            this.operationType = operationType
            addToText(operationType)
        } catch (e: Exception) {
            // The previous equation failed to solve
            // That means it was not a full equation
            // Just replace the old operation sign with the new one
            this.operationType = operationType;
            val textSeq = text.text
            text.text = (textSeq.toString().substring(0, textSeq.length - 1) + operationType)
            return
        }
    }
}