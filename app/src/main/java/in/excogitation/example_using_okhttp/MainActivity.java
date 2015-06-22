package in.excogitation.example_using_okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	ListView lv;
	ArrayAdapter<String> adapter;
	ArrayList<String> data;
	OkHttpClient client;
	String SERVER="http://xxx.xxxx.xxx/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		client = new OkHttpClient();

		lv = (ListView) findViewById(R.id.listView);
		data = new ArrayList<String>();
		data.add("GET Request");
		data.add("POST Request");
		data.add("Cancel Request");

		adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				switch (i) {
					case 0:
						Request req = new Request.Builder().url(SERVER).build();
						Call call = client.newCall(req);
						call.enqueue(new Callback() {
							@Override
							public void onFailure(Request request, IOException e) {
								Log.e("Error", e.toString());
							}

							@Override
							public void onResponse(Response response) throws IOException {
								if (response.isSuccessful()) {
									Log.d("Response", response.body().string());
								}
							}
						});
						break;
					case 1:
						RequestBody formBody = new FormEncodingBuilder()
								.add("key1", "value1")
								.add("key2", "value2")
								.add("key3", "value3")
								.add("key4", "value4")
								.add("key5", "value5")
								.build();
						Request request = new Request.Builder()
								.url(SERVER)
								.post(formBody)
								.build();

						Call call1 = client.newCall(request);
						call1.enqueue(new Callback() {
							@Override
							public void onFailure(Request request, IOException e) {
								Log.e("Error",e.toString());
							}

							@Override
							public void onResponse(Response response) throws IOException {
								if (response.isSuccessful()) {
									Log.d("Response",response.body().string());
								}
							}
						});

						break;
					case 2:

						break;
				}
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
