package sg.edu.ntu.cz3002.enigma.eclinic.model;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by koAllen on 9/1/2016.
 */
public interface ApiService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("auth-tokens/")
    // TODO: change to Token model
    Observable<ResponseBody> authenticate(@Body User user);
}
