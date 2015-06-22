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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

	ListView lv;
	ArrayAdapter<String> adapter;
	ArrayList<String> data;
	OkHttpClient client;
	final String SERVER = "http://xxx.xxxx.xxx/";

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
						getReq("test");
						break;
					case 1:

						HashMap<String, String> params = new HashMap<String, String>();
						params.put("Key1", "Value1");
						params.put("Key2", "Value2");
						params.put("Key3", "Value3");
						postReq("test", params);
						break;
					case 2:

						break;
				}
			}
		});
	}

	void postReq(String urlHandle, HashMap<String, String> params) {
		FormEncodingBuilder formdata = new FormEncodingBuilder();
		for (String param : params.keySet()) {
			formdata.add(param, params.get(param));
		}
		RequestBody formBody = formdata.build();

		Request request = new Request.Builder()
				.url(SERVER + urlHandle)
				.post(formBody)
				.build();

		Call call1 = client.newCall(request);
		call1.enqueue(new Callback() {
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

	}

	void getReq(String urlHandle) {

		Request req = new Request.Builder().url(SERVER + urlHandle).build();
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
