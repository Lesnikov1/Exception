import org.jetbrains.annotations.Nullable

data class Post(
    val id: Int,
    val authorId: Int,
    val authorName: String,
    var likes: Likes? = null,
    var comment: Comment = Comment(),
    val attachment: Array<Attachment>
)

data class Likes(
    var count: Int = 0,
    var canPost: Boolean = true
)

data class Comment(
    var id: Int = 0,
    var text: String = "text"
)

class PostNotFoundException(message: String) : Exception(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var lastId = 0
    private var comments = emptyArray<Comment>()

    fun createComment(postId: Int, comment: Comment): Comment {
        for ((index, fromArrPost) in posts.withIndex()) {
            println(fromArrPost.id )
            println(postId)
            if (fromArrPost.id == postId) {
                comments += comment.copy()
                return comment
            }
        }
        throw PostNotFoundException("Пост с id $postId не найден")
            }



    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId, likes = post.likes?.copy())
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, fromArrPost) in posts.withIndex()) {
            if (fromArrPost.id == post.id) {
                posts[index] = post.copy(likes = post.likes?.copy())
                return true
            }
        }
        return false
    }

    fun getPosts(): Array<Post> {
        return posts
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
        comments = emptyArray<Comment>()
    }

    fun printPosts() {
        for (post in posts) {
            print(post)
            print(" ")
        }
        println()
    }


}

interface Attachment {
    val type: String
}

data class Audio(
    val id: Int,
    val duration: Int,
)

data class AudioAttachment(val audio: Audio) : Attachment {
    override val type: String = "audio"
    override fun toString(): String {
        return "Audio(type='$type', audio ='$audio')"
    }
}

data class Video(
    val duration: Int,
    val title: String
)

class VideoAttachment(val video: Video) : Attachment {
    override val type: String = "video"
    override fun toString(): String {
        return "Video(type='$type', video = '$video')"
    }
}


fun main() {

    val array = arrayOf(
        AudioAttachment(Audio(1, 2)),
        VideoAttachment(Video(1, "title"))
    )

    array.forEach { println(it) }

    val post = Post(5, 1, "author", null, Comment(), array)
    val postId = 5
    val comment = Comment(0, "text")
    WallService.add(post)
    println( WallService.createComment(postId, comment))
}