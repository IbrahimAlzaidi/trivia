package com.thechance.triviatask.data

import com.thechance.triviatask.util.model.TriviaQuestion
import io.reactivex.rxjava3.core.Observable

object Data {
    fun getResultForQuiz(): Observable<State<TriviaQuestion>> {
        return Observable.create { emitter ->
            if (!emitter.isDisposed)
                emitter.onNext(State.Loading)
            if (!emitter.isDisposed)
                emitter.onNext(Network.makeRequestUsingOkhttp())
            if (!emitter.isDisposed)
                emitter.onComplete()

        }
    }
}
