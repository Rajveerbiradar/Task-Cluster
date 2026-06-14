package com.journeytix.taskcluster.data.export

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract

/* "Download SKILL" — hands an AI the prompt to WRITE a TaskCluster import file.
   A single bundled asset (taskcluster-skill.md) with the icon list baked in, so
   there's no live injection — the prompt can never break at runtime. */
object SkillExport {

    private const val SKILL_ASSET = "taskcluster-skill.md"
    private const val SKILL_FILE = "taskcluster-skill.md"

    /** The bundled SKILL prompt (icon list already baked in). */
    fun readSkillMarkdown(context: Context): String =
        context.assets.open(SKILL_ASSET).bufferedReader().use { it.readText() }

    /** Copies the SKILL prompt to the clipboard for quick paste into an AI chat. */
    fun copySkillToClipboard(context: Context) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(
            ClipData.newPlainText("TaskCluster SKILL", readSkillMarkdown(context))
        )
    }

    /** Writes the SKILL prompt into the user-picked folder (SAF tree picker).
        Returns true on success. Never throws. */
    fun saveSkillFilesToTree(context: Context, treeUri: Uri): Boolean = try {
        val resolver = context.contentResolver
        val parentDocId = DocumentsContract.getTreeDocumentId(treeUri)
        val parentDocUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, parentDocId)
        writeDocument(resolver, parentDocUri, SKILL_FILE, readSkillMarkdown(context))
        true
    } catch (e: Exception) {
        false
    }

    private fun writeDocument(
        resolver: ContentResolver,
        parentDocUri: Uri,
        name: String,
        content: String,
    ) {
        val fileUri = DocumentsContract.createDocument(resolver, parentDocUri, "text/markdown", name)
            ?: return
        resolver.openOutputStream(fileUri)?.use { it.write(content.toByteArray()) }
    }
}
