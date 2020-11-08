package messenger.frontend

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.w3c.dom.EventSource

@ExperimentalCoroutinesApi
fun EventSource.asFlow() = callbackFlow {
	onmessage = {
		offer(it.data as String)
	}
	onerror =  {
		cancel(CancellationException("EventSource failed"))
	}
	awaitClose {
		this@asFlow.close()
	}
}

