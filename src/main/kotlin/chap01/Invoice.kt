package chap01

data class Invoice(
    val customer: String,
    val performances: List<Performance>,
) {
    data class Performance(
        val playId: String,
        val audience: Int,
    )
}
