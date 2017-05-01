package com.notemon.rest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.notemon.helpers.Constants;
import com.notemon.helpers.DocumentHelper;
import com.notemon.models.ImageResponse;
import com.notemon.models.UploadImage;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emil on 01.05.17.
 */

public class UploadService {
    public final static String TAG = UploadService.class.getSimpleName();

    private Context mContext;
    private Activity mActivity;

    public UploadService(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }

    public void buildRestAdapter(UploadImage uploadImage) {

        File file = DocumentHelper.getFile(mContext, uploadImage.uri);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        Map<String, RequestBody> map = new HashMap<>();
        map.put("image", requestBody);


        ImgurService service = RestRetriever.getImageAdapter().create(ImgurService.class);
        Call<ImageResponse> call = service.postImage(Constants.CLIENT_AUTH, map);

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                Log.d(TAG, "Response message: " + response.message()
                        + "\n code: " + response.code()
                        + "\n Error body: " + response.errorBody()
                        + "\n Raw: " + response.raw()
                        + "\n Headers: " + response.headers());

                if (response.body() == null) {
                    Toast.makeText(mActivity, "Not Uploaded! Response Body Null", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Not Uploaded: Response Body Null: ");// + "\n" + response.body().success);
                    return;
                }
                Log.d(TAG, "Uploaded: " + response.body().data.link);// + response.body().data.link);
                Toast.makeText(mActivity, "Uploaded: " + response.body().data.link, Toast.LENGTH_LONG).show();
//                updatePhoto(userId, response.body().data.link);
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(mActivity, "Not Uploaded! Failure", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Not Uploaded: Failure: " + t.getMessage() + "\n" + t.getCause() + "\n" + t.getStackTrace() + "\n" + t.getLocalizedMessage());

            }
        });
    }

    public void updatePhoto(Long userId, String url){
//        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(Constants.userDetailsSharedPreferences, Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString(Constants.token, "");
//
//        RestService service = RestRetriever.getClient().create(RestService.class);
//        UserPhoto photo = new UserPhoto();
//        photo.photoUrl = url;
//        Call<String> call = service.patchPhotoUrl(token, userId, photo);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.body() == null) {
//                    Toast.makeText(mActivity, "Not Updated! Response Body Null", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "Not Updated: Response Body Null: ");// + response.body().status + "\n" + response.body().success);
//                    return;
//                }
//                Log.d(TAG, "Uploaded: " + response.body());// + response.body().data.link);
//                Toast.makeText(mActivity, "Updated Photo Url: " + response.body(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(mActivity, "Not Updated Photo Url! Failure", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Not Updated Photo Url! Failure" + t.getMessage() + "\n" + t.getCause() + "\n" + t.getStackTrace() + "\n" + t.getLocalizedMessage());
//            }
//        });
    }

    public class UserPhoto {
        String photoUrl;
    }
}