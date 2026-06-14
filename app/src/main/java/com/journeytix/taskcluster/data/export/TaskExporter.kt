package com.journeytix.taskcluster.data.export

import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task
import org.json.JSONArray
import org.json.JSONObject

object TaskExporter {

    fun export(
        parents: List<Parent>,
        sections: List<Section>,
        tasks: List<Task>,
    ): String {
        val tasksBySection = tasks.filter { !it.isTrashed }.groupBy { it.sectionId }
        val sectionsByParent = sections.filter { !it.isDaily }.groupBy { it.parentId }

        val parentsJson = JSONArray()
        parents.forEach { parent ->
            val parentObj = JSONObject()
            parentObj.put("title", parent.title)
            // Skip custom-image emojis — they're local file paths, not portable.
            parent.emoji?.takeUnless { it.startsWith("img:") }?.let { parentObj.put("emoji", it) }

            val sectionsJson = JSONArray()
            (sectionsByParent[parent.id] ?: emptyList()).forEach { section ->
                val sectionObj = JSONObject()
                sectionObj.put("title", section.title)
                section.iconKey?.let { sectionObj.put("icon", it) }

                val tasksJson = JSONArray()
                (tasksBySection[section.id] ?: emptyList()).forEach { task ->
                    val taskObj = JSONObject()
                    taskObj.put("title", task.title)
                    if (task.description.isNotEmpty()) taskObj.put("description", task.description)
                    tasksJson.put(taskObj)
                }
                sectionObj.put("tasks", tasksJson)
                sectionsJson.put(sectionObj)
            }
            parentObj.put("sections", sectionsJson)
            parentsJson.put(parentObj)
        }

        val standaloneJson = JSONArray()
        sections.filter { !it.isDaily && it.parentId == null }.forEach { section ->
            val sectionObj = JSONObject()
            sectionObj.put("title", section.title)
            section.iconKey?.let { sectionObj.put("icon", it) }

            val tasksJson = JSONArray()
            (tasksBySection[section.id] ?: emptyList()).forEach { task ->
                val taskObj = JSONObject()
                taskObj.put("title", task.title)
                if (task.description.isNotEmpty()) taskObj.put("description", task.description)
                tasksJson.put(taskObj)
            }
            sectionObj.put("tasks", tasksJson)
            standaloneJson.put(sectionObj)
        }

        val root = JSONObject()
        root.put("parents", parentsJson)
        root.put("standaloneSections", standaloneJson)
        return root.toString(2)
    }

    data class ImportData(
        val parents: List<ImportParent>,
        val standaloneSections: List<ImportSection>,
    )
    data class ImportParent(
        val title: String,
        val emoji: String?,
        val favourite: Boolean,
        val sections: List<ImportSection>,
    )
    data class ImportSection(val title: String, val icon: String?, val tasks: List<ImportTask>)
    data class ImportTask(
        val title: String,
        val description: String,
        val priority: String?,
        val due: String?,
        val completed: Boolean,
    )

    private fun JSONObject.optStringOrNull(key: String): String? =
        if (isNull(key) || !has(key)) null else optString(key, "").ifBlank { null }

    private fun parseTasks(arr: JSONArray): List<ImportTask> {
        val tasks = mutableListOf<ImportTask>()
        for (k in 0 until arr.length()) {
            val t = arr.getJSONObject(k)
            val title = t.optString("title", "").trim()
            if (title.isEmpty()) continue // skip titleless tasks rather than fail the whole import
            tasks.add(
                ImportTask(
                    title = title,
                    description = t.optString("description", ""),
                    priority = t.optStringOrNull("priority"),
                    due = t.optStringOrNull("due"),
                    completed = t.optBoolean("completed", false),
                )
            )
        }
        return tasks
    }

    private fun parseSection(s: JSONObject): ImportSection =
        ImportSection(
            title = s.getString("title"),
            icon = s.optStringOrNull("icon"),
            tasks = parseTasks(s.optJSONArray("tasks") ?: JSONArray()),
        )

    fun parse(jsonString: String): ImportData {
        val root = JSONObject(jsonString)
        val parents = mutableListOf<ImportParent>()
        val parentsArr = root.optJSONArray("parents") ?: JSONArray()
        for (i in 0 until parentsArr.length()) {
            val p = parentsArr.getJSONObject(i)
            val sectionsArr = p.optJSONArray("sections") ?: JSONArray()
            val sections = (0 until sectionsArr.length()).map { parseSection(sectionsArr.getJSONObject(it)) }
            parents.add(
                ImportParent(
                    title = p.getString("title"),
                    emoji = p.optStringOrNull("emoji"),
                    favourite = p.optBoolean("favourite", false),
                    sections = sections,
                )
            )
        }

        val standaloneArr = root.optJSONArray("standaloneSections") ?: JSONArray()
        val standalone = (0 until standaloneArr.length()).map { parseSection(standaloneArr.getJSONObject(it)) }

        return ImportData(parents, standalone)
    }
}
