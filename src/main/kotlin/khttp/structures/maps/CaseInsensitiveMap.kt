/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package khttp.structures.maps

import java.util.*

class CaseInsensitiveMap<out V>(private val map: Map<String, V>) : Map<String, V> by map {

    override fun containsKey(key: String): Boolean {
        return this.map.keys.any { it.equals(key.lowercase(Locale.getDefault()), ignoreCase = true) }
    }

    override fun get(key: String): V? {
        return this.map.filter { it.key.equals(key.lowercase(Locale.getDefault()), ignoreCase = true) }.map { it.value }
            .firstOrNull()
    }

    override fun toString(): String {
        return this.map.toString()
    }

}
