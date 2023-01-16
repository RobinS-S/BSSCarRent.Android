package com.bss.carrent.ui.car

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bss.carrent.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarListFragmentTest : TestCase(){

    private lateinit var scenario: FragmentScenario<CarListFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_BSSCarRent)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testCarList() {
        onView(withId(R.id.car_list_recycler_view)).check(matches(isDisplayed()))
    }
}