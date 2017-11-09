package com.quadstingray.openligadb.exceptions

class NoMatchFoundException(message: String = "No Games found Exception", cause: Throwable = null) extends Exception(message, cause)

class NoSeasonFoundException(message: String = "No Season found Exception", cause: Throwable = null) extends Exception(message, cause)

class NoSportFoundException(message: String = "No Sport found Exception", cause: Throwable = null) extends Exception(message, cause)

class NoMatchGroupFoundException(message: String = "No Matchgroup found for Session Exception", cause: Throwable = null) extends Exception(message, cause)
