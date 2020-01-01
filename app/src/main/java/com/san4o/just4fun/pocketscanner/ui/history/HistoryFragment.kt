package com.san4o.just4fun.pocketscanner.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.databinding.ViewHolderHistoryItemBinding
import com.san4o.just4fun.pocketscanner.presentation.base.toFullDatetimeFormat
import com.san4o.just4fun.pocketscanner.presentation.history.HistoryItem
import com.san4o.just4fun.pocketscanner.presentation.history.HistoryViewModel
import com.san4o.just4fun.pocketscanner.presentation.result.BarcodeParams
import com.san4o.just4fun.pocketscanner.ui.base.inflateBinding
import com.san4o.just4fun.pocketscanner.ui.base.setVisibile
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {

    val viewModel by viewModel<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            HistoryAdapter {
                val arg = BarcodeParams.arguments(
                    BarcodeParams(
                        id = it
                    )
                )
                findNavController().navigate(
                    R.id.action_historyFragment_to_historyItemFragment,
                    arg
                )
            }
        barcodeContainer.adapter = adapter
        barcodeContainer.layoutManager = LinearLayoutManager(requireContext())

        viewModel.historyItems.observe(this, Observer {
            adapter.update(it)
        })

        viewModel.loadItems()
    }

}

class HistoryAdapter(
    private val onItemClickFunc: (Long) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {

    private val items = ArrayList<HistoryItem>()

    fun update(items: List<HistoryItem>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(
            parent
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = items[position]
        val id = item.id

        holder.binding.name.text = item.name
        holder.binding.name.setVisibile(item.name.isNotEmpty())
        holder.binding.date.text = item.date.toFullDatetimeFormat()
        holder.binding.data.text = item.barcode
        holder.binding.itemLayout.setOnClickListener {
            onItemClickFunc.invoke(id)
        }
    }
}

class HistoryViewHolder(
    val binding: ViewHolderHistoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(viewGroup: ViewGroup): HistoryViewHolder {

            val view =
                viewGroup.inflateBinding<ViewHolderHistoryItemBinding>(R.layout.view_holder_history_item)

            return HistoryViewHolder(
                view
            )
        }
    }

}

