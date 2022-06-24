package com.io.myutilsproject

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.io.myutilsproject.screens.fifth.FifthScreen
import com.io.myutilsproject.screens.first.FirstPresenter
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.second.SecondEffect
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.seventh.SeventhScreen
import com.io.myutilsproject.screens.sixth.SixthScreen
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.TripleScreen
import com.io.navigation.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.extensions.*
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.generateGraph() {

    screen(
        name = Screens.FirstScreen.route,
    ) {
        val controller = LocalRootController.current
        val firstPresenter: FirstPresenter = presenter(key = Constant.APP_FACTORY)
        FirstScreen{
            controller.push(Screens.SecondScreen.route)
        }
    }

    screen(
        name = Screens.SecondScreen.route,
    ){
        RebuildConfig{ release(Constant.NEXT_FACTORY) }
        val controller = LocalRootController.current
        val secondPresenter: SecondPresenter = presenter(key = Constant.APP_FACTORY)
        val state = secondPresenter.state.collectAsState()
        val snackbarHostState = remember {
            SnackbarHostState()
        }

        LaunchedEffect(Unit) {
            secondPresenter
                .singleEffect
                .onEach {
                    when (it) {
                        is SecondEffect.Snack -> {
                            snackbarHostState.showSnackbar(
                                message = it.message,
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                }
                .launchIn(this)
        }

        SecondScreen(
            state = state.value,
            inc = { secondPresenter.inc(state.value.count) },
            incGod = { secondPresenter.incGod(state.value.godCount) },
            open = {
                controller.push(
                    screen = Screens.ThirdScreen.route
                )
            }
        )
    }

    screen(
        name = Screens.ThirdScreen.route,
    ){
        RebuildConfig{
            put(Constant.NEXT_FACTORY to ::createNextComponent)
        }
        val controller = LocalRootController.current
        val thirdPresenter: ThirdPresenter = presenter(key = Constant.NEXT_FACTORY)
        val state = thirdPresenter.state.collectAsState()
        val adapter = presenterController<OdesseyPresenterController>()

        TripleScreen(
            state = state.value,
            inc = { thirdPresenter.inc(state.value.count) },
            backToFirst = {
                controller.backToScreen(Screens.FirstScreen.route)
                adapter.pop()
            },
            next = {
                controller.present(
                    screen = Screens.FourScreen.route,
                    startScreen = Screens.FifthScreen.route
                )
            }
        )
    }

    flow(name = Screens.FourScreen.route) {
        screen(name = Screens.FifthScreen.route) {

                val controller = LocalRootController.current
                val thirdPresenter: ThirdPresenter = sharedPresenter(key = Constant.NEXT_FACTORY)
                val state = thirdPresenter.state.collectAsState()

                FifthScreen(
                    state = state.value,
                    inc = { thirdPresenter.inc(state.value.count) },
                    open = {
                        controller.push(
                            screen = Screens.SixthScreen.route
                        )
                    }
                )

        }

        screen(name = Screens.SixthScreen.route) {

                val controller = LocalRootController.current
                val thirdPresenter: ThirdPresenter = sharedPresenter(key = Constant.NEXT_FACTORY)
                val state = thirdPresenter.state.collectAsState()

                SixthScreen(
                    state = state.value,
                    inc = { thirdPresenter.inc(state.value.count) },
                    open = {
                        controller.push(
                            screen = Screens.SeventhScreen.route
                        )
                    }
                )

        }

        screen(name = Screens.SeventhScreen.route) {

                val thirdPresenter: ThirdPresenter = presenter(key = Constant.NEXT_FACTORY)
                val state = thirdPresenter.state.collectAsState()

                SeventhScreen(
                    state = state.value,
                    inc = { thirdPresenter.inc(state.value.count) },
                )
            }
    }

//    bottomNavigation(name = Screens.FourScreen.route, tabsNavModel = BottomConfiguration()) {
//        tab(FifthTab()) {
//            screen(name = Screens.FifthScreen.route) {
//                UpdatePresenter(factory = ::createNextComponent) {
//                    val thirdPresenter: ThirdPresenter = sharedPresenter()
//                    val state = thirdPresenter.state.collectAsState()
//
//                    FifthScreen(
//                        state = state.value,
//                        inc = { thirdPresenter.inc(state.value.count) }
//                    )
//                }
//            }
//        }
//
//        tab(SixthTab()) {
//            screen(name = Screens.SixthScreen.route) {
//                UpdatePresenter(factory = ::createNextComponent) {
//                    val controller = LocalRootController.current
//                    val thirdPresenter: ThirdPresenter = sharedPresenter()
//                    val state = thirdPresenter.state.collectAsState()
//
//                    SixthScreen(
//                        state = state.value,
//                        inc = { thirdPresenter.inc(state.value.count) },
//                        open = {
//                            controller.push(
//                                screen = Screens.SeventhScreen.route
//                            )
//                        }
//                    )
//                }
//            }
//
//            screen(name = Screens.SeventhScreen.route) {
//                UpdatePresenter(factory = ::createNextComponent) {
//                    val thirdPresenter: ThirdPresenter = presenter()
//                    val state = thirdPresenter.state.collectAsState()
//
//                    SeventhScreen(
//                        state = state.value,
//                        inc = { thirdPresenter.inc(state.value.count) },
//                    )
//                }
//            }
//
//        }
//    }
}