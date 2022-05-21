import kotlinx.coroutines.*
import kotlin.random.Random

fun main(args: Array<String>) {
    runBlocking {
        var i = 0
        var j = 0
        val scope = CoroutineScope(Dispatchers.Default)
        val job1 = scope.launch {
            while (true) {
                delay(Random.nextLong(1, 10) * 10)
                i += 1
            }
        }
        val job2 = scope.launch {
            while (true) {
                delay(Random.nextLong(1, 10) * 10)
                j += 1
            }
        }
        while (true) {
            print("Enter command (current, cancel or exit): ")
            when (readLine()) {
                "current" -> {
                    println("Current value of job1 is $i")
                    println("Current value of job2 is $j")
                }
                "cancel" -> {
                    scope.cancel()
                    println("Final value of job1 was $i")
                    println("Final value of job2 was $j")
                }
                "exit" -> {
                    println("Thank you, come again!")
                    break
                }
                else -> {
                    println("Command must be current, cancel or exit")
                }
            }
        }
    }
}