import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        flow {
            emit(1)
            emit(2)
            kotlinx.coroutines.delay(600)
            emit(3)
            kotlinx.coroutines.delay(100)
            emit(4)
            kotlinx.coroutines.delay(100)
            emit(5)
        }
            .debounce(500)
            .collect {
                println(it)
            }
    }
}