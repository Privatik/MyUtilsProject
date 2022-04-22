package com.io.myutilsproject

import com.io.navigation.UIPresenter
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    val factory: MyPresenterFactory

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}

@Module
interface AppModule{

    @Binds
    @[IntoMap PresenterKey(FirstPresenter::class)]
    fun provideFirstPresenter(firstPresenter: FirstPresenter): Presenter

    @Binds
    @[IntoMap PresenterKey(SecondPresenter::class)]
    fun provideSecondPresenter(secondPresenter: SecondPresenter): Presenter
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class PresenterKey(val value: KClass<out UIPresenter>)