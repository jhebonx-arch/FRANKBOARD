package com.icecrusher.frankboard

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object NoteRepository {

    private const val PREFS_NAME = "frankboard_notes"
    private const val NOTES_KEY = "notes"

    fun getNotes(context: Context): List<Note> {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val json = preferences.getString(
            NOTES_KEY,
            null
        ) ?: return emptyList()

        return try {

            val array = JSONArray(json)

            buildList {

                for (index in 0 until array.length()) {

                    val item = array.getJSONObject(index)

                    add(
                        Note(
                            id = item.getLong("id"),
                            title = item.getString("title"),
                            text = item.getString("text"),
                            createdAt = item.getLong("createdAt"),
                            updatedAt = item.getLong("updatedAt"),
                            color = item.optString(
                                "color",
                                "yellow"
                            )
                        )
                    )
                }
            }

        } catch (exception: Exception) {

            emptyList()
        }
    }

    fun saveNote(
        context: Context,
        note: Note
    ) {

        val notes = getNotes(context)
            .filterNot { it.id == note.id }
            .toMutableList()

        notes.add(note)

        saveAll(
            context = context,
            notes = notes
        )
    }

    fun deleteNote(
        context: Context,
        noteId: Long
    ) {

        val notes = getNotes(context)
            .filterNot { it.id == noteId }

        saveAll(
            context = context,
            notes = notes
        )
    }

    fun updateNoteColor(
        context: Context,
        noteId: Long,
        color: String
    ) {

        val notes = getNotes(context)
            .map { note ->

                if (note.id == noteId) {
                    note.copy(
                        color = color,
                        updatedAt = System.currentTimeMillis()
                    )
                } else {
                    note
                }
            }

        saveAll(
            context = context,
            notes = notes
        )
    }

    private fun saveAll(
        context: Context,
        notes: List<Note>
    ) {

        val array = JSONArray()

        notes.forEach { note ->

            array.put(
                JSONObject().apply {
                    put("id", note.id)
                    put("title", note.title)
                    put("text", note.text)
                    put("createdAt", note.createdAt)
                    put("updatedAt", note.updatedAt)
                    put("color", note.color)
                }
            )
        }

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                NOTES_KEY,
                array.toString()
            )
            .apply()
    }
}