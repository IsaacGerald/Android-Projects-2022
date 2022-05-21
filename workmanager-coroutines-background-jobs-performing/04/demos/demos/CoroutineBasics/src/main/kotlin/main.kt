import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        launch {
            Thread.sleep(5000)
            println("I'm the first println")
        }
        launch {
            delay(1000)
            println("I'm the middle println")
        }
        println("I'm the last println")
    }
}

