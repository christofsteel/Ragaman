package org.opensomething.ragaman
import kotlinx.serialization.*

@Serializable
class Point(val name:String, val lon:Double, val lat:Double)

@Serializable
class ProtoWords(val words:ArrayList<Point>)

//class Point(val name:String, val lon: Double, val lat: Double, val type:String)

class Words() {
    var words:ArrayList<Pair<Point, String>> = ArrayList()
    private lateinit var sortedwords : Map<Pair<String,String>, Point>

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

    fun addWords(newwords: ArrayList<Point>, type:String) {
        for(word in newwords) {
            words.add(Pair(word, type))
        }
        words.sortBy({it.first.name})
    }

    fun buildIndex() {
        sortedwords = words.associateBy({ Pair(anagramify(it.first.name, true), it.second)},{it.first} )
    }

    fun filtered(input: String) : ArrayList<Pair<Point, String>> {
        val anagramifiedInput = anagramify(input)
        val l = arrayListOf<Pair<Point, String>>()
        for ((key, value) in sortedwords) {
            if(substr(anagramifiedInput, key.first)) {
                l.add(Pair(value, key.second))
            }
        }
        if(input.isEmpty())
            return ArrayList(l.sortedBy({it.first.name}))
        return ArrayList(l.sortedBy({it.first.name.length}))
    }

}