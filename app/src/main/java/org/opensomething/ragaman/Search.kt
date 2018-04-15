package org.opensomething.ragaman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.serialization.json.JSON

class Search : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<Pair<Point, String>>
    private lateinit var words: Words

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                dataset.clear()
                dataset.addAll(words.filtered(editText.text.toString()))
                viewAdapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        var stopsJSON = assets.open("dsw21.json").bufferedReader().readText()
        var strassenJSON = assets.open("strassen.json").bufferedReader().readText()
        var poiJSON = assets.open("poi.json").bufferedReader().readText()

        var protostops = JSON.parse<ProtoWords>(stopsJSON)
        var protostrassen = JSON.parse<ProtoWords>(strassenJSON)

        words = Words()

        words.addWords(protostops.words, "stop")
        words.addWords(protostrassen.words, "street")
        words.addWords(JSON.parse<ProtoWords>(poiJSON).words, "poi")

        words.buildIndex()
        dataset = words.words

        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchAdapter(dataset)

        recyclerView = findViewById<RecyclerView>(R.id.searchResultsView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(false)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }


    }
}
