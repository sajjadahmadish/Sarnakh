/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package project.data.local.db

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import project.data.model.*


/**
 * Created by amitshekhar on 07/07/17.
 */

interface DbHelper {


    val markers: Flowable<List<Marker>>

    fun insertMarker(markers: List<Marker>): Observable<Boolean>

    fun deleteListMarker(idList: List<String>): Observable<Boolean>

    fun truncateMarker() : Observable<Boolean>

}
