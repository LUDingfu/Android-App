// Language: java
        package com.example.myapplication;

        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import androidx.activity.EdgeToEdge;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.graphics.Insets;
        import androidx.core.view.ViewCompat;
        import androidx.core.view.WindowInsetsCompat;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

        public class MainActivity extends AppCompatActivity {
            RecyclerView recyclerView;
            List<Item> items;
            private static final String JSON_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
            Adapter adapter;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                EdgeToEdge.enable(this);
                setContentView(R.layout.activity_main);

                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

                recyclerView = findViewById(R.id.itemList);
                items = new ArrayList<>();
                extractItems();
            }

            private void extractItems() {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    JSON_URL,
                    null,
                        response -> {
                            // Log the entire response
                            Log.d("API_RESPONSE", "Raw response: " + response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject itemObject = response.getJSONObject(i);
                                    // Check explicitly for JSON null
                                    String name = "";
                                    if (!itemObject.isNull("name")) {
                                        name = itemObject.getString("name").trim();
                                    }
                                    // Filter out item if name is blank
                                    if (name.isEmpty()) {
                                        continue;
                                    }
                                    Item item = new Item();
                                    item.setId(itemObject.getInt("id"));
                                    item.setListId(itemObject.getInt("listId"));
                                    item.setName(name);
                                    items.add(item);
                                } catch (JSONException e) {
                                    Log.e("JSON_ERROR", "Error parsing item: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }

                            // Log for debugging
                            Log.d("ItemCount", "Valid items count: " + items.size());

                            if (items.isEmpty()) {
                                // Show a message if no items are available after filtering
                                Toast.makeText(MainActivity.this, "No items to display after filtering", Toast.LENGTH_LONG).show();
                                return;
                            }

                            // Sort first by listId then by name
                            Collections.sort(items, (o1, o2) -> {
                                int cmp = o1.getListId().compareTo(o2.getListId());
                                if (cmp == 0) {
                                    // Extract numeric part for natural sorting (Item 1, Item 2, etc.)
                                    try {
                                        String name1 = o1.getName();
                                        String name2 = o2.getName();

                                        if (name1.contains(" ") && name2.contains(" ")) {
                                            // Try to extract number part after "Item "
                                            String num1 = name1.substring(name1.lastIndexOf(" ") + 1);
                                            String num2 = name2.substring(name2.lastIndexOf(" ") + 1);
                                            return Integer.parseInt(num1) - Integer.parseInt(num2);
                                        }
                                    } catch (Exception e) {
                                        // If any parsing error, fall back to string comparison
                                    }
                                    return o1.getName().compareTo(o2.getName());
                                }
                                return cmp;
                            });

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter = new Adapter(getApplicationContext(), items);
                            recyclerView.setAdapter(adapter);
                        },
                        error -> {
                            Log.e("NETWORK_ERROR", "Error: " + error.getMessage());
                            Toast.makeText(MainActivity.this, "Network error: " +
                                            (error.getMessage() != null ? error.getMessage() : "Failed to load data"),
                                    Toast.LENGTH_LONG).show();
                        }
                );

                // Set timeout to avoid long waiting times
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        15000, // 15 seconds timeout
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonArrayRequest);
            }
        }