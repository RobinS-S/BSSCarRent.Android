package com.bss.carrent.ui.car

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bss.carrent.R
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarOwnListFragmentTest : TestCase(){

    private lateinit var scenario: FragmentScenario<CarOwnListFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_BSSCarRent)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testCarList() {
        Espresso.onView(ViewMatchers.withId(R.id.car_list_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}