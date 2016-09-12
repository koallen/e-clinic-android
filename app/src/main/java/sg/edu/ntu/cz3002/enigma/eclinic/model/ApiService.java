package sg.edu.ntu.cz3002.enigma.eclinic.model;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    Observable<AuthToken> getReservation(@Body String patientName);
}
