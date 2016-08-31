package sg.edu.ntu.cz3002.enigma.eclinic.model;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by koAllen on 9/1/2016.
 */
public class ApiManager {
    private static ApiManager ourInstance = new ApiManager();
    private Retrofit _retrofit;
    private ApiService _apiService;
    private static final String _url = "http://192.168.0.112:8000/api/";

    public static ApiManager getInstance() {
        return ourInstance;
    }

    private ApiManager() {
        _retrofit = new Retrofit.Builder()
                .baseUrl(_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        _apiService = _retrofit.create(ApiService.class);
    }

    // TODO: change to Token model
    public Observable<ResponseBody> authenticate(String username, String password) {
        return _apiService.authenticate(new User(username, password));
    }
}
