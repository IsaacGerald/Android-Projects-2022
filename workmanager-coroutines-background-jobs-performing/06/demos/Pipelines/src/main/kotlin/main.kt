import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

enum class ClubActivity {
    FITNESS, RECREATION, SOCIAL
}

data class Member(val name: String, val active: Boolean, val activity: ClubActivity)

val checkInScope = CoroutineScope(Dispatchers.IO)

fun main(args: Array<String>) {
    val members = listOf<Member>(
        Member("Adam", true, ClubActivity.FITNESS),
        Member("Bob", false, ClubActivity.FITNESS),
        Member("Carl", true, ClubActivity.RECREATION),
        Member("Don", false, ClubActivity.RECREATION),
        Member("Ellen", true, ClubActivity.FITNESS),
        Member("Frank", true, ClubActivity.SOCIAL),
        Member("Gina", false, ClubActivity.FITNESS),
        Member("Henry", true, ClubActivity.SOCIAL),
        Member("Ivan", true, ClubActivity.FITNESS),
        Member("Jorge", false, ClubActivity.FITNESS),
        Member("Karen", true, ClubActivity.RECREATION),
        Member("Lisa", false, ClubActivity.RECREATION),
        Member("Mike", true, ClubActivity.FITNESS),
        Member("Nancy", true, ClubActivity.SOCIAL),
        Member("Oscar", false, ClubActivity.FITNESS),
        Member("Peter", true, ClubActivity.SOCIAL),
        Member("Richard", true, ClubActivity.FITNESS),
        Member("Steven", true, ClubActivity.SOCIAL),
        Member("Tina", false, ClubActivity.FITNESS),
        Member("Urban", true, ClubActivity.SOCIAL),
    )

    runBlocking {
        val checkInChannel = checkInScope.produce<Member> {
            for (member in members) {
                if (!member.active) {
                    println("${member.name} is not active")
                } else {
                    send(member)
                }
            }
            close()
        }

        val fitnessDeferred = checkInScope.async { processFitness(checkInChannel) }
        val recreationDeferred = checkInScope.async {processRecreation(fitnessDeferred.await())}
        val socialJob = checkInScope.launch {processSocial(recreationDeferred.await())}
        socialJob.join()
    }
}


suspend fun processFitness(memberChannel: ReceiveChannel<Member>): ReceiveChannel<Member> {
    return checkInScope.produce {
        for (member in memberChannel) {
            if (member.activity == ClubActivity.FITNESS) {
                println("${member.name} participating in fitness")
            } else {
                send(member)
            }
        }
    }
}
suspend fun processRecreation(memberChannel: ReceiveChannel<Member>): ReceiveChannel<Member> {
    return checkInScope.produce {
        for (member in memberChannel) {
            if (member.activity == ClubActivity.RECREATION) {
                println("${member.name} participating in recreation")
            } else {
                send(member)
            }
        }
    }
}
suspend fun processSocial(memberChannel: ReceiveChannel<Member>) {
    for (member in memberChannel) {
        if (member.activity == ClubActivity.SOCIAL) {
            println("${member.name} is participating in social")
        } else {
            println("No valid activity for ${member.name}")
        }
    }
}