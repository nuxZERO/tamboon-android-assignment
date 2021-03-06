package com.natthawut.tamboon.repository

import co.omise.android.Client
import co.omise.android.TokenRequest
import co.omise.android.TokenRequestListener
import co.omise.android.models.Token
import com.natthawut.tamboon.repository.remote.ApiRemote
import com.natthawut.tamboon.repository.remote.Charity
import com.natthawut.tamboon.repository.remote.Donation
import com.natthawut.tamboon.repository.remote.DonationResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class TamboonRepository(val remote: ApiRemote, val client: Client) {

    fun getCharities(): Observable<List<Charity>> {
        return remote.getCharities()
    }

    /**
     * It not working yet. This Observable wrap send request Omise token.
     * Then use token to donate endpoint.
     * */
    fun donate(tokenRequest: TokenRequest, amount: Int): Observable<DonationResponse> {
        return Observable.create<Token> { emitter ->
            client.send(tokenRequest, object : TokenRequestListener {
                override fun onTokenRequestSucceed(request: TokenRequest?, token: Token?) {
                    if (token != null) {
                        emitter.onNext(token)
                        emitter.onComplete()
                    } else {
                        emitter.onError(NullPointerException("Token was null."))
                    }
                }

                override fun onTokenRequestFailed(request: TokenRequest?, throwable: Throwable?) {
                    emitter.onError(throwable!!)
                }
            })
        }.flatMap { token ->
            val donation = Donation()
            donation.name = tokenRequest.name
            donation.token = token.id
            donation.amount = amount
            remote.donate(donation)
        }
    }

    fun donate(tokenRequest: TokenRequest, amount: Int, response: (result: DonationResponse?,
                                                                   errorMessage: String?) -> Unit) {

        // Retrieve token from card info.
        client.send(tokenRequest, object : TokenRequestListener {
            override fun onTokenRequestSucceed(request: TokenRequest?, token: Token?) {
                if (token != null) {

                    // Create donate body request
                    val donation = Donation()
                    donation.name = tokenRequest.name
                    donation.token = token.id
                    donation.amount = amount

                    // Call donate endpoint
                    remote.donate(donation)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(object : WrapperObserver<DonationResponse>() {
                                override fun success(t: DonationResponse) {
                                    response(t, null)
                                }

                                override fun failure(errorMessage: String?) {
                                    response(null, errorMessage)
                                }

                            })
                } else {
                    response(null, "Token was null.")
                }
            }

            override fun onTokenRequestFailed(request: TokenRequest?, error: Throwable?) {
                response(null, error?.message?.capitalize()?.trim())
            }
        })
    }

}
