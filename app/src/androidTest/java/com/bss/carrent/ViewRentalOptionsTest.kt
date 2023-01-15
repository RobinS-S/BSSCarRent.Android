package com.bss.carrent

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.bss.carrent.ui.auth.LoginFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ViewRentalOptionsTest {

    @Test
    fun testLogin() {
        onView(withId(R.id.email)).perform(replaceText("julian@test.nl"))
        onView(withId(R.id.password)).perform(replaceText("wachtwoord"))
        onView(withId(R.id.login_button)).perform(click())

        onView(withId(R.id.car_list_recycler_view)).check(matches(isDisplayed()))

    }
}