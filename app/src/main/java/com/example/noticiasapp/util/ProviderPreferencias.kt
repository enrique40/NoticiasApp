package com.example.noticiasapp.util

import android.content.Context

class ProviderPreferencias(contect: Context) {
    private var prefss = contect.getSharedPreferences(
        Preferencias.DATOS,
        Context.MODE_PRIVATE
    )

    fun setNav(dato: String) {
        val elemento = prefss.edit()
        elemento.putString(Preferencias.Origen, dato).apply()
        elemento.commit()
    }
    fun getNav(): String? {
        val prefName = prefss?.getString(Preferencias.Origen, "")
        return prefName
    }
}