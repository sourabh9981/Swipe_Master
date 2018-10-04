package com.swipe.shrinkcom.swipe.network;

import com.swipe.shrinkcom.swipe.pojo.HideFriend.HideFriend;
import com.swipe.shrinkcom.swipe.pojo.acceptfriendpojo.AcceptFriendPojo;
import com.swipe.shrinkcom.swipe.pojo.allfriendlist.Frienlist;
import com.swipe.shrinkcom.swipe.pojo.allmessagepojo.GetMessageList;
import com.swipe.shrinkcom.swipe.pojo.connectionpojo.ConnectionStablishPojo;
import com.swipe.shrinkcom.swipe.pojo.hidefriendlist.HideFriendList;
import com.swipe.shrinkcom.swipe.pojo.historypojo.HistoryPojo;
import com.swipe.shrinkcom.swipe.pojo.interestpojo.InterestPojo;
import com.swipe.shrinkcom.swipe.pojo.packagelist.PackageList;
import com.swipe.shrinkcom.swipe.pojo.paymentspojo.PaymentPojo;
import com.swipe.shrinkcom.swipe.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.swipe.pojo.profilepojo.ProfilePojo;
import com.swipe.shrinkcom.swipe.pojo.purchaseitemPojo.PurchaseItemPojo;
import com.swipe.shrinkcom.swipe.pojo.randomnumberpojo.RandomNumberPojo;
import com.swipe.shrinkcom.swipe.pojo.receivethumbsuppojo.ReceiveThumbsupPojo;
import com.swipe.shrinkcom.swipe.pojo.reducepojo.ReduceGemsPojo;
import com.swipe.shrinkcom.swipe.pojo.sendfriend.SendFriendRequest;
import com.swipe.shrinkcom.swipe.pojo.sendfriendlist.Sendfriendlist;
import com.swipe.shrinkcom.swipe.pojo.totalpojo.TotalGems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiCall {
    @FormUrlEncoded
    @POST("webservices.php")
    Call<SignupP> Signup(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<SendFriendRequest> sendfriendlist(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<Frienlist> AllFriendList(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<HideFriend> HideFriend(@FieldMap HashMap<String, String> data);

 @FormUrlEncoded
    @POST("webservices.php")
    Call<HideFriendList> HideFriendList(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<PackageList> Packeges(@FieldMap HashMap<String, String> data);



    @FormUrlEncoded
    @POST("webservices.php")
    Call<PaymentPojo> Payments(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<RandomNumberPojo> CallRandomEmails(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<PurchaseItemPojo> PurchaseItem(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<TotalGems> TotalGems(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<ProfilePojo> Profile(@FieldMap HashMap<String, String> data);



    @FormUrlEncoded
    @POST("webservices.php")
    Call<ProfilePojo> UpdetProfileImage(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<ProfilePojo> UpdetProfileName(@FieldMap HashMap<String, String> data);



    @Multipart
    @POST("webservices.php")
    Call<ProfilePojo> postImage(@Part MultipartBody.Part image,@Part("action") RequestBody action,@Part("user_id") RequestBody userid);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<ReceiveThumbsupPojo> ReceivethumbsupList(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<ReceiveThumbsupPojo> SendThumbsupList(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<ReduceGemsPojo> ReduceGems(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<InterestPojo> AddInterest(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<InterestPojo> getInterest(@FieldMap HashMap<String, String> data);

 @FormUrlEncoded
    @POST("webservices.php")
    Call<HistoryPojo> History(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<Sendfriendlist>SenderLIst(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("webservices.php")
    Call<AcceptFriendPojo>AcceptFriend(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<GetMessageList>getMessageChat(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("webservices.php")
    Call<GetMessageList>sendMessage(@FieldMap HashMap<String, String> data);



   @FormUrlEncoded
   @POST("webservices.php")
   Call<ReceiveThumbsupPojo> AddThumbsup(@FieldMap HashMap<String, String> data);



    @FormUrlEncoded
    @POST("webservices.php")
    Call<ConnectionStablishPojo> ConnectionStable(@FieldMap HashMap<String, String> data);


}//end of the Api Call

