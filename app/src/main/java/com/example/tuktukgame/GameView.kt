package com.example.tuktukgame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c : Context, var gameTask :GameTask ):View(c) {

    // Declaring variables
    private var myPaint:Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var tukPosition = 1
    private val otherVehicles = ArrayList<HashMap<String, Any>>()
    private var highScore = 0

    var viewWidth = 0
    var viewHeight = 0
    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        //Setting the speed
        if(time % 700 < 10 +speed){
            val map = HashMap<String,Any>()
            map ["lane"] = (0..3).random()
            map["startTime"] = time
            otherVehicles.add(map)
        }
        time = time + 10 + speed
        val carWidth = viewWidth / 5
        val carHeight = carWidth + 10
        myPaint!!.style = Paint.Style.FILL

        //user vehicle tuk tuk
        val d = resources.getDrawable(R.drawable.user_tuktuk, null)

        d.setBounds(
            tukPosition * viewWidth / 4 + viewWidth / 15 + 25,
            viewHeight - 2 - carHeight,
            tukPosition * viewWidth / 4 + viewWidth / 15 + carWidth - 25 ,
            viewHeight - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN

        for(i in otherVehicles.indices){
            try{
                val carX = otherVehicles[i]["lane"] as Int * viewWidth / 4 + viewWidth / 50
                val carY = time - otherVehicles[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.red_car, null)

                d2.setBounds(
                    carX + 10, carY - carHeight, carX + carWidth - 10, carY
                )
                d2.draw(canvas!!)
                if(otherVehicles[i]["lane"] as Int == tukPosition){
                    if(carY > viewHeight - 2 - carHeight && carY < viewHeight - 2 ){

                        gameTask.closeGame(score)
                    }
                }
                if(carY > viewHeight + carHeight){
                    otherVehicles.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if(score > highScore){
                        highScore = score
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

        //Positioning score
        myPaint!!.color = Color.BLACK
        myPaint!!.textSize = 50f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        canvas.drawText("Highest Score : $highScore", 680f, 80f, myPaint!!)
        invalidate()
    }

    //onTouchEvent function
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1 < viewWidth / 2){
                    if(tukPosition > 0){
                        tukPosition--
                    }
                }

                if(x1 > viewWidth/2){
                    if(tukPosition < 2){
                        tukPosition++
                    }
                }
                invalidate()
            }

            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }

    //Reset game
    fun resetGame() {
        score = 0
        speed = 1
        time = 0
        tukPosition = 0
        otherVehicles.clear()
        invalidate()
    }
}
