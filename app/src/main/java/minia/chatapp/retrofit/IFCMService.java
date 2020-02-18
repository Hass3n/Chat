package minia.chatapp.retrofit;

import minia.chatapp.retrofit.WebServiceModels.DataMessage;
import minia.chatapp.retrofit.response.FCMResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    //service for sending message using retrofit
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAy6qreR8:APA91bEy7kvQvKdCMPNF6ujLN4HDGrODb5CMiy0vwaqF0EAIpXXEB6rtKGgpOFDCnFzIwAEVsMtX5gZU1MouEr8s93ca9dFvyOio2bn_xnIu8joNQgJU3MENKZjN05fHEe4xETlQFHCi"
    })
    @POST("fcm/send")
    Call<FCMResponse>sendMessage(@Body DataMessage body);
}
