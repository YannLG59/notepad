package android.training.notepad.utils

import android.content.Context
import android.text.TextUtils
import android.training.notepad.Note
import android.util.Log
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import kotlin.collections.MutableList as MutableList

private val TAG = "storage"

fun persistNote (context: Context, note : Note) {

    if (TextUtils.isEmpty(note.filename)) {
        note.filename = UUID.randomUUID().toString() + ".note"
    }

    val fileOutPut = context.openFileOutput(note.filename, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutPut)
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNotes (context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()

    val notesDir = context.filesDir
    for (filename in notesDir.list()) {
        val note = loadNote(context, filename)
        Log.i(TAG, "Loaded note $note")
        notes.add(note)
    }
    return notes
}

fun deleteNote(context: Context,note: Note) {
    context.deleteFile(note.filename)
}

private fun loadNote(context: Context, filename : String ) : Note {
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()

    return note
}