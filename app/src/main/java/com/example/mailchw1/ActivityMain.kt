package com.example.mailchw1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityMain() : AppCompatActivity(), NumListFragment.IListener {
    companion object {

        const val TAG_DETAILS = "DETAILS"
        const val TAG_NUMBERS_LIST = "NUM_LIST"
        const val NUM_ARRAY = "ARRAY"
        const val FIRST_ARR_VAL = 1
        const val LAST_ARR_VAL = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val numbers = arrayListOf<Int>()
            numbers.addAll(FIRST_ARR_VAL..LAST_ARR_VAL)
            val bundle = Bundle().apply {
                putIntegerArrayList(NUM_ARRAY, numbers)
            }

            val fragment = NumListFragment().apply {
                arguments = bundle
            }
            supportFragmentManager.beginTransaction().add(R.id.main_ll, fragment, TAG_NUMBERS_LIST)
                .commitAllowingStateLoss()
        }

    }

    private fun showDetails(number: Int) {
        val detailsFragment = NumDetailsFragment.newInstance(number)
        supportFragmentManager.beginTransaction().replace(R.id.main_ll, detailsFragment)
            .addToBackStack(
                TAG_DETAILS
            ).commitAllowingStateLoss()
    }

    override fun onNumClicked(num: Int) {
        showDetails(num)
    }
}