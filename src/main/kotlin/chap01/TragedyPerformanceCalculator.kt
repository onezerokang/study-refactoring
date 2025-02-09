package chap01

class TragedyPerformanceCalculator(
    performance: Invoice.Performance,
) : PerformanceCalculator(performance) {
    override fun amount(): Int {
        var result = 40000
        if (this.performance.audience > 30) {
            result += 1000 * (this.performance.audience - 30)
        }
        return result
    }
}