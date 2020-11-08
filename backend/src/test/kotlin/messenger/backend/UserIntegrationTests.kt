package messenger.backend

import kotlinx.coroutines.runBlocking
import messenger.backend.repository.UserRepository
import messenger.backend.web.UserHandler
import messenger.shared.model.User
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

@SpringBootTest
@AutoConfigureWebTestClient
class UserIntegrationTests
        @Autowired constructor(val webTestClient: WebTestClient, val repository: UserRepository) {

    @Test
    fun `Find all users`() {
        runBlocking {
            webTestClient.get().uri("/user/").exchange().expectBodyList<User>().contains(
                User("snicoll", "Stéphane", "Nicoll"),
                User("sdeleuze", "Sébastien", "Deleuze"),
                User("bclozel", "Brian", "Clozel")
            )
        }
    }

    @Test
    fun `Find one user`() {
        runBlocking {
            webTestClient.get().uri("/user/bclozel").exchange().expectBody<User>()
                .isEqualTo(User("bclozel", "Brian", "Clozel"))
        }
    }

    @Test
    fun `Find one user with wrong login`() {
        runBlocking {
            webTestClient.get().uri("/user/foo").exchange().expectStatus().isNotFound()
        }
    }

    @Test
    fun `Create user`() {
        runBlocking {
            webTestClient.post().uri("/user/").bodyValue(User("sbasle", "Simon", "Baslé")).exchange().expectStatus()
                .isCreated
            assertNotNull(repository.findOne("sbasle"))
            repository.delete("sbasle")
        }
    }

    @Test
    fun `Create existing user`() {
        runBlocking {
            webTestClient.post().uri("/user/").bodyValue(User("bclozel", "Brian", "Clozel")).exchange().expectStatus()
                .is5xxServerError
        }
    }

    @Test
    fun `Delete user`() {
        runBlocking {
            repository.create(User("sbasle", "Simon", "Baslé"))
            webTestClient.delete().uri("/user/sbasle").exchange().expectStatus().isOk
            assertNull(repository.findOne("sbasle"))
        }
    }
}
