package com.br.projekt.internshipproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lista;
    private ImageView avatar;
    private ArrayList<UserModel> users;
    public CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData fc = new fetchData();

        fc.execute("https://reqres.in/api/users?per_page=12");

        adapter = new CustomAdapter(MainActivity.this, new ArrayList<UserModel>());

        lista = findViewById(R.id.lista);
        avatar = findViewById(R.id.avatar);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel user = (UserModel) adapter.getItem(position);
                if(user != null) {
                    Intent i = new Intent(MainActivity.this, UserDetails.class);
                    i.putExtra("Emri", user.getFirstName());
                    i.putExtra("Mbiemri", user.getLastName());
                    i.putExtra("Email", user.getEmail());
                    i.putExtra("Id", user.getId());
                    i.putExtra("Avatar", user.getAvatar());
                    startActivity(i);
                }
            }
        });
    }

    class fetchData extends AsyncTask<String, Void, ArrayList<UserModel>> {

        private ArrayList<UserModel> userModels = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<UserModel> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;


            try {
                URL url = new URL("https://reqres.in/api/users?per_page=12");
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

                JSONObject jsonObject = new JSONObject(forecastJsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalObject = jsonArray.getJSONObject(i);
                    UserModel contactModel = new UserModel();
                    contactModel.setId(finalObject.getInt("id"));
                    contactModel.setFirstName(finalObject.getString("first_name"));
                    contactModel.setLastName(finalObject.getString("last_name"));
                    contactModel.setEmail(finalObject.getString("email"));
                    contactModel.setAvatar(finalObject.getString("avatar"));
                    userModels.add(contactModel);
                }
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
        protected void onPostExecute(ArrayList<UserModel> result) {
            super.onPostExecute(result);
            adapter.updateUsers(result);
            adapter.notifyDataSetChanged();
        }
    }
}
