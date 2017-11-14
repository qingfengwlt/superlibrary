package ken.superlibrary.com.appdemo.interfaces;


import ken.superlibrary.com.appdemo.enity.ComBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by PC_WLT on 2017/4/26.
 */

public interface AppAPI {

    public final String BASE_URL_ROOT="https://api.github.com/";
    @GET("word")
    Call<ComBean> getData(@Body ComBean word);

}
