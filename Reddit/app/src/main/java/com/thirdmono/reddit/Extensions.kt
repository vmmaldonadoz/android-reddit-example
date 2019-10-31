package com.thirdmono.reddit

fun <T : Any, O : Any> ifNotNull(objectA: T?, objectB: O?): Pair<T, O>? {
    return when {
        objectA != null && objectB != null -> objectA to objectB
        else -> null
    }
}

fun <A : Any, B : Any> doIfBothAreNotNull(objectA: A?, objectB: B?, function: (A, B) -> Unit) {
    ifNotNull(objectA, objectB)?.let { (a, b) ->
        function(a, b)
    }
}

fun String?.ifNotNullOrBlank(function: (String) -> Unit) {
    val safeObject = this.orEmpty()
    if (safeObject.isNotBlank()) {
        function(safeObject)
    }
}