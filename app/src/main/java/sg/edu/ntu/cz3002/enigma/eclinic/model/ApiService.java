package sg.edu.ntu.cz3002.enigma.eclinic.model;

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
interface ApiService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("auth-tokens/")
    Observable<AuthToken> authenticate(@Body User user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("reservations/")
    Observable<List<Reservation>> getReservation(@Query("patient") String patientName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("reservations/")
    Observable<ResponseBody> sendReservation(@Body Reservation reservation);

    @POST("progresses/")
    Observable<ResponseBody> sendProgress(@Body Progress progress);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("users/")
    Observable<User> signup(@Body User user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("tokens/")
    Observable<ResponseBody> sendMessageToken(@Body MessageToken messageToken);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("doctors/")
    Observable<List<Doctor>> testIdentity(@Query("user") String username);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("messages/")
    Observable<ResponseBody> sendMessage(@Body Message msg);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("progresses/")
    Observable<List<Progress>> getProgress(@Query("patient") String patientName, @Query("doctor") String doctorName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("patients/")
    Observable<Patient> registerAsPatient(@Body Patient patient);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("doctors/")
    Observable<Doctor> registerAsDoctor(@Body Doctor doctor);
}
