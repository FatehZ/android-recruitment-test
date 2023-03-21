package com.ktxdevelopment.websocket.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ktxdevelopment.websocket.R
import com.ktxdevelopment.websocket.databinding.FragmentMainBinding
import com.ktxdevelopment.websocket.model.connection.DataState
import com.ktxdevelopment.websocket.model.connection.UIConnectionState
import com.ktxdevelopment.websocket.model.connection.UIState
import com.ktxdevelopment.websocket.mvvm.ConnectionViewModel
import com.ktxdevelopment.websocket.ui.rv.InvestAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO

@AndroidEntryPoint
class FragmentMain : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var investAdapter: InvestAdapter
    private lateinit var viewModel: ConnectionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[ConnectionViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }


    private fun initializeUI() {
        investAdapter = InvestAdapter()
        binding.rvInvests.apply {
            adapter = investAdapter
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false) }

        binding.btnConnection.setOnClickListener {
            if (binding.btnConnection.isChecked) viewModel.connect()
            else { viewModel.disconnect() }
        }

        initConnections()
    }

    val TAG = "LTS_TAG"


    private fun initConnections() {
        viewModel.launchObservers()
        viewModel.uiState.observe(viewLifecycleOwner) {
            Log.i(TAG, "initConnections: ${it.state}")
            investAdapter.submitList(it.data)
            when (it.state) {
                UIConnectionState.ONLINE -> { enableUI(it)  }
                UIConnectionState.CONNECTING -> { disableUI() }
                UIConnectionState.OFFLINE -> { enableUI(it) }
                UIConnectionState.DISCONNECTING -> { disableUI() }
            }
        }
    }

    private fun disableUI() {
        binding.btnConnection.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        binding.rvInvests.visibility = View.GONE
        binding.ivNoData.visibility = View.GONE
        binding.cvTop.alpha = 0.5F
    }

    private fun enableUI(state: UIState) {
        binding.cvTop.alpha = 1.0F
        binding.btnConnection.isEnabled = true
        binding.progressBar.visibility = View.GONE
        if (state.state == UIConnectionState.ONLINE) setStateConnected()
        else if (state.data.isEmpty()) setStateNoData()
        else setStateDisconnected()
    }

    private fun setStateDisconnected() {
        binding.ivConnectionState.setImageResource(R.drawable.disconnected)
        binding.btnConnection.isChecked = false
        binding.tvConnect.text = getString(R.string.disconnected)
        binding.ivNoData.visibility = View.GONE
        binding.rvInvests.visibility = View.VISIBLE
    }

    private fun setStateConnected() {
        binding.ivConnectionState.setImageResource(R.drawable.connected)
        binding.btnConnection.isChecked = true
        binding.rvInvests.visibility = View.VISIBLE
        binding.ivNoData.visibility = View.GONE
        binding.tvConnect.text = getString(R.string.connected)
    }

    private fun setStateNoData() {
        binding.ivConnectionState.setImageResource(R.drawable.disconnected)
        binding.btnConnection.isChecked = false
        binding.tvConnect.text = getString(R.string.disconnected)
        binding.rvInvests.visibility = View.GONE
        binding.ivNoData.visibility = View.VISIBLE
    }
}