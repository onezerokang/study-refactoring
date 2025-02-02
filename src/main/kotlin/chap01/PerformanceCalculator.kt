package chap01

class PerformanceCalculator(
    val performance: Invoice.Performance,
    val play: Play,
) {

    fun amount(): Int {
        var result: Int

        when (this.play.type) {
            "tragedy" -> { // 비극
                result = 40000
                if (this.performance.audience > 30) {
                    result += 1000 * (this.performance.audience - 30)
                }
            }

            "comedy" -> { // 희극
                result = 30000
                if (this.performance.audience > 20) {
                    result += 10000 + 500 * (this.performance.audience - 20)
                }
                result += 300 * this.performance.audience
            }

            else -> throw RuntimeException("알 수 없는 장르: ${this.play.type}")
        }
        return result
    }
}
