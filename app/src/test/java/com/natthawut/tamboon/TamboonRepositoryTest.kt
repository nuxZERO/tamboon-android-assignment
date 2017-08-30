package com.natthawut.tamboon

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
    val repository = TamboonRepository(remote)

    @Test
    fun getCharities() {
        // Give
        val charities = arrayListOf(Charity(), Charity(), Charity())
        Mockito.`when`(remote.getOrganizations()).thenReturn(Observable.just(charities))

        // When
        val testObservable = repository.getOrganizations().test()
        testObservable.awaitTerminalEvent()

        // Then

    }

}
