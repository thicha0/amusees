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

// nom, prénom, anniversaire, email, login, mot de passe, adresse, ville, pays, sexe
@Keep
@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "id")
                private var _id: Long = 0L,

                @ColumnInfo(name = "lastname")
                private var _lastname: String? = "",

                @ColumnInfo(name = "firstname")
                private var _firstname: String? = "",

                @ColumnInfo(name = "birthday_date")
                private var _birthdayDate: Long = 0,

                @ColumnInfo(name = "email")
                private var _email: String? = "",

                @ColumnInfo(name = "login")
                private var _login: String? = "",

                // TODO : encoder le mot de passe
                @ColumnInfo(name = "password")
                private var _password: String? = "",

                @ColumnInfo(name = "adress")
                private var _adress: String? = "",

                @ColumnInfo(name = "city")
                private var _city: String? = "",

                @ColumnInfo(name = "country")
                private var _country: String? = "",

                @ColumnInfo(name = "gender")
                private var _gender: String? = ""):
    BaseObservable(), Parcelable {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }

    var lastname: String?
        @Bindable get() = _lastname
        set(value) {
            _lastname = value
            notifyPropertyChanged(BR.lastname)
        }


    var firstname: String?
        @Bindable get() = _firstname
        set(value) {
            _firstname = value
            notifyPropertyChanged(BR.firstname)
        }


    var birthdayDate: Long
        @Bindable get() = _birthdayDate
        set(value) {
            _birthdayDate = value
            notifyPropertyChanged(BR.birthdayDate)
        }

    var login: String?
        @Bindable get() = _login
        set(value) {
            _login = value
            notifyPropertyChanged(BR.login)
        }

    var email: String?
        @Bindable get() = _email
        set(value) {
            _email = value
            notifyPropertyChanged(BR.email)
        }

    var password: String?
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }

    var adress: String?
        @Bindable get() = _adress
        set(value) {
            _adress = value
            notifyPropertyChanged(BR.adress)
        }

    var city: String?
        @Bindable get() = _email
        set(value) {
            _email = value
            notifyPropertyChanged(BR.city)
        }

    var country: String?
        @Bindable get() = _country
        set(value) {
            _country = value
            notifyPropertyChanged(BR.country)
        }

    var gender: String?
        @Bindable get() = _gender
        set(value) {
            _gender = value
            notifyPropertyChanged(BR.gender)
        }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_lastname)
        parcel.writeString(_firstname)
        parcel.writeLong(_birthdayDate)
        parcel.writeString(_email)
        parcel.writeString(_login)
        parcel.writeString(_password)
        parcel.writeString(_adress)
        parcel.writeString(_city)
        parcel.writeString(_country)
        parcel.writeString(_gender)
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

}