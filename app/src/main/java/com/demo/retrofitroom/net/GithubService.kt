import com.demo.retrofitroom.entity.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}/repos?per_page=200")
    fun getUserRepos(@Path("username") username: String): Call<List<Repo>>
}