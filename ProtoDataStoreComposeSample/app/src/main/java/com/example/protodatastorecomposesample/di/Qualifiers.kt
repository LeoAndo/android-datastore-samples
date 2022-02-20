package com.example.protodatastorecomposesample.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class IoCoroutineScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class DefaultCoroutineScope