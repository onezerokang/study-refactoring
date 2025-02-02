package chap01

data class StatementData(
    val customer: String,
    val totalAmount: Int,
    val performances: List<EnrichedPerformance>,
) {
    data class EnrichedPerformance(
        val playId: String,
        val audience: Int,
        val play: Play,
        val amount: Int,
        val volumeCredits: Int,
    )
}
