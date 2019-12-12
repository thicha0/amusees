package com.polytech.amusees.model

import android.os.Parcel
import android.os.Parcelable

enum class Column(val string: String) {
    nom_du_musee("Nom du musée"),
    ville("Ville"),
    nomdep("Département"),
    new_regions("Région");

    fun columnStrings() : Array<String?> {
        var strings: ArrayList<String> = arrayListOf()
        for (col in Column.values()) {
            strings.add(col.string)
        }

        val arrayNames = arrayOfNulls<String>(strings.size)
        strings.toArray(arrayNames)

        return arrayNames
    }
}

class Request() :Parcelable {
    //recherche
    var refine: Column = Column.nom_du_musee
    var refine_value: String? = null

    //tri
    var sort: Column = Column.nom_du_musee

    //pages
    var page: Int = 0
    var rows: Int = 10

    constructor(parcel: Parcel) : this() {
        refine_value = parcel.readString()
        page = parcel.readInt()
        rows = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(refine_value)
        parcel.writeInt(page)
        parcel.writeInt(rows)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Request> {
        override fun createFromParcel(parcel: Parcel): Request {
            return Request(parcel)
        }

        override fun newArray(size: Int): Array<Request?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        var message = "Refine type : "+ this.refine + "\n"+
                "Refine value : "+this.refine_value + "\n"+
                "Tri : "+this.sort+"\n"+
                "Page : "+this.page+"\n"+
                "Rows : "+this.rows+"\n"

        return message
    }
}