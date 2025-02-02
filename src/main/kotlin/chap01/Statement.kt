package chap01

import java.text.NumberFormat
import java.util.*
import kotlin.math.max

fun statement(invoice: Invoice, plays: Map<String, Play>): String {

    fun playFor(perf: Invoice.Performance): Play {
        return plays[perf.playId]!!
    }

    fun amountFor(play: Play, aPerformance: Invoice.Performance): Int {
        var result: Int

        when (play.type) {
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

            else -> throw RuntimeException("알 수 없는 장르: ${play.type}")
        }
        return result
    }

    var totalAmount = 0
    var volumeCredits = 0
    var result = "청구 내역 (고객명: ${invoice.customer})\n"
    val format = NumberFormat.getCurrencyInstance(Locale.US)

    for (perf: Invoice.Performance in invoice.performances) {
        val play = playFor(perf)
        val thisAmount: Int = amountFor(play, perf)

        // 포인트를 적립한다.
        volumeCredits += max(perf.audience - 30, 0)
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if ("comedy" == play.type) {
            volumeCredits += perf.audience / 5
        }
        // 청구 내역을 출력한다.
        result += " ${play.name}: ${format.format(thisAmount / 100)} (${perf.audience}석)\n"
        totalAmount += thisAmount
    }
    result += "총액: ${format.format(totalAmount / 100)}\n"
    result += "적립 포인트: ${volumeCredits}점\n"
    return result
}
