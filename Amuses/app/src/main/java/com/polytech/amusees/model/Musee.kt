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

// nom, région, département, ville, adresse, latitude, longitude, site web, téléphone, période d'ouverture, réf du musée
@Keep
@Entity(tableName = "musee")
data class Musee(@ColumnInfo(name = "nom")
                private var _nom: String? = "",

                @ColumnInfo(name = "region")
                private var _region: String? = "",

                @ColumnInfo(name = "departement")
                private var _departement: String? = "",

                 @ColumnInfo(name = "ville")
                 private var _ville: String? = "",

                 @ColumnInfo(name = "adresse")
                 private var _adresse: String? = "",

                @ColumnInfo(name = "latitude")
                private var _latitude: Double = 48.8566,

                 @ColumnInfo(name = "longitude")
                 private var _longitude: Double = 2.3522,

                 @ColumnInfo(name = "siteweb")
                 private var _siteweb: String? = "",

                 @ColumnInfo(name = "telephone")
                 private var _telephone: String? = "",

                 @ColumnInfo(name = "periodeouverture")
                 private var _periodeouverture: String? = "",

                 @PrimaryKey
                 @ColumnInfo(name = "refmusee")
                 private var _refmusee: String? = ""):
    BaseObservable(), Parcelable {

    var nom: String?
        @Bindable get() = _nom
        set(value) {
            _nom = value
            notifyPropertyChanged(BR.nom)
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

    var ville: String?
        @Bindable get() = _ville
        set(value) {
            _ville = value
            notifyPropertyChanged(BR.ville)
        }

    var adresse: String?
        @Bindable get() = _adresse
        set(value) {
            _adresse = value
            notifyPropertyChanged(BR.adresse)
        }

    var latitude: Double
        @Bindable get() = _latitude
        set(value) {
            _latitude = value
            notifyPropertyChanged(BR.latitude)
        }

    var longitude: Double
        @Bindable get() = _longitude
        set(value) {
            _longitude = value
            notifyPropertyChanged(BR.longitude)
        }

    var siteweb: String?
        @Bindable get() = _siteweb
        set(value) {
            _siteweb = value
            notifyPropertyChanged(BR.siteweb)
        }

    var telephone: String?
        @Bindable get() = _telephone
        set(value) {
            _telephone = value
            notifyPropertyChanged(BR.telephone)
        }

    var periodeouverture: String?
        @Bindable get() = _periodeouverture
        set(value) {
            _periodeouverture = value
            notifyPropertyChanged(BR.periodeouverture)
        }

    var refmusee: String?
        @Bindable get() = _refmusee
        set(value) {
            _refmusee = value
            notifyPropertyChanged(BR.refmusee)
        }


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_nom)
        parcel.writeString(_region)
        parcel.writeString(_departement)
        parcel.writeString(_ville)
        parcel.writeString(_adresse)
        parcel.writeDouble(_latitude)
        parcel.writeDouble(_longitude)
        parcel.writeString(_siteweb)
        parcel.writeString(_telephone)
        parcel.writeString(_periodeouverture)
        parcel.writeString(_refmusee)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        var message = "Nom : "+ this.nom + "\n"+
                "Région : "+this.region + "\n"+
                "Département : "+this.departement+"\n"+
                "Ville : "+this.ville+"\n"+
                "Adresse : "+this.adresse+"\n"+
                "Latitude : "+this.latitude+"\n"+
                "Longitude : "+this.longitude+"\n"+
                "Site web : "+this.siteweb+"\n"+
                "Téléphone : "+this.telephone+"\n"+
                "Période d'ouverture : "+this.periodeouverture+"\n"+
                "Référence du musée : "+this.refmusee+"\n"

        return message
    }

}