package com.io.myutilsproject

import com.io.myutilsproject.screens.first.FirstPresenter
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.navigation.PresenterFactory
import dagger.*
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import javax.inject.Provider
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

    @Multibinds
    fun provideEmptyPresenters(): Map<Class<out Presenter>, Presenter>

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
    @[IntoMap PresenterKey(ThirdPresenter::class)]
    fun provideFirstPresenter(thirdPresenter: ThirdPresenter): Presenter
}

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NextScope

@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class PresenterKey(val value: KClass<out Presenter>)