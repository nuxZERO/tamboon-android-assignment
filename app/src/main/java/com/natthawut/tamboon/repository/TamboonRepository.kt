package com.natthawut.tamboon.repository

import co.omise.android.Client
import co.omise.android.TokenRequest
import co.omise.android.TokenRequestListener
import co.omise.android.models.Token
import com.natthawut.tamboon.remote.ApiRemote
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.remote.Donation
import com.natthawut.tamboon.remote.DonationResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class TamboonRepository(val remote: ApiRemote, val client: Client) {

    fun getOrganizations(): Observable<List<Charity>> {
        return remote.getOrganizations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun donate(tokenRequest: TokenRequest, amount: Int, response: (result: DonationResponse) -> Unit) {

        client.send(tokenRequest, object : TokenRequestListener {
            override fun onTokenRequestSucceed(p0: TokenRequest?, token: Token?) {
                if (token != null) {
                    val donation = Donation()
                    donation.name = tokenRequest.name
                    donation.token = token.id
                    donation.amount = amount
                    remote.donate(donation)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { result -> response(result)}
                }
            }

            override fun onTokenRequestFailed(p0: TokenRequest?, p1: Throwable?) {

            }
        })
    }

}
