package com.example.madcampweek1

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable

data class GalleryItem(val imageResource: Int, val title: String) {
}

//data class GalleryItem(val imageResource: Drawable, val title: String) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        TODO("imageResource"),
//        parcel.readString(),
//        parcel.readString()) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<GalleryItem> {
//        override fun createFromParcel(parcel: Parcel): GalleryItem {
//            return GalleryItem(parcel)
//        }
//
//        override fun newArray(size: Int): Array<GalleryItem?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
