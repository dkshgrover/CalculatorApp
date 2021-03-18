package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var dotClicked: Boolean = false
    var numClicked: Boolean = false
    var firstMinus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.append((view as Button).text)
        numClicked = true
    }

    fun onClear(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        numClicked = false
        dotClicked = false
        firstMinus = false
    }

    fun onDot(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if(numClicked && !dotClicked){
            tvInput.append(".")
            dotClicked = true
        }
    }

    fun onOperator(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if((view as Button).text == "-" && !firstMinus){
            tvInput.append((view as Button).text)
            firstMinus = true
        }
        else if(numClicked && !checkOperator(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            numClicked = false
            dotClicked = false
        }
    }

    fun onEquals(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        var tvValue = tvInput.text.toString()
        var prefix: String = ""
        if(tvValue.startsWith("-") && tvValue.length == 1){
            onClear(view)
            return
        }else if(tvValue.startsWith("-") && tvValue.length != 1){
            prefix = "-"
            tvValue = tvValue.substring(1)
        }
        try{
            if(tvValue.contains("-")){
                val splitValue = tvValue.split('-')
                var num1 = splitValue[0]
                var num2 = splitValue[1]
                if(!prefix.isEmpty()){
                    num1 = (prefix+num1)
                    prefix = ""
                }
                tvInput.text = removeZeroes((num1.toDouble() - num2.toDouble()).toString())
            }else if(tvValue.contains("+")){
                val splitValue = tvValue.split('+')
                var num1 = splitValue[0]
                var num2 = splitValue[1]
                if(!prefix.isEmpty()){
                    num1 = (prefix+num1)
                    prefix = ""
                }
                tvInput.text = removeZeroes((num1.toDouble() + num2.toDouble()).toString())

            }else if(tvValue.contains("*")){
                val splitValue = tvValue.split('*')
                var num1 = splitValue[0]
                var num2 = splitValue[1]
                if(!prefix.isEmpty()){
                    num1 = (prefix+num1)
                    prefix = ""
                }
                tvInput.text = removeZeroes((num1.toDouble() * num2.toDouble()).toString())
            }else if(tvValue.contains("/")){
                val splitValue = tvValue.split('/')
                var num1 = splitValue[0]
                var num2 = splitValue[1]
                if(!prefix.isEmpty()){
                    num1 = (prefix+num1)
                    prefix = ""
                }
                tvInput.text = removeZeroes((num1.toDouble() / num2.toDouble()).toString())
            }
        }catch(e: ArithmeticException){
            e.printStackTrace()
        }
    }

    private fun checkOperator(value: String) : Boolean{
        if(value.startsWith("-")){
            return false
        }else{
            return value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroes(value: String) : String{
        var result = ""
        if(value.contains(".0")){
            result = value.substring(0,value.length-2)
        }else{
            result = value
        }
        return result
    }
}