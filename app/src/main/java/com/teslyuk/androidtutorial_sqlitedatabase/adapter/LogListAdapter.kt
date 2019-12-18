package com.teslyuk.androidtutorial_sqlitedatabase.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.teslyuk.androidtutorial_sqlitedatabase.R
import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel

/**
 * Created by taras.teslyuk on 11/16/15.
 */
class LogListAdapter(private val context: Context) : BaseAdapter() {

    private val TAG = javaClass.getSimpleName()
    internal var listener: LogListAdapterEventListener? = null
    private val list = mutableListOf<LogModel>()

    interface LogListAdapterEventListener {
        fun removeLog(position: Int)
    }

    init {
        Log.d(TAG, "items count: " + list.size)
    }

    fun addListener(listener: LogListAdapterEventListener) {
        this.listener = listener
    }

    internal class ViewHolder(root: View) {
        var name: TextView = root.findViewById(R.id.item_log_name)
        var delete: ImageView = root.findViewById(R.id.item_log_remove)

        fun bind(model: LogModel, position: Int, listener: LogListAdapterEventListener?) {
            name.text = model.message

            delete.setOnClickListener {
                if (listener != null) {
                    listener!!.removeLog(position)
                }
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        if (convertView == null) {
            val inflator = (context as Activity).layoutInflater
            view = inflator.inflate(R.layout.item_log, null)
            val viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
        }

        val holder = view?.tag as ViewHolder

        holder.bind(list[position], position, listener)

        return view
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun onDataUpdate(newData: List<LogModel>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}