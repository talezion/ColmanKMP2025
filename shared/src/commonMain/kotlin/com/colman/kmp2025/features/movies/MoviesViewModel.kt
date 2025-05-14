package com.colman.kmp2025.features.movies

import co.touchlab.kermit.Logger
import com.colman.kmp2025.features.BaseViewModel
import com.colman.kmp2025.models.Movie
import com.colman.kmp2025.models.Movies
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MoviesViewModel: BaseViewModel() {

    private val _uiState: MutableStateFlow<MoviesState> = MutableStateFlow(MoviesState.Loading)
    val uiState: StateFlow<MoviesState> get() = _uiState

    private var log = Logger.withTag("MoviesViewModel")
    private var logflow = Logger.withTag("MoviesViewModel-FLOW")

    init {
        fetchMovies()
//        runCryptoTasks()
//        runFlowExamples()
    }

    /*
•	🔵 Flow (cold)
•	🔴 StateFlow (hot, state holder)
•	🟠 SharedFlow (hot, event emitter)

StateFlow new value: 1
FlowViewModel           com.colman.kmpdemo                   I  Emitting SharedFlow event: SharedFlow event
FlowViewModel           com.colman.kmpdemo                   I  Cold Flow emit: 0
FlowViewModel           com.colman.kmpdemo                   I  Cold Flow collected: 0
FlowViewModel           com.colman.kmpdemo                   I  Cold Flow emit: 1
FlowViewModel           com.colman.kmpdemo                   I  Cold Flow collected: 1
FlowViewModel           com.colman.kmpdemo                   I  Cold Flow emit: 2
FlowViewModel           com.colman.kmpdemo                   I  Cold Flow collected: 2

🔥 In-Class Talking Points: Flow vs StateFlow vs SharedFlow

| Type         | Hot/Cold | Use For                  | Key Property                           |
|--------------|----------|---------------------------|----------------------------------------|
| Flow         | Cold     | Streams, repositories     | Starts fresh for each collector        |
| StateFlow    | Hot      | UI state (e.g., counter)  | Always holds the latest value          |
| SharedFlow   | Hot      | One-time events (e.g., nav/snackbar)
                                  | Emits to all collectors; supports replay/buffer |

👇 Key behaviors to highlight during demo:
- Flow only runs when collected; suspending until then.
- StateFlow needs an initial value, emits immediately to collectors.
- SharedFlow doesn’t hold a value unless replay is used — perfect for events.

✅ Tip: use `collect`, `collectLatest`, or `onEach + launchIn(scope)` depending on use case.
 */

    fun runFlowExamples() {
        // Cold flow: each collect starts from scratch
        scope.launch {
            observeColdFlow().collect { logflow.i("Cold Flow collected: $it") }
        }

        // Delay execution to ensure ViewModel is fully constructed
        scope.launch {
            delay(100)
            incrementStateFlow()
            triggerSharedFlowEvent()
        }
    }

    // 1. Cold Flow Example
    fun observeColdFlow(): Flow<Int> = flow {
        repeat(3) {
            delay(1000)
            logflow.i("Cold Flow emit: $it")
            emit(it)
        }
    }

    // 2. Hot StateFlow Example
    private val _counterState = MutableStateFlow(0)
    val counterState: StateFlow<Int> get() = _counterState

    fun incrementStateFlow() {
        val newValue = _counterState.value + 1
        logflow.i("StateFlow new value: $newValue")
        _counterState.value = newValue
    }

    // 3. Hot SharedFlow Example
    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> get() = _eventFlow

    fun triggerSharedFlowEvent() {
        scope.launch {
            val message = "SharedFlow event"
            logflow.i("Emitting SharedFlow event: $message")
            _eventFlow.emit(message)
        }
    }

    private fun runCryptoTasks() {
        scope.launch {

            val job1 = async {
                log.i("Task 1: Fetching market data...")
                delay(2000)
                log.i("Task 1: Market data fetched")
            }

            val job2 = async {
                job1.await()
                log.i("Task 2: Analyzing data for trends...")
                delay(3000)
                log.i("Task 2: Trend analysis complete")
            }

            val job3 = async {
                job1.await()
                log.i("Task 3: Updating user portfolio...")
                delay(1000)
                log.i("Task 3: User portfolio updated")
            }

            val job4 = async {
                job1.await()
                log.i("Task 4: Fetching user-specific data...")
                delay(5500)
                log.i("Task 4: User-specific data fetched")
            }

            launch {
                job2.await()
                job3.await()
                log.i("Task 5: Generating summary report...")
                delay(1000)
                log.i("Task 5: Summary report generated")
            }

            /*
            Here’s the flow:
          •	job1 → must finish before job2, job3, and job4 start.
          •	job2 and job3 → must finish before Task 5 starts.
          •	job4 → runs after job1, but it’s independent. No one waits for it.

          Expected results:
          Task 1: Fetching market data...
            Task 1: Market data fetched         ← after 2s

            Task 2: Analyzing data for trends...  ← starts after Task 1
            Task 3: Updating user portfolio...
            Task 4: Fetching user-specific data...

            Task 3: User portfolio updated       ← after 1s
            Task 4: User-specific data fetched   ← after 1.5s
            Task 2: Trend analysis complete      ← after 3s

            Task 5: Generating summary report... ← triggered after Task 2 & 3
            Task 5: Summary report generated
             */
        }
    }





    private fun fetchMovies() {
        scope.launch {
            val movies = createMockMoviesData()
            delay(1500)

            _uiState.emit(
                MoviesState.Loaded(movies)
            )
        }

    }
}

private fun createMockMoviesData(): Movies {
    val moviesList = listOf(
        Movie(id = "101", title = "Die Hard", voteCount = 4, posterPath = "some_path"),
        Movie(id = "102", title = "The Shawshank Redemption", voteCount = 9, posterPath = "some_path"),
        Movie(id = "103", title = "Inception", voteCount = 8, posterPath = "some_path"),
        Movie(id = "104", title = "The Dark Knight", voteCount = 10, posterPath = "some_path"),
        Movie(id = "105", title = "Pulp Fiction", voteCount = 7, posterPath = "some_path"),
        Movie(id = "106", title = "The Matrix", voteCount = 9, posterPath = "some_path"),
        Movie(id = "107", title = "Forrest Gump", voteCount = 8, posterPath = "some_path"),
        Movie(id = "108", title = "Fight Club", voteCount = 7, posterPath = "some_path"),
        Movie(id = "109", title = "The Godfather", voteCount = 10, posterPath = "some_path"),
        Movie(id = "110", title = "The Lord of the Rings", voteCount = 9, posterPath = "some_path"),
        Movie(id = "111", title = "Interstellar", voteCount = 8, posterPath = "some_path"),
        Movie(id = "112", title = "Gladiator", voteCount = 7, posterPath = "some_path"),
        Movie(id = "113", title = "Titanic", voteCount = 6, posterPath = "some_path"),
        Movie(id = "114", title = "Braveheart", voteCount = 7, posterPath = "some_path"),
        Movie(id = "115", title = "The Departed", voteCount = 8, posterPath = "some_path"),
        Movie(id = "116", title = "Avatar", voteCount = 6, posterPath = "some_path"),
        Movie(id = "117", title = "Whiplash", voteCount = 9, posterPath = "some_path"),
        Movie(id = "118", title = "Joker", voteCount = 8, posterPath = "some_path"),
        Movie(id = "119", title = "No Country for Old Men", voteCount = 7, posterPath = "some_path"),
        Movie(id = "120", title = "The Prestige", voteCount = 3, posterPath = "some_path9")
    )

    return Movies(items = moviesList)
}