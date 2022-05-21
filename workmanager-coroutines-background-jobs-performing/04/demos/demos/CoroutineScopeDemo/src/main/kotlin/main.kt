import kotlinx.coroutines.*

fun main(args: Array<String>) {
    runBlocking {
        val scopeJob: Job = Job()
        val scope = CoroutineScope(Dispatchers.Default + scopeJob)
        val coroutine1 = scope.launch {
            delay(500)
            println("I'm in the first coroutine")
        }
        val coroutine2 = scope.launch {
            delay(1000)
            println("I'm in the second coroutine")
        }

        val coroutines = listOf(coroutine1, coroutine2)

        println("List of coroutine jobs")
        for (coroutine in coroutines) {
            println("$coroutine")
        }

        println("Children of CoroutineScope")
        for (child in scopeJob.children) {
            println("$child")
        }

        coroutines.joinAll()

        coroutineScope {
            launch {
                delay(500)
                println("I'm in the coroutineScope builder")
            }
        }
    }

}