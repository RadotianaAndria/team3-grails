package test

import org.joda.time.DateTime

class Banner {
    String name
    Date created

    static constraints = {
        name nullable: false, blank: false, unique: true
        created nullable: false, blank: false, unique: true
    }
}
