package com.br.projekt.internshipproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class UserDetails extends AppCompatActivity {
    TextView id;
    TextView firstName;
    TextView lastName;
    TextView email;
    ImageView userAvatar;
    PhotoViewAttacher mAttacher;
    Button prev,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        id = findViewById(R.id.id);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        userAvatar = findViewById(R.id.userAvatar);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        mAttacher = new PhotoViewAttacher(userAvatar);

        firstName.setText(getIntent().getStringExtra("Emri"));
        lastName.setText(getIntent().getStringExtra("Mbiemri"));
        email.setText(getIntent().getStringExtra("Email"));
        id.setText(getIntent().getIntExtra("Id",-1));

        Picasso.get().load(getIntent().getStringExtra("Avatar")).into(userAvatar);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previd = (getIntent().getIntExtra("Id",-1)) - 1;
                if(previd > 0) {
                    fetchUserData fc = new fetchUserData();
                    fc.execute(previd);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextid = (getIntent().getIntExtra("Id",-1)) + 1;
                if(nextid < 12) {
                    fetchUserData fc = new fetchUserData();
                    fc.execute(nextid);
                }
            }
        });

    }

    class fetchUserData extends AsyncTask<Integer, Void, ArrayList<UserModel>> {

        private ArrayList<UserModel> userModels = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<UserModel> doInBackground(Integer... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;


            try {
                URL url = new URL("https://reqres.in/api/users/"+params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();

                JSONObject object = new JSONObject(forecastJsonStr);
                JSONObject finalObject = object.getJSONObject("data");
                    UserModel contactModel = new UserModel();
                    contactModel.setId(finalObject.getInt("id"));
                    contactModel.setFirstName(finalObject.getString("first_name"));
                    contactModel.setLastName(finalObject.getString("last_name"));
                    contactModel.setEmail(finalObject.getString("email"));
                    contactModel.setAvatar(finalObject.getString("avatar"));
                    userModels.add(contactModel);

                return userModels;

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return userModels;
        }

        @Override
        protected void onPostExecute(ArrayList<UserModel> user) {
            super.onPostExecute(user);
            Intent i = new Intent(UserDetails.this,UserDetails.class);
            i.putExtra("Emri", user.get(0).getFirstName());
            i.putExtra("Mbiemri", user.get(0).getLastName());
            i.putExtra("Email", user.get(0).getEmail());
            i.putExtra("Id", user.get(0).getId());
            i.putExtra("Avatar", user.get(0).getAvatar());
            startActivity(i);
        }
    }
}
