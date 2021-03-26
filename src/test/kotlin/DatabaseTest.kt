import com.winterbe.expekt.should
import dev.wickedev.voca.coroutine.mono.await
import dev.wickedev.voca.infrastructure.repository.UserRepository
import dev.wickedev.voca.infrastructure.table.User
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object DatabaseTest : Spek({
    describe("postgresql create schema") {
        val db = DatabaseContainer(this)
        val userRepository by lazy { db.getRepository(UserRepository::class.java) }

        beforeEachTest {
            db.create()
            db.populate("db/ddl.sql")
        }

        afterEachTest {
            db.destroy()
        }

        it("postgresql create schema") {
            val user = fixture<User>()
            val saved = userRepository.save(user).await()
            saved.should.be.equal(user.copy(id = saved.id))
        }
    }
})
