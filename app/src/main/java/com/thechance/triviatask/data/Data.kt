package com.thechance.triviatask.data

import android.util.Log
import com.thechance.triviatask.util.model.TriviaQuestion
import com.thechance.triviatask.util.model.TriviaResult
import io.reactivex.rxjava3.core.Observable

object Data {
    fun getResultForQuiz(): Observable<State<TriviaQuestion>> {
        return Observable.create { emitter ->
            emitter.onNext(State.Loading)
            emitter.onNext(Network.makeRequestUsingOkhttp())
            emitter.onComplete()
        }
    }
}