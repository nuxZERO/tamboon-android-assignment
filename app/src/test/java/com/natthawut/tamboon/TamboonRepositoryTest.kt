package com.natthawut.tamboon

import co.omise.android.Client
import co.omise.android.TokenRequest
import com.natthawut.tamboon.remote.ApiRemote
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.repository.TamboonRepository
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class TamboonRepositoryTest {

    val remote = Mockito.mock(ApiRemote::class.java)
    val client = Mockito.mock(Client::class.java)

    val repository = TamboonRepository(remote, client)

    @Test
    @Throws(Exception::class)
    fun getCharities() {
        // Give
        val charities = arrayListOf(Charity(), Charity(), Charity())
        Mockito.`when`(remote.getCharities()).thenReturn(Observable.just(charities))

        // When
        val testObservable = repository.getCharities().test()
        testObservable.awaitTerminalEvent()

        // Then
        testObservable.assertComplete()
    }

    @Test
    fun donate() {
        // Give
        val tokenRequest = TokenRequest()
        val amount = 10000

        // When
//        val testObservable = repository.donate(tokenRequest, amount).test()
//        testObservable.awaitTerminalEvent()

        // Then
//        testObservable.assertComplete()
    }

}
