package com.io.myutilsproject

import com.io.navigation.PresenterFactory
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Scope
import javax.inject.Singleton
import kotlin.reflect.KClass

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent: PresenterDeps {
    override val factory: MyPresenterFactory

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}

interface PresenterDeps{
    val factory: PresenterFactory
}

@Component(modules = [NextModule::class], dependencies = [PresenterDeps::class])
@NextScope
interface NextComponent {
    val factory: MyPresenterFactory

    @Component.Builder
    interface Builder {

        fun deps(deps: PresenterDeps): Builder

        fun build(): NextComponent
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

@Module
interface NextModule{

    @Binds
    @[IntoMap PresenterKey(ThriplePresenter::class)]
    fun provideFirstPresenter(thriplePresenter: ThriplePresenter): Presenter
}

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NextScope

@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class PresenterKey(val value: KClass<out Presenter>)