package com.natthawut.tamboon

import com.natthawut.tamboon.remote.ApiRemote
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.remote.Donation
import com.natthawut.tamboon.remote.DonationResponse
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiRemoteTest {

    private val mockWebServer = MockWebServer()
    private lateinit var apiRemote: ApiRemote

    @Before
    fun setUp() {
        apiRemote = ApiRemote(mockWebServer.url("/").toString())
    }

    @Test
    fun getCharities_complete() {
        // Give - Create mock charities response
        val mockResponse = MockResponse().setResponseCode(200)
                .setBody(" [\n" +
                        "     { \"id\": 0, \"name\": \"Ban Khru Noi\", \"logo_url\": \"http://rkdretailiq.com/news/img-corporate-baankrunoi.jpg\" },\n" +
                        "     { \"id\": 1, \"name\": \"Habitat for Humanity Thailand\", \"logo_url\": \"http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif\" },\n" +
                        "     { \"id\": 2, \"name\": \"Paper Ranger\", \"logo_url\": \"https://myfreezer.files.wordpress.com/2007/06/paperranger.jpg\" },\n" +
                        "     { \"id\": 3, \"name\": \"Makhampom\", \"logo_url\": \"http://www.makhampom.net/makhampom/ppcms/uploads/UserFiles/Image/Thai/T14Publice/2554/January/Newyear/logoweb.jpg\" }\n" +
                        "   ]")
        mockWebServer.enqueue(mockResponse)

        // When - Retrieve charities
        val testObserver = TestObserver<List<Charity>>()
        apiRemote.getCharities().subscribe(testObserver)

        // Then - Check request completed and result list count should equal 4
        testObserver.assertComplete()
        val actualCharities = testObserver.values()[0]
        Assert.assertEquals(4, actualCharities.size)
    }

    @Test
    fun donate_complete() {
        // Give
        val mockResponse = MockResponse().setResponseCode(200)
                .setBody("{\"success\": true\n}")
        mockWebServer.enqueue(mockResponse)

        // When
        val testObserver = TestObserver<DonationResponse>()
        apiRemote.donate(Donation()).subscribe(testObserver)

        // Then
        testObserver.assertComplete()
    }
}
