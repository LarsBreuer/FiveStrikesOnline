//Neu=> Neue Activity

package de.fivestrikes.online;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class RegisterActivity extends Activity {
	
	private final static String REGISTER_API_ENDPOINT_URL = "http://calm-waters-7359.herokuapp.com/api/v1/registrations";
	private SharedPreferences mPreferences;
	private String mUserEmail;
	private String mUserName;
	private String mUserPassword;
	private String mUserPasswordConfirmation;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_register);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
	    
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.register);
	    
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        
	}

	public void registerNewAccount(View button) {
	    EditText userEmailField = (EditText) findViewById(R.id.userEmail);
	    mUserEmail = userEmailField.getText().toString();
	    EditText userNameField = (EditText) findViewById(R.id.userName);
	    mUserName = userNameField.getText().toString();
	    EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
	    mUserPassword = userPasswordField.getText().toString();
	    EditText userPasswordConfirmationField = (EditText) findViewById(R.id.userPasswordConfirmation);
	    mUserPasswordConfirmation = userPasswordConfirmationField.getText().toString();

	    if (mUserEmail.length() == 0 || mUserName.length() == 0 || mUserPassword.length() == 0 || mUserPasswordConfirmation.length() == 0) {
	        // input fields are empty
	        Toast.makeText(this, "Please complete all the fields",
	            Toast.LENGTH_LONG).show();
	        return;
	    } else {
	        if (!mUserPassword.equals(mUserPasswordConfirmation)) {
	            // password doesn't match confirmation
	            Toast.makeText(this, "Your password doesn't match confirmation, check again",
	                Toast.LENGTH_LONG).show();
	            return;
	        } else {
	            // everything is ok!
	            RegisterTask registerTask = new RegisterTask(RegisterActivity.this);
	            registerTask.setMessageLoading("Registering new account...");
	            registerTask.execute(REGISTER_API_ENDPOINT_URL);
	        }
	    }
	}
	
	private class RegisterTask extends UrlJsonAsyncTask {
	    public RegisterTask(Context context) {
	       super(context);
	    }

	    @Override
	    protected JSONObject doInBackground(String... urls) {
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(urls[0]);
	        JSONObject holder = new JSONObject();
	        JSONObject userObj = new JSONObject();
	        String response = null;
	        JSONObject json = new JSONObject();

	        try {
	            try {
	                // setup the returned values in case
	                // something goes wrong
	                json.put("success", false);
	                json.put("info", "Something went wrong. Retry!");
	                // add the users's info to the post params
	                userObj.put("email", mUserEmail);
	                userObj.put("name", mUserName);
	                userObj.put("password", mUserPassword);
	                userObj.put("password_confirmation", mUserPasswordConfirmation);
	                holder.put("user", userObj);
	                StringEntity se = new StringEntity(holder.toString());
	                post.setEntity(se);

	                // setup the request headers
	                post.setHeader("Accept", "application/json");
	                post.setHeader("Content-Type", "application/json");

	                ResponseHandler<String> responseHandler = new BasicResponseHandler();
	                response = client.execute(post, responseHandler);
	                json = new JSONObject(response);

	            } catch (HttpResponseException e) {
	                e.printStackTrace();
	                Log.e("ClientProtocol", "" + e);
	            } catch (IOException e) {
	                e.printStackTrace();
	                Log.e("IO", "" + e);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	            Log.e("JSON", "" + e);
	        }

	        return json;
	    }

	    @Override
	    protected void onPostExecute(JSONObject json) {
	        try {
	            if (json.getBoolean("success")) {
	                // everything is ok
	                SharedPreferences.Editor editor = mPreferences.edit();
	                // save the returned auth_token into
	                // the SharedPreferences
	                editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
	                editor.commit();

	                // launch the HomeActivity and close this one
	                // Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
	                // startActivity(intent);
	                finish();
	            }
	            Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            // something went wrong: show a Toast
	            // with the exception message
	            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
	        } finally {
	            super.onPostExecute(json);
	        }
	    }
	}
	
}