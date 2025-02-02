package chap01

import java.text.NumberFormat
import java.util.*
import kotlin.math.max

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderPlainText(StatementData(invoice.customer, invoice.performances), plays)
}

fun renderPlainText(data: StatementData, plays: Map<String, Play>): String {
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
        var result = 0
        for (perf: Invoice.Performance in data.performances) {
            result += amountFor(perf)
        }
        return result
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
        var result = 0
        for (perf: Invoice.Performance in data.performances) {
            result += volumeCreditsFor(perf)
        }
        return result
    }

    fun usd(amount: Int): String {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100)
    }

    var result = "청구 내역 (고객명: ${data.customer})\n"

    for (perf: Invoice.Performance in data.performances) {
        // 청구 내역을 출력한다.
        result += " ${playFor(perf).name}: ${usd(amountFor(perf))} (${perf.audience}석)\n"
    }

    result += "총액: ${usd(totalAmount())}\n"
    result += "적립 포인트: ${totalVolumeCredits()}점\n"
    return result
}
