package com.basesimpledialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.WindowManager

/**
 * Created by xiaoqing.zhou
 * on  2019/4/2
 */
class SimpleDialog {

    protected var mBuilder: Builder? = null

    constructor(builder: Builder) {
        mBuilder = builder
        mBuilder?.dialog = initDialog(builder)
    }

    fun show() {
        if (null != mBuilder && null != mBuilder?.dialog) {
            mBuilder?.dialog?.show()
        }
    }

    fun hide() {
        if (null != mBuilder && null != mBuilder?.dialog) {
            mBuilder?.dialog?.dismiss()
        }
    }

    private fun initDialog(builder: Builder): AlertDialog {
        val dialog = AlertDialog.Builder(builder.context).create().apply {
            setCanceledOnTouchOutside(builder.canCancelInOut)
            setTitle(builder.title)
            setButton(-1,builder.positiveText) { dialog, which ->
                if (null!=builder.positiveListener){
                    builder.positiveListener?.positiveClick(builder.dialog)
                }
            }
            setButton(-2,builder.negativeText){dialog, which ->
                if (null!=builder.negativeListener){
                    builder.negativeListener?.negativeClick(builder.dialog)
                }
            }
        }
        val window = dialog.window.apply {
            setWindowAnimations(builder.style)
            setGravity(
                when (builder.gravity) {
                    DIYGravity.BOTTOM -> Gravity.BOTTOM
                    DIYGravity.TOP -> Gravity.TOP
                    DIYGravity.LEFT -> Gravity.LEFT
                    DIYGravity.RIGHT -> Gravity.RIGHT
                    else -> Gravity.CENTER
                }
            )
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        val attr = window.attributes.apply {
            width = (builder.context.resources.displayMetrics.widthPixels * 0.9).toInt()
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        window.attributes = attr
        return dialog

    }

    class Builder {
        var context: Context
        lateinit var dialog: AlertDialog
        var title: String = ""
        var style: Int = R.style.Ani_Fade2Fade
        var gravity = DIYGravity.CENTER
        var percentage = 0.8
        var canCancelInOut = true
        var positiveText = "确认"
        var positiveListener: PositiveListener? = null
        var negativeText = "取消"
        var negativeListener:NegativeListener?=null

        constructor(context: Context) {
            this.context = context
        }

        /**
         * set widnow title
         */
        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        /**
         * set window animation default is fadein 2 fade out
         */
        fun setStyle(style: Int): Builder {
            this.style = style
            return this
        }

        /**
         * set  window gravity default is center
         */
        fun setGravity(gravity: DIYGravity): Builder {
            this.gravity = gravity
            return this
        }

        /**
         * set window width by percentage,like 0.5 is half width and 1.0 is full width
         */
        fun setWidthPercentage(percentage: Double): Builder {
            this.percentage = percentage
            return this
        }

        /**
         * set positive text default os 确定
         */
        fun setPositiveText(text:String):Builder{
            this.positiveText=text
            return this
        }

        /**
         * set positive listener
         */
        fun setPositiveListener(positiveListener: PositiveListener): Builder {
            this.positiveListener = positiveListener
            return this
        }

        /**
         * set negative text,default is 取消
         */
        fun setNegativeText(text:String):Builder{
            this.negativeText=text
            return this
        }

        /**
         * set negative listener
         */
        fun setNegativeListener(negativeListener: NegativeListener): Builder {
            this.negativeListener = negativeListener
            return this
        }

        fun build(): SimpleDialog {
            return SimpleDialog(this)
        }
    }
}