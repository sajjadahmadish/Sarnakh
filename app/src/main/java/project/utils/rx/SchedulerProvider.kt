package project.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun database(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}