package chap01

class ComedyPerformanceCalculator(
    performance: Invoice.Performance,
    play: Play,
) : PerformanceCalculator(performance, play) {
    override fun amount(): Int {
        var result = 30000
        if (this.performance.audience > 20) {
            result += 10000 + 500 * (this.performance.audience - 20)
        }
        result += 300 * this.performance.audience
        return result
    }

    override fun volumeCredits(): Int {
        return super.volumeCredits() + performance.audience / 5
    }
}