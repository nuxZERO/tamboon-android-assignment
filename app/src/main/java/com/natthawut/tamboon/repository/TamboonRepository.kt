package com.natthawut.tamboon.repository
import com.natthawut.tamboon.remote.ApiRemote
import com.natthawut.tamboon.remote.Charity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TamboonRepository(private val remote: ApiRemote) {

    fun getOrganizations(): Observable<List<Charity>> {
        return remote.getOrganizations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
