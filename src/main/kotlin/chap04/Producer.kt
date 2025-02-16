package chap04

class Producer(aProvince: Province, data: ProvinceData.Producer) {
    val province = aProvince
    val cost = data.cost
    val name = data.name
    var production = data.production

    fun production(amount: Int) {
        this.province.totalProduction += amount - this.production
        this.production = amount
    }
}