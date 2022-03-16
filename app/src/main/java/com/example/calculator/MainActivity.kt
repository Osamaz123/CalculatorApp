package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import soup.neumorphism.ShapeType
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView?=null
   private var tvOutput : TextView?=null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
tvOutput = findViewById(R.id.tvOutput)
    }


    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        tvOutput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0] //99
                    var two = splitValue[1] //1 --> //99-1 = 98
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvOutput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0] //99
                    var two = splitValue[1] //1 --> //99-1 = 98
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvOutput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0] //99
                    var two = splitValue[1] //1 --> //99-1 = 98
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvOutput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0] //99
                    var two = splitValue[1] //1 --> //99-1 = 98
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvOutput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains("0"))
            value = result.substring(0, result.length - 2)
        // 99.0
        // 0123
        return value
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput?.text.toString()))
            tvInput?.append((view as Button).text)
        lastNumeric = false
        lastDot = false
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")) {
            false
        }else{
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

}