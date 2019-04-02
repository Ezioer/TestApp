package com.basesimpledialog

import android.content.Context
import android.support.v7.app.AlertDialog

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
        mBuilder?.dialog?.show()
    }

    fun hide() {
        mBuilder?.dialog?.dismiss()
    }

    private fun initDialog(builder: Builder): AlertDialog {
        val dialog = AlertDialog.Builder(builder.context).create()
        dialog.setTitle(builder.title)

        return dialog

    }

    class Builder {
        var context: Context
        lateinit var dialog: AlertDialog
        var title: String = ""

        constructor(context: Context) {
            this.context = context
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): SimpleDialog {
            return SimpleDialog(this)
        }
    }
}