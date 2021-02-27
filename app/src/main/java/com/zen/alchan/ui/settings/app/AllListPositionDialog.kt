package com.zen.alchan.ui.settings.app

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zen.alchan.R
import kotlinx.android.synthetic.main.dialog_number_picker.view.*

class AllListPositionDialog : DialogFragment() {

    interface AllListPositionListener {
        fun passPosition(pos: Int)
    }

    private lateinit var listener: AllListPositionListener
    private var currentPos = 1
    private var maxPos = 1

    companion object {
        const val CURRENT_POSITION = "currentPosition"
        const val MAX_POSITION = "maxPosition"
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_number_picker, null)

        if (!this::listener.isInitialized) {
            dismiss()
        }

        currentPos = arguments?.getInt(CURRENT_POSITION, 1) ?: 1
        maxPos = arguments?.getInt(MAX_POSITION, 1) ?: 1

        val positionArray = 1..maxPos

        view.numberPicker.apply {
            minValue = 1
            maxValue = maxPos
            displayedValues = positionArray.map { it.toString() }.toTypedArray()
            wrapSelectorWheel = false
            value = currentPos
        }

        builder.setView(view)
        builder.setPositiveButton(R.string.set) { _, _ ->
            listener.passPosition(view.numberPicker.value)
        }
        builder.setNegativeButton(R.string.cancel, null)
        return builder.create()
    }

    fun setListener(allListPositionListener: AllListPositionListener) {
        listener = allListPositionListener
    }
}