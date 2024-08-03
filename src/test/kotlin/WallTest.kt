import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class WallTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPostTest() {
        val array = arrayOf(
            AudioAttachment(Audio(1, 2)),
            VideoAttachment(Video(1, "title"))
        )
        val post = Post(
            1,
            2,
            "author",
            null,
            Comment(),
            array,
        )
        WallService.add(post)

        assertEquals(1, WallService.getPosts().size)

    }


    @Test
    fun noUpdateTestId() {
        val array = arrayOf(
            AudioAttachment(Audio(1, 2)),
            VideoAttachment(Video(1, "title"))
        )
        val post = Post(1, 1, "author", null, Comment(), array)
        val postTest = Post(2, 1, "author", null, Comment(), array)

        WallService.add(post)
        val result = WallService.update(postTest)
        assertEquals(false, result)

    }

    @Test
    fun UpdateTestId() {
        val array = arrayOf(
            AudioAttachment(Audio(1, 2)),
            VideoAttachment(Video(1, "title"))
        )
        val post = Post(1, 1, "author", null, Comment(), array)
        val postTest = Post(1, 1, "author", null, Comment(), array)

        WallService.add(post)
        val result = WallService.update(postTest)
        assertEquals(true, result)

    }

    @Test
    fun shouldThrow() {
        val array = arrayOf(
            AudioAttachment(Audio(1, 2)),
            VideoAttachment(Video(1, "title"))
        )
        val post = Post(5, 1, "author", null, Comment(), array)
        val postId = 1
        val comment = Comment(1, "text")
        WallService.add(post)
        val result = WallService.createComment(postId, comment)
        assertEquals(comment, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldNoThrow() {
        val array = arrayOf(
            AudioAttachment(Audio(1, 2)),
            VideoAttachment(Video(1, "title"))
        )
        val post = Post(5, 1, "author", null, Comment(), array)
        val postId = 2
        val comment = Comment(1, "text")
        WallService.add(post)
        WallService.createComment(postId, comment)
    }

}