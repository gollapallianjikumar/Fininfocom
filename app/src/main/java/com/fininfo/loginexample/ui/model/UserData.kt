package com.fininfo.loginexample.ui.model

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.Objects

open class UserData : RealmObject(),java.io.Serializable {
    @PrimaryKey
    var id: String = ObjectId().toHexString()

    var name: String = ""

    var age: Int = 0

    var city: String = ""
}
