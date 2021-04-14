package com.komangss.submissionjetpack.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object CustomMatchers {
    fun withBackground(resourceId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                return sameBitmap(view.context, view.background, resourceId)
            }

            override fun describeTo(description: Description) {
                description.appendText("has background resource $resourceId")
            }
        }
    }

    fun withCompoundDrawable(resourceId: Int): BoundedMatcher<View?, TextView> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has compound drawable resource $resourceId")
            }

            public override fun matchesSafely(textView: TextView): Boolean {
                for (drawable in textView.compoundDrawables) {
                    if (sameBitmap(textView.context, drawable, resourceId)) {
                        return true
                    }
                }
                return false
            }
        }
    }

    fun withImageDrawable(resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View?, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has image drawable resource $resourceId")
            }

            public override fun matchesSafely(imageView: ImageView): Boolean {
                return sameBitmap(imageView.context, imageView.drawable, resourceId)
            }
        } as Matcher<View>
    }

    private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {
        var drawable: Drawable? = drawable
        var otherDrawable = context.resources.getDrawable(resourceId)
        if (drawable == null || otherDrawable == null) {
            return false
        }
        if (drawable is StateListDrawable && otherDrawable is StateListDrawable) {
            drawable = drawable.getCurrent()
            otherDrawable = otherDrawable.getCurrent()
        }
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val otherBitmap = (otherDrawable as BitmapDrawable).bitmap
            return bitmap.sameAs(otherBitmap)
        }
        return false
    }
}