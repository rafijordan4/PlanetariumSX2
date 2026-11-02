package com.rafi.planetariumsx2

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.RectF
import android.view.View
import android.view.MotionEvent
import android.widget.Toast
import android.graphics.Path

class MainActivity : Activity() {
    private lateinit var screen: PlanetariumView
    private val pickGame = 4102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        screen = PlanetariumView { openGamePicker() }
        setContentView(screen)
    }

    private fun openGamePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/octet-stream", "application/x-cue"))
        }
        startActivityForResult(intent, pickGame)
    }

    @Deprecated("Deprecated in Android API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickGame && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                val name = uri.lastPathSegment?.substringAfterLast('/') ?: "File game dipilih"
                Toast.makeText(this, "Dipilih: $name\nIni baru pemilih file; belum menjalankan emulator.", Toast.LENGTH_LONG).show()
                screen.selected = name
                screen.invalidate()
            }
        }
    }
}

private class PlanetariumView(val onChoose: () -> Unit) : View(null) {
    private val p = Paint(Paint.ANTI_ALIAS_FLAG)
    var selected = "Belum ada game dipilih"
    private val cyan = Color.rgb(73, 217, 232)
    private val gold = Color.rgb(243, 215, 122)
    private val white = Color.rgb(235, 244, 250)
    private val dark = Color.rgb(5, 13, 25)

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val w = width.toFloat(); val h = height.toFloat()
        c.drawColor(dark)
        // Star field and planetarium dome
        p.color = Color.rgb(15, 35, 57); c.drawCircle(w*.72f, h*.43f, h*.48f, p)
        p.color = Color.rgb(23, 63, 82); c.drawCircle(w*.72f, h*.43f, h*.37f, p)
        p.color = Color.rgb(5, 13, 25); c.drawCircle(w*.72f, h*.43f, h*.31f, p)
        p.color = cyan; p.style = Paint.Style.STROKE; p.strokeWidth = 3f
        c.drawCircle(w*.72f, h*.43f, h*.38f, p); p.style = Paint.Style.FILL
        for (i in 0..65) {
            val x = ((i * 7919) % 997) / 997f * w
            val y = ((i * 3571) % 991) / 991f * h
            p.color = if (i % 7 == 0) gold else Color.WHITE
            c.drawCircle(x, y, if (i % 7 == 0) 2.3f else 1.1f, p)
        }
        text(c, "PLANETARIUM SX2", w*.055f, h*.17f, h*.075f, gold, true)
        text(c, "PS2 GAME COMPANION", w*.058f, h*.23f, h*.027f, cyan, false)
        text(c, selected.take(34), w*.058f, h*.34f, h*.032f, white, false)
        button(c, RectF(w*.055f,h*.43f,w*.30f,h*.57f), "PILIH GAME", cyan)
        text(c, "BIN / CUE / ISO", w*.065f, h*.62f, h*.023f, white, false)
        text(c, "Prototype UI • belum menjalankan game", w*.055f, h*.91f, h*.021f, Color.LTGRAY, false)
        // Decorative controller cluster
        button(c, RectF(w*.73f,h*.72f,w*.82f,h*.86f), "△", cyan)
        button(c, RectF(w*.84f,h*.78f,w*.93f,h*.92f), "○", gold)
        button(c, RectF(w*.62f,h*.78f,w*.71f,h*.92f), "□", cyan)
        button(c, RectF(w*.73f,h*.88f,w*.82f,h*.99f), "×", gold)
        button(c, RectF(w*.39f,h*.78f,w*.49f,h*.91f), "L", cyan)
        button(c, RectF(w*.51f,h*.78f,w*.61f,h*.91f), "R", cyan)
        p.color = Color.argb(90, 73, 217, 232); p.style = Paint.Style.STROKE; p.strokeWidth = 3f
        c.drawCircle(w*.18f,h*.79f,h*.105f,p); p.style = Paint.Style.FILL
        text(c, "D-PAD", w*.14f,h*.80f,h*.025f,white,true)
    }

    private fun button(c: Canvas, r: RectF, label: String, color: Int) {
        p.color = Color.argb(55, 255, 255, 255); c.drawRoundRect(r, 18f,18f,p)
        p.color = color; p.style = Paint.Style.STROKE; p.strokeWidth = 2.5f
        c.drawRoundRect(r,18f,18f,p); p.style = Paint.Style.FILL
        text(c,label,r.centerX(),r.centerY()+r.height()*.12f,r.height()*.42f,white,true, true)
    }
    private fun text(c: Canvas, s: String, x: Float, y: Float, size: Float, color: Int, bold: Boolean, center: Boolean = false) {
        p.color=color; p.textSize=size; p.typeface=if(bold) Typeface.create("sans-serif",Typeface.BOLD) else Typeface.create("sans-serif",Typeface.NORMAL)
        p.textAlign=if(center) Paint.Align.CENTER else Paint.Align.LEFT
        c.drawText(s,x,y,p)
    }
    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_UP) {
            val r = RectF(width*.055f,height*.43f,width*.30f,height*.57f)
            if (r.contains(e.x,e.y)) onChoose()
            return true
        }
        return true
    }
}
