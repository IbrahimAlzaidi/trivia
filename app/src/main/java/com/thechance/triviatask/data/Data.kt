package com.thechance.triviatask.data

import android.util.Log
import com.thechance.triviatask.util.model.TriviaQuestion
import com.thechance.triviatask.util.model.TriviaResult
import io.reactivex.rxjava3.core.Observable

object Data {
    fun getResultForQuiz(): Observable<State<TriviaQuestion>> {
        return getQuizInfo().flatMap() {
            when(it){
                is State.Error ->{
                    Observable.create{ emitter ->
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                is State.Loading ->{
                    Observable.create{ emitter ->
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                is State.Success ->{
                    Observable.create { emitter ->
                        if (it.data.itemTypes?.isEmpty() == true){
                            emitter.onNext(State.Error("Data not found"))
                            Log.i("Status.Success_if",it.toString())
                        } else {
                            emitter.onNext(Network.makeRequestUsingOkhttp()) //to make it easier we pick the first city and skip others
                        }
                        emitter.onComplete()
                    }
                }
            }
        }
    }
    private fun getQuizInfo(): Observable<State<TriviaQuestion>> {
        return Observable.create { emitter ->
            emitter.onNext(State.Loading)
            emitter.onNext(Network.makeRequestUsingOkhttp())
            emitter.onComplete()
        }
    }
}
