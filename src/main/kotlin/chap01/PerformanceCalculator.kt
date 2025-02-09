package chap01

import kotlin.math.max

abstract class PerformanceCalculator(
    val performance: Invoice.Performance,
) {

    abstract fun amount(): Int

    open fun volumeCredits(): Int {
        return max(performance.audience - 30, 0)
    }
}
