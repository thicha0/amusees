package com.polytech.amusees.model

import android.os.Parcel
import android.os.Parcelable

class Request() :Parcelable {
    //recherche
    var query: String? = ""

    //tri
    var sort: String? = "ville"

    //pages
    var page: Int = 0
    var rows: Int = 10

    constructor(parcel: Parcel) : this() {
        query = parcel.readString()
        sort = parcel.readString()
        page = parcel.readInt()
        rows = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(query)
        parcel.writeString(sort)
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
        var message = "Refine type : "+ this.query + "\n"+
                "Tri : "+this.sort+"\n"+
                "Page : "+this.page+"\n"+
                "Rows : "+this.rows+"\n"

        return message
    }
}