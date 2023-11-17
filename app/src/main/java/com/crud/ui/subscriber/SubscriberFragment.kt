package com.crud.ui.subscriber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crud.R
import com.crud.data.db.AppDataBase
import com.crud.data.db.dao.SubscriberDAO
import com.crud.databinding.FragmentSubscriberBinding
import com.crud.extension.hideKeyboard
import com.crud.repository.DataBaseDataSource
import com.crud.repository.SubscriberRepository
import com.google.android.material.snackbar.Snackbar

class SubscriberFragment : Fragment(R.layout.fragment_subscriber) {

    private val viewModel: SubscriberViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDataBase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DataBaseDataSource(subscriberDAO)
                return SubscriberViewModel(repository) as T
            }
        }
    }

    private lateinit var binding: FragmentSubscriberBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSubscriberBinding.bind(view)

        observeEvents()
        setListeners()
    }
    private fun observeEvents() {
        viewModel.subcriberStateEvenData.observe(viewLifecycleOwner) { subscriberState ->
            when (subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()

                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEvenData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.inputName.text?.clear()
        binding.inputData.text?.clear()
        binding.inputCpf.text?.clear()
        binding.inputCel.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        binding.buttonAdd.setOnClickListener {
            val name = binding.inputName.text.toString()
            val birth = binding.inputData.text.toString()
            val cpf = binding.inputCpf.text.toString()
            val tel = binding.inputCel.text.toString()

            viewModel.addSubscriber(name, birth, cpf, tel)
        }
    }
}