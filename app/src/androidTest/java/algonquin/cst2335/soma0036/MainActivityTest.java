package algonquin.cst2335.soma0036;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testfindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPass));
        appCompatEditText.perform(replaceText("vinit@123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.login)));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.passText)));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testfindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPass));
        appCompatEditText.perform(replaceText("VINIT@123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.login)));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.passText)));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testfindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPass));
        appCompatEditText.perform(replaceText("Vinit@"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.login)));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.passText)));
        textView.check(matches(withText("You shall not pass")));
    }
    @Test
    public void testfindMissingSpecialCharacter() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPass));
        appCompatEditText.perform(replaceText("Vinit123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.login)));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.passText)));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testAllRequirements() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPass));
        appCompatEditText.perform(replaceText("Vinit@123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.login)));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.passText)));
        textView.check(matches(withText("Your password meets the requirements")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }
            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
