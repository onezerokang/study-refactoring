package chap01

import java.text.NumberFormat
import java.util.*
import kotlin.math.max

fun createStatementData(invoice: Invoice, plays: Map<String, Play>): StatementData {

    fun playFor(perf: Invoice.Performance): Play {
        return plays[perf.playId]!!
    }

    fun amountFor(aPerformance: Invoice.Performance): Int {
        var result: Int

        when (playFor(aPerformance).type) {
            "tragedy" -> { // 비극
                result = 40000
                if (aPerformance.audience > 30) {
                    result += 1000 * (aPerformance.audience - 30)
                }
            }

            "comedy" -> { // 희극
                result = 30000
                if (aPerformance.audience > 20) {
                    result += 10000 + 500 * (aPerformance.audience - 20)
                }
                result += 300 * aPerformance.audience
            }

            else -> throw RuntimeException("알 수 없는 장르: ${playFor(aPerformance).type}")
        }
        return result
    }

    fun totalAmount(): Int {
        return invoice.performances.fold(0) { total, perf ->
            total + amountFor(perf)
        }
    }

    fun volumeCreditsFor(aPerformance: Invoice.Performance): Int {
        var result = 0
        result += max(aPerformance.audience - 30, 0)
        if ("comedy" == playFor(aPerformance).type) {
            result += aPerformance.audience / 5
        }
        return result
    }

    fun totalVolumeCredits(): Int {
        return invoice.performances.fold(0) { total, perf ->
            total + volumeCreditsFor(perf)
        }
    }

    fun enrichPerformance(aPerformance: Invoice.Performance): StatementData.EnrichedPerformance {
        return StatementData.EnrichedPerformance(
            aPerformance.playId,
            aPerformance.audience,
            playFor(aPerformance),
            amountFor(aPerformance),
            volumeCreditsFor(aPerformance),
        )
    }

    return StatementData(
        invoice.customer,
        totalAmount(),
        totalVolumeCredits(),
        invoice.performances.map { enrichPerformance(it) }
    )
}

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderPlainText(createStatementData(invoice, plays))
}

fun renderPlainText(data: StatementData): String {

    fun usd(amount: Int): String {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100)
    }

    var result = "청구 내역 (고객명: ${data.customer})\n"

    for (perf: StatementData.EnrichedPerformance in data.performances) {
        // 청구 내역을 출력한다.
        result += " ${perf.play.name}: ${usd(perf.amount)} (${perf.audience}석)\n"
    }

    result += "총액: ${usd(data.totalAmount)}\n"
    result += "적립 포인트: ${data.totalVolumeCredits}점\n"
    return result
}
