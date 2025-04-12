package com.abdelmageed.mazadytask.data.remote.response

enum class ErrorStatus(var message: String, val code: Int) {
    SERVER_ERROR("Internal Server Error , we will repair it", 500),
    UNAUTHORIZED("Unauthorized User please confirm your email or password", 401),
    REQUEST_ERROR("Error in request", 400),
    UNKNOWN_ERROR("Unknown error, we will repair it", 0),
    FORBIDDEN("Forbidden, please confirm your email or password", 403),
    NOT_FOUND("Not Found", 404);

    companion object {
        fun getStatusValue(code: Int): ErrorStatus {
            return entries.find { it.code == code } ?: UNKNOWN_ERROR
        }
    }
}