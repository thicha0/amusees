package com.polytech.myapplication
data class Response(
    val nhits: Int,
    val parameters: Parameters,
    val records: List<Record>
)

data class Parameters(
    val dataset: List<String>,
    val format: String,
    val rows: Int,
    val timezone: String
)

data class Record(
    val datasetid: String,
    val fields: Fields,
    val geometry: Geometry,
    val record_timestamp: String,
    val recordid: String
)

data class Fields(
    val adr: String,
    val coordonnees_ban: List<Double>,
    val coordonnees_cp: List<Double>,
    val coordonnees_finales: List<Double>,
    val cp: String,
    val date_appellation: String,
    val fax: String,
    val fermeture_annuelle: String,
    val new_regions: String,
    val nom_du_musee: String,
    val nomdep: String,
    val periode_ouverture: String,
    val ref_musee: String,
    val sitweb: String,
    val telephone1: String,
    val ville: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)