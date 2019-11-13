package com.polytech.amusees.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// nom, région, département, code postal, ville, adresse, numéro de téléphone, site web, coordonnées géographiques lat et lon
//api : https://data.opendatasoft.com/explore/dataset/liste-et-localisation-des-musees-de-france%40culture/table/
@Keep
@Entity(tableName = "musee")
data class Musee(@PrimaryKey(autoGenerate = true)
                 @ColumnInfo(name = "id")
                 private var _id: Long = 0L,

                 @ColumnInfo(name = "name")
                 private var _name : String? = "",

                 @ColumnInfo(name = "region")
                 private var _region : String? = "",

                 @ColumnInfo(name = "departement")
                 private var _departement : String? = "",

                 @ColumnInfo(name = "postal_code")
                 private var _postal_code : String? = "",

                 @ColumnInfo(name = "city")
                 private var _city : String? = "",

                 @ColumnInfo(name = "adress")
                 private var _adress : String? = "",

                 @ColumnInfo(name = "phone")
                 private var _phone : String? = "",

                 @ColumnInfo(name = "website")
                 private var _website : String? = "",

                    @ColumnInfo(name = "lat")
                    private var _lat : Double = 48.8534,

                    @ColumnInfo(name = "lon")
                    private var _lon : Double = 2.3488):
    BaseObservable(), Parcelable {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }

    var name: String?
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    var region: String?
        @Bindable get() = _region
        set(value) {
            _region = value
            notifyPropertyChanged(BR.region)
        }

    var departement: String?
        @Bindable get() = _departement
        set(value) {
            _departement = value
            notifyPropertyChanged(BR.departement)
        }

    var postal_code: String?
        @Bindable get() = _postal_code
        set(value) {
            _postal_code = value
            notifyPropertyChanged(BR.postal_code)
        }

    var city: String?
        @Bindable get() = _city
        set(value) {
            _city = value
            notifyPropertyChanged(BR.city)
        }

    var adress: String?
        @Bindable get() = _adress
        set(value) {
            _adress = value
            notifyPropertyChanged(BR.adress)
        }

    var phone: String?
        @Bindable get() = _phone
        set(value) {
            _phone = value
            notifyPropertyChanged(BR.phone)
        }

    var website: String?
        @Bindable get() = _website
        set(value) {
            _website = value
            notifyPropertyChanged(BR.website)
        }

    var lat: Double
        @Bindable get() = _lat
        set(value) {
            _lat = value
            notifyPropertyChanged(BR.lat)
        }

    var lon: Double
        @Bindable get() = _lon
        set(value) {
            _lon = value
            notifyPropertyChanged(BR.lon)
        }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_name)
        parcel.writeString(_region)
        parcel.writeString(_departement)
        parcel.writeString(_postal_code)
        parcel.writeString(_city)
        parcel.writeString(_adress)
        parcel.writeString(_phone)
        parcel.writeString(_website)
        parcel.writeDouble(_lat)
        parcel.writeDouble(_lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Musee> {
        override fun createFromParcel(parcel: Parcel): Musee {
            return Musee(parcel)
        }

        override fun newArray(size: Int): Array<Musee?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        var message = "Nom du musée : " + this.name + "\n"+
                "Région : "+this.region+"\n"+
                "Département : "+this.departement+"\n"+
                "Code postal : "+this.postal_code+"\n"+
                "Ville : "+this.city+"\n"+
                "Adresse : "+this.adress+"\n"+
                "Téléphone : "+this.phone+"\n"+
                "Site web : "+this.website+"\n"+
                "Latitude : "+this.lat+"\n"+
                "Longitude : "+this.lon+"\n"
        return message
    }

}