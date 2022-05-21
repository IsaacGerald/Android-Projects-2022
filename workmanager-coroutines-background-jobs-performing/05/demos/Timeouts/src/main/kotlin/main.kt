import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main(args: Array<String>) {
    runBlocking {
        var i = 0
        withTimeout(1000) {
            while (true) {
                delay(100)
                println("$i")
                i += 1
            }
        }
    }
}