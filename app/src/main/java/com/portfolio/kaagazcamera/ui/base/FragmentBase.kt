package com.portfolio.kaagazcamera.ui.base

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

abstract class FragmentBase(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val navOptionsBuilder = NavOptions.Builder()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateBack()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun navigateTo(direction: NavDirections) {
        val navAction =
            requireNotNull(findNavController().currentDestination).getAction(direction.actionId)
        val predefinedNavOptions = requireNotNull(navAction).navOptions

        predefinedNavOptions?.apply {
            navOptionsBuilder.setPopUpTo(popUpToId, isPopUpToInclusive())
        }

        findNavController().navigate(direction, navOptionsBuilder.build())
    }

    protected fun <T> LiveData<T>.observe(observer: Observer<T>) =
        observe(viewLifecycleOwner, observer)

    protected open fun navigateBack() {
        findNavController().popBackStack()
    }
}