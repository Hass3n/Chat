package minia.chatapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWebService {

    public static final String fcmUrl="https://fcm.googleapis.com";
    public static IFCMService getFcmService(){
        return getClient(fcmUrl).create(IFCMService.class);
    }

    private static Retrofit retrofitClient=null;

    /**
     * a method for getiing a retrofit client for networking
     *
     * @param baseUrl a string url to making a connection to api
     * @return retrofitClient object
     */
    private static Retrofit getClient(String baseUrl){
        if(retrofitClient==null){
            retrofitClient=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitClient;
    }
}
