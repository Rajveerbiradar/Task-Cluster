package com.journeytix.taskcluster.ui.components.core

import com.journeytix.taskcluster.R

object SectionIcons {

    val all: Map<String, Int> by lazy {
        R.drawable::class.java.fields
            .filter { it.name.startsWith("ic_section_") }
            .associate { field ->
                field.name.removePrefix("ic_section_") to field.getInt(null)
            }
            .toSortedMap()
    }

    val keys: List<String> get() = all.keys.toList()

    fun resId(key: String?): Int? = key?.let { all[it] }

    fun resIdOrDefault(key: String?): Int {
        val direct = key?.let { all[it] }
        if (direct != null) return direct
        return all["default"] ?: R.drawable.ic_section_default
    }
}
