package project.utils.extension

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import project.utils.rx.SchedulerProvider

fun <T> Observable<T>.onUi(schedulerProvider: SchedulerProvider) : Observable<T>
        = this.observeOn(schedulerProvider.ui())

fun <T> Observable<T>.forDatabase(schedulerProvider: SchedulerProvider) : Observable<T>
        = this.subscribeOn(schedulerProvider.database())

fun <T> Observable<T>.forIo(schedulerProvider: SchedulerProvider) : Observable<T>
        = this.subscribeOn(schedulerProvider.io())

fun <T> Observable<T>.forComputation(schedulerProvider: SchedulerProvider) : Observable<T>
        = this.subscribeOn(schedulerProvider.computation())

 
fun <T> Flowable<T>.onUi(schedulerProvider: SchedulerProvider) : Flowable<T>
        = this.observeOn(schedulerProvider.ui())

fun <T> Flowable<T>.forDatabase(schedulerProvider: SchedulerProvider) : Flowable<T>
        = this.subscribeOn(schedulerProvider.database())

fun <T> Flowable<T>.forIo(schedulerProvider: SchedulerProvider) : Flowable<T>
        = this.subscribeOn(schedulerProvider.io())

fun <T> Flowable<T>.forComputation(schedulerProvider: SchedulerProvider) : Flowable<T>
        = this.subscribeOn(schedulerProvider.computation())


fun <T> Single<T>.onUi(schedulerProvider: SchedulerProvider) : Single<T>
        = this.observeOn(schedulerProvider.ui())

fun <T> Single<T>.forDatabase(schedulerProvider: SchedulerProvider) : Single<T>
        = this.subscribeOn(schedulerProvider.database())

fun <T> Single<T>.forIo(schedulerProvider: SchedulerProvider) : Single<T>
        = this.subscribeOn(schedulerProvider.io())

fun <T> Single<T>.forComputation(schedulerProvider: SchedulerProvider) : Single<T>
        = this.subscribeOn(schedulerProvider.computation())


fun <T> Maybe<T>.onUi(schedulerProvider: SchedulerProvider) : Maybe<T>
        = this.observeOn(schedulerProvider.ui())

fun <T> Maybe<T>.forDatabase(schedulerProvider: SchedulerProvider) : Maybe<T>
        = this.subscribeOn(schedulerProvider.database())

fun <T> Maybe<T>.forIo(schedulerProvider: SchedulerProvider) : Maybe<T>
        = this.subscribeOn(schedulerProvider.io())

fun <T> Maybe<T>.forComputation(schedulerProvider: SchedulerProvider) : Maybe<T>
        = this.subscribeOn(schedulerProvider.computation())





