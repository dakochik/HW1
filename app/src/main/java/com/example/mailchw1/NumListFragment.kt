package com.example.mailchw1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NumListFragment : Fragment() {
    companion object {
        const val NUM_ARRAY = "ARRAY"
        const val FIRST_ARR_VAL = 1
        const val LAST_ARR_VAL = 100
    }

    interface IListener {
        fun onNumClicked(num: Int)
    }

    protected var listener: IListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = requireActivity() as? IListener
    }

    var numbers = arrayListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sth = arguments?.getIntegerArrayList(NUM_ARRAY)
        val savedNums = savedInstanceState?.getIntegerArrayList(NUM_ARRAY)

        when {
            savedNums != null -> {
                numbers = savedNums; Log.e("SAVE_STATE", "got from bundle: " + numbers.size)
            }
            sth != null -> {
                numbers = sth
                Log.e("SAVE_STATE", "got from arguments: " + numbers.size)
            }
            else -> {
                numbers.addAll(FIRST_ARR_VAL..LAST_ARR_VAL); Log.e(
                    "SAVE_STATE",
                    "created: " + numbers.size
                )
            }
        }

        return inflater.inflate(R.layout.activity_with_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numAdapter = NumberAdapter(numbers, NumberClickHandler())

        view.findViewById<Button>(R.id.btn_add_num).setOnClickListener {
            numbers.add(if (numbers.isEmpty()) 1 else (numbers.last() + 1))
            numAdapter.notifyItemChanged(numbers.size - 1)
        }


        view.findViewById<RecyclerView>(R.id.first_fr_recycler).apply {
            layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.columns_num))
            adapter = numAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (numbers.isNotEmpty()) {
            Log.e("SAVE_STATE", "we saved it: " + numbers.size)
            outState.putIntegerArrayList(NUM_ARRAY, numbers)
        }
    }

    inner class NumberClickHandler : NumberViewHolder.IListener {
        override fun onNumClicked(pos: Int) {
            val num = numbers[pos]

            listener?.onNumClicked(num)
        }
    }
}