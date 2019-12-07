package com.polytech.amusees.model

enum class Column(val value: String, val string: String) {
    nom("nom_du_musee","Nom du musée"),
    ville("ville","Ville"),
    dep("nomdep", "Département"),
    region("new_regions","Région");

    fun columnStrings() : Array<String?> {
        var strings: ArrayList<String> = arrayListOf()
        for (col in Column.values()) {
            strings.add(col.toString())
        }

        val arrayNames = arrayOfNulls<String>(strings.size)
        strings.toArray(arrayNames)

        return arrayNames
    }
}

class Request {
    //recherche
    var refine: Column = Column.nom
    var refine_value: String = ""

    //tri
    var sort: Column = Column.nom

    //pages
    var page: Int = 0
    var rows: Int = 10
}