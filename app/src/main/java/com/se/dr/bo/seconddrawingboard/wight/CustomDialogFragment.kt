package com.se.dr.bo.seconddrawingboard.wight

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.se.dr.bo.seconddrawingboard.R

class CustomDialogFragment : DialogFragment() {

    var positiveClickListener: (() -> Unit)? = null
    var negativeClickListener: (() -> Unit)? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_layout, container, false)

        view.findViewById<TextView>(R.id.tv_left).setOnClickListener {
            positiveClickListener?.invoke()
            dismiss()
        }

        view.findViewById<TextView>(R.id.tv_right).setOnClickListener {
            negativeClickListener?.invoke()
            dismiss()
        }

        return view
    }

    companion object {
        fun newInstance(message: String,left:String,right:String): CustomDialogFragment {
            val fragment = CustomDialogFragment()
            val args = Bundle()
            args.putString("message", message)
            args.putString("left", left)
            args.putString("right", right)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_tip).text = arguments?.getString("message")
        view.findViewById<TextView>(R.id.tv_left).text = arguments?.getString("left")
        view.findViewById<TextView>(R.id.tv_right).text = arguments?.getString("right")
    }
}
