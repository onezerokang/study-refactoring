package chap01

import kotlin.math.max

open class PerformanceCalculator(
    val performance: Invoice.Performance,
    val play: Play,
) {

    open fun amount(): Int {
        throw RuntimeException("서브클래스에서 처리하도록 설계되었습니다.")
    }

    open fun volumeCredits(): Int {
        return max(performance.audience - 30, 0)
    }
}
