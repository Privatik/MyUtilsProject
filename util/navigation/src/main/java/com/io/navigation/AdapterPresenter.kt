package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.*

abstract class AdapterPresenter<Key: Any,Controller>(
    private val controller: Controller,
    startPresenterFactory: () -> PresenterFactory = ::emptyPresenter
) {
    private val presenterController = PresenterController<Key>()
    abstract val currentScreen: Flow<Key>

    @Composable
    private fun ListenerChangeState(){
        LaunchedEffect(Unit){
            currentScreen
                .onEach {
                    presenterController.updateFactory(it)
                }
                .launchIn(this)
        }
    }


    protected abstract fun getBackStack(): List<Key>

    final fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        isShared: Boolean
    ): P {
        return if (isShared)
            presenterController.getSharedPresenter(clazz)
        else
            presenterController.getPresenter(clazz)
    }

    fun popBackStack() {
        stopWorkPresenter(currentScreenKey)
        super.popBackStack()
        removeLastScreenWithPresenters()
    }

    fun backToScreenWithPresenter(screen: String){
        stopWorkPresenter(currentScreenKey)
        backToScreen(screen)
        deleteUnnecessaryFactory()
    }

    private fun deleteUnnecessaryFactory(){
        val backstack = getBackStack()

        var isFind = false
        while (!isFind){
            if (screensKeys.size == 1){
                val screenInfo = screensKeys.peek()
                updateFactoryForPresenter(screenInfo.presenterFactory)
                break
            }

            val screenInfo = screensKeys.pop()
            val currentScreen = backstack.findLast { it.key == screenInfo.screen }
            stopWorkPresenter(screenInfo.screen)

            if (currentScreen != null){
                updateFactoryForPresenter(screenInfo.presenterFactory)
                isFind = true
            }
        }
    }

    private fun removeLastScreenWithPresenters(){
        val screen = screensKeys.peek()
        if (screen.screen == currentScreenKey || screensKeys.size == 1){
            updateFactoryForPresenter(screen.presenterFactory)
            if (screensKeys.size != 1){
                screensKeys.pop()
            }
        }
    }
}