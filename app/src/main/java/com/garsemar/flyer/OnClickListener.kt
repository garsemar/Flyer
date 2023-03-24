package com.garsemar.flyer

import io.realm.kotlin.types.ObjectId

interface OnClickListener {
    fun onClick(bookId: ObjectId)
}