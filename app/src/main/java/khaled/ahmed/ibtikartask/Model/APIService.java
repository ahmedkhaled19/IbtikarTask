package khaled.ahmed.ibtikartask.Model;

import com.altatawwar.carwash.Model.Responses.CheckCreditCardResponse;
import com.altatawwar.carwash.Model.Responses.ComplainResponse;
import com.altatawwar.carwash.Model.Responses.NearestVehiclesResponse;
import com.altatawwar.carwash.Model.Responses.OrdersResponse;
import com.altatawwar.carwash.Model.Responses.ProfileResponse;
import com.altatawwar.carwash.Model.Responses.ReasonsResponse;
import com.altatawwar.carwash.Model.Responses.TotalPriceResponse;
import com.altatawwar.carwash.Model.Responses.UserRegisterSignUpResponse;
import com.altatawwar.carwash.Model.Responses.VehiclesAndCitiesResponse;
import com.altatawwar.carwash.Model.Responses.successResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by NaderNabil@gmail.com on 20/3/2018.
 */

public interface APIService {

    @GET("vehicles")
    Call<VehiclesAndCitiesResponse> GetVehicles_Cities();

    /**
     * check phone number
     */
    @FormUrlEncoded
    @POST("checkPhone")
    Call<successResponse> checkphone(@Field("phone_number") String phone_number);

    /**
     * update FCM
     */
    @FormUrlEncoded
    @POST("updateFCM")
    Call<successResponse> sendFCM(@Field("user_id") String id,
                                  @Field("fcm_id") String fcm);

    @FormUrlEncoded
    @POST("phoneLogin")
    Call<UserRegisterSignUpResponse> phonelogin(@Field("phone_number") String phone,
                                                @Field("fcm_id") String fcm);


    @FormUrlEncoded
    @POST("buyerRegistration")
    Call<UserRegisterSignUpResponse> UserRegistration(@Field("email") String email,
                                                      @Field("full_name") String full_name,
                                                      @Field("country_code") String country_code,
                                                      @Field("phone_number") String phone_number,
                                                      @Field("city_name") String city_name,
                                                      @Field("modal_name") String modal_name,
                                                      @Field("sub_modal_name") String sub_modal_name,
                                                      @Field("modal_year") String modal_year,
                                                      @Field("vehicle_number") String vehicle_number,
                                                      @Field("gender") String gender,
                                                      @Field("promo_code") String promo_code);

    /**
     * Get ProfileInfo
     */
    @FormUrlEncoded
    @POST("myAccount")
    Call<ProfileResponse> getProfile(@Field("user_id") String id);

    @FormUrlEncoded
    @POST("logout")
    Call<successResponse> logOut(@Field("user_id") String id);

    @FormUrlEncoded
    @POST("updateLanguage")
    Call<successResponse> changeLang(@Field("user_id") String id,
                                     @Field("Lang") String lang);

    @FormUrlEncoded
    @POST("updateUserLocation")
    Call<successResponse> updateUserLocation(@Field("user_id") String user_id,
                                             @Field("user_latitude") String user_latitude,
                                             @Field("user_longitude") String user_longitude);

    @FormUrlEncoded
    @POST("complains")
    Call<ComplainResponse> getComplains(@Field("user_id") String id);

    @FormUrlEncoded
    @POST("availableStatus")
    Call<successResponse> changeDriverAvailable(@Field("user_id") String user_id,
                                                @Field("available_status") String statu);

    @POST("addComplain")
    Call<successResponse> AddComplain(@Field("user_id") String user_id,
                                      @Field("purchasing_id") String purchasing_id,
                                      @Field("details") String details);

    @FormUrlEncoded
    @POST("reasons")
    Call<ReasonsResponse> GetReasons(@Field("user_id") String user_id,
                                     @Field("purchasing_id") String purchasing_id,
                                     @Field("reason_type") String reason_type);

    @FormUrlEncoded
    @POST("cancelPurchasing")
    Call<successResponse> CancelOrder(@Field("user_id") String user_id,
                                      @Field("purchasing_id") String purchasing_id,
                                      @Field("cancel_reason") String cancel_reason);

    @FormUrlEncoded
    @POST("activeNow")
    Call<OrdersResponse> ActiveOrder(@Field("user_id") String user_id,
                                     @Field("page") String page);

    @FormUrlEncoded
    @POST("myPurchasings")
    Call<OrdersResponse> FinishedOrder(@Field("user_id") String user_id,
                                       @Field("page") String page);

    @POST("driverEditProfile")
    Call<successResponse> DriverEditProfile(@Body RequestBody body);

    @POST("editProfile")
    Call<successResponse> UserEditProfile(@Body RequestBody body);

    @POST("driverRegistration")
    Call<UserRegisterSignUpResponse> driverRegistration(@Body RequestBody body);

    @FormUrlEncoded
    @POST("myPoints")
    Call<UserRegisterSignUpResponse> myPoints(@Field("user_id") String id);

    @FormUrlEncoded
    @POST("nearestVehicle")
    Call<NearestVehiclesResponse> GetNearestVehicles(@Field("user_id") String user_id,
                                                     @Field("pickup_lat") String pickup_lat,
                                                     @Field("pickup_long") String pickup_long);

    @FormUrlEncoded
    @POST("getOrderPrice")
    Call<TotalPriceResponse> GetTotalPrice(@Field("car_info") String car_info, @Field("machine_wash") String machine_wash);

    @FormUrlEncoded
    @POST("sendOrder")
    Call<successResponse> CreateOrder(@Field("user_id") String user_id,
                                      @Field("destination_address") String destination_address,
                                      @Field("destination_lat") String destination_lat,
                                      @Field("destination_long") String destination_long,
                                      @Field("purchasing_later_status") String purchasing_later_status,
                                      @Field("payment_type") String payment_type,
                                      @Field("machine_wash") String machine_wash,
                                      @Field("cars_info") String cars_info,
                                      @Field("order_date") String order_date,
                                      @Field("order_time") String order_time,
                                      @Field("test") String test);

    @POST("driverResponse")
    Call<successResponse> driverResponse(@Field("user_id") String id,
                                         @Field("purchasing_id") String pid,
                                         @Field("driver_response") String res);

    @FormUrlEncoded
    @POST("addCreditCard")
    Call<successResponse> addCreditCard(@Field("user_id") String id,
                                        @Field("credit_number") String number,
                                        @Field("expire_date") String date,
                                        @Field("card_name") String name,
                                        @Field("country_name") String country);

    @FormUrlEncoded
    @POST("getCreditCard")
    Call<CheckCreditCardResponse> CheckCreditCard(@Field("user_id") String id);
}
