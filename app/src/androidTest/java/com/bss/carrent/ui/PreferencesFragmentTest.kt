package com.bss.carrent.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bss.carrent.R
import junit.framework.TestCase
import org.hamcrest.core.IsNot.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferencesFragmentTest : TestCase(){

    private lateinit var scenario: FragmentScenario<PreferencesFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_BSSCarRent)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testCarList() {
        onView(ViewMatchers.withId(R.id.switch_auto_theme)).perform(ViewActions.click())
        onView(withId(R.id.switch_dark_theme)).check(matches(not(isEnabled())));
    }
}