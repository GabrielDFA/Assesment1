package org.d3if3027.assesment1.model

import org.d3if3027.assesment1.db.EntityBmr

fun EntityBmr.calculateBMR() : HasilBMR {
    val bmr: Float
    if (isMale) {
        bmr = (66 + (9.6 * berat) + (1.8 * tinggi) - (4.7 * usia)).toFloat()
    } else {
        bmr = (65 + (13.7 * berat) + (5 * tinggi) - (6.8 * usia)).toFloat()
    }
    return HasilBMR(bmr)
}