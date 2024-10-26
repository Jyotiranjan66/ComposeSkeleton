package com.yudiz.testing.api


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.yudiz.testing.R
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApiTest {

    @get:Rule var mainAct = ActivityScenarioRule(MainActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)

        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                OkHttpProvider.getOkHttpClient()
            )
        )
    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("success_response.json"))
            }
        }
        mainAct.scenario

        onView(withId(R.id.pb))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rv_post))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tv_no_data))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testFailedResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(400)

//                throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }

        mainAct.scenario

        onView(withId(R.id.pb))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rv_post))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_no_data))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tv_no_data))
            .check(matches(withText(R.string.no_data)))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}