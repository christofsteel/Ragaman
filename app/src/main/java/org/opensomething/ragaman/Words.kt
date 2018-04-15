package org.opensomething.ragaman
import android.text.TextUtils.replace
import kotlinx.serialization.*

@Serializable
class ProtoWords(val words:ArrayList<String>)

class Words() {
    var words:ArrayList<Pair<String, String>> = ArrayList()
    private lateinit var sortedwords : Map<Pair<String,String>, String>

    private fun anagramify(input: String, str:Boolean=false) : String {
        return String((if(str) input.toLowerCase().replace("traße", "trasse.").replace("str.", "strasse.") else input.toLowerCase()).replace("ü","ue").replace("ä","ae")
                .replace("ö","oe").replace("ß","ss").toCharArray().sortedArray())
    }

    private fun substr(word1: String, word2: String) : Boolean{
        if(word1.isEmpty())
            return true
        var pos1 = 0
        var pos2 = 0
        while (pos2 < word2.length) {
            if(word1[pos1] == word2[pos2]) {
                pos1++
                pos2++
                if(pos1 == word1.length) {
                    return true
                }
            } else {
                pos2++
            }
        }
        return false
    }

    fun addWords(newwords: ArrayList<String>, type:String) {
        for(word in newwords) {
            words.add(Pair(word, type))
        }
        words.sortBy({it.first})
    }

    fun buildIndex() {
        sortedwords = words.associateBy({ Pair(anagramify(it.first, true), it.second)},{it.first} )
    }

    fun filtered(input: String) : ArrayList<Pair<String, String>> {
        val l = arrayListOf<Pair<String, String>>()
        for ((key, value) in sortedwords) {
            if(substr(anagramify(input), key.first)) {
                l.add(Pair(value, key.second))
            }
        }
        if(input.isEmpty())
            return ArrayList(l.sortedBy({it.first}))
        return ArrayList(l.sortedBy({it.first.length}))
    }

}