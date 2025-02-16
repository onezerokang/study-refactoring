package chap04

data class ProvinceData(
    val name: String,
    val demand: Int,
    val price: Int,
    val producers: List<Producer>
) {
    data class Producer(
        val name: String,
        val cost: Int,
        val production: Int,
    )
}

fun sampleProvinceData(): ProvinceData {
    return ProvinceData(
        name = "Asia",
        producers = listOf(
            ProvinceData.Producer(name = "Byzantium", cost = 10, production = 9),
            ProvinceData.Producer(name = "Attalia", cost = 12, production = 10),
            ProvinceData.Producer(name = "Sinope", cost = 10, production = 6),
        ),
        demand = 30,
        price = 20,
    )
}
