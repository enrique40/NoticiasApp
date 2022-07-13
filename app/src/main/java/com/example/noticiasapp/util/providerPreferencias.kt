package com.example.noticiasapp.util

import android.content.Context

class providerPreferencias(contect: Context) {
    var prefss = contect.getSharedPreferences(
        Preferencias.DATOS,
        Context.MODE_PRIVATE
    )

    fun set_Nav(dato: String) {
        val elemento = prefss.edit()
        elemento.putString(Preferencias.Origen.toString(), dato).apply()
        elemento.commit()
    }
    fun get_Nav(): String? {
        var prefName = prefss?.getString(Preferencias.Origen.toString(), "")
        return prefName
    }


    fun pref_limpiarDatossharedPrefer() {
        val elemento = prefss?.edit()
        elemento?.putString(Preferencias.Origen.toString(), "")?.apply()
        elemento?.commit()


    }
}