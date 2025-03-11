package pos

class SalesService {

    def getUniqueNumber() {
        // Get the current time in milliseconds
        def currentTimeMillis = System.currentTimeMillis()

        // Generate a random number to reduce duplication risk
        def randomComponent = new Random().nextInt(1000)  // Random number between 0 and 999

        // Combine the time and the random number to form a unique number
        def uniqueNumber = "${currentTimeMillis}${randomComponent}"

        return uniqueNumber

    }
}
