package sg.edu.ntu.cz3002.enigma.eclinic.model;

import java.util.List;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Api definitions
 */
public interface ApiService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("auth-tokens/")
    Observable<AuthToken> authenticate(@Body User user);

    @GET("reservations/")
    Observable<List<Reservation>> getReservation(@Query("patient") String patientName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("users/")
    Observable<User>  signup(@Body User user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("tokens/")
    Observable<ResponseBody> sendMessageToken(@Body MessageToken messageToken);
}
