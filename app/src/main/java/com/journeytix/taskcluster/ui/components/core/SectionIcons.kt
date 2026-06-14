package com.journeytix.taskcluster.ui.components.core

import com.journeytix.taskcluster.R

/* Section icons live as ic_section_<name>.xml vector drawables. The public key is
   the CLEAN hyphenated form shown in the picker and stored in import JSON:
     file ic_section_apple_reminder.xml  <->  key "apple-reminder"
   Rule: strip "ic_section_" and swap underscores for hyphens to get the key;
   reverse (hyphens->underscores, add prefix) to resolve a key back to a drawable.
   Unknown/missing keys fall back to ic_section_default. */
object SectionIcons {

    // Clean hyphenated key -> drawable resId, discovered by scanning R.drawable.
    private val byKey: Map<String, Int> by lazy {
        R.drawable::class.java.fields
            .filter { it.name.startsWith("ic_section_") }
            .associate { field ->
                val key = field.name.removePrefix("ic_section_").replace('_', '-')
                key to field.getInt(null)
            }
            .toSortedMap()
    }

    /** Clean hyphenated keys, sorted, excluding the "default" fallback. */
    val keys: List<String> get() = byKey.keys.filter { it != "default" }

    fun resId(key: String?): Int? = key?.let { byKey[it] }

    fun resIdOrDefault(key: String?): Int {
        val direct = key?.let { byKey[it] }
        if (direct != null) return direct
        return byKey["default"] ?: R.drawable.ic_section_default
    }
}
