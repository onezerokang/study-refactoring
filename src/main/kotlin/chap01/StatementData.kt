package chap01

data class StatementData(
    val customer: String,
    val performances: List<Invoice.Performance>
)
