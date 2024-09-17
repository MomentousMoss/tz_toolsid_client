package com.momentousmoss.tz_toolsid_client.ui.test_screen

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.api.JsonService.TestDataQRPayload
import com.momentousmoss.tz_toolsid_client.databinding.ItemTestDataPayloadBinding

class PayloadListAdapter(
    private val payloadList: List<TestDataQRPayload?>,
) : RecyclerView.Adapter<PayloadListAdapter.PayloadListViewHolder>() {

    class PayloadListViewHolder internal constructor(binding: ItemTestDataPayloadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val blockId: TextView = binding.blockId
        val stageName: TextView = binding.stageName
        val stageAddress: TextView = binding.stageAddress
        val blockName: TextView = binding.blockName
        val ping: TextView = binding.ping
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PayloadListViewHolder {
        return PayloadListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_test_data_payload,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(payloadListViewHolder: PayloadListViewHolder, i: Int) {
        val resources = payloadListViewHolder.itemView.context.resources
        payloadList[i].apply {
            val payloadData = this
            payloadListViewHolder.apply {
                payloadData?.let {
                    blockId.text = it.block_id.toString()
                    stageName.text = it.stage_name
                    stageAddress.text = it.stage_address
                    blockName.text = it.block_name
                    ping.text =  resources.getString(R.string.test_ping_text, it.block_ping)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return payloadList.size
    }
}