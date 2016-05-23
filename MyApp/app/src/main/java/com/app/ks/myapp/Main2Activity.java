package com.app.ks.myapp;

        import android.app.Activity;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.Bind;
        import butterknife.ButterKnife;
        import butterknife.OnClick;
        import butterknife.OnItemClick;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    @Bind(R.id.text_view_username2)
    TextView mTextViewUsername;
    @Bind(R.id.text_view_password2)
    TextView mTextViewPassword;
    @Bind(R.id.list_view)
    ListView listView;

    private List<Client> clientList;
    private String username;
    private String password;
    private SimpleAdapter clientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle(R.string.title_activity_main_2);

        ButterKnife.bind(this);

        getListWeb();

//        getData();
//        setUiData();
//        initList();
//        initListAdapter();
//        clearDatabase();
    }

    private Callback<List<Client>> getClientListCallback() {
        return new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    //status 2xx
                    clientList = response.body();
                    clientsAdapter = new SimpleAdapter(clientList);
                    listView.setAdapter(clientsAdapter);

                } else {
                    //status 4xx lub 5xx
                    Toast.makeText(Main2Activity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

            }
        };
    }

    private void getListWeb() {

        UserCredentails userCredentails = MyAppApplication.getsInstance().getUserCredentails();
        CRMService crmService = ServiceManager.getCrmService();
        Call<List<Client>> getClientListCall = crmService.getClientList(userCredentails.getmUsername(), userCredentails.getmPassword());

        getClientListCall.enqueue(getClientListCallback());

    }

    public void saveClientList() {
        if(clientList == null || clientList.size() == 0) {
            return;
        }
        for(Client client : clientList) {
            new BaseDao.ClientDao().insertObject(client);
        }
    }

//    @Override
//    protected void onResume() {
//        Eventbus.getDefault().register(this);
//        super.onResume();
//    }

    @OnClick(R.id.floating_action_button)
    protected void onFABAddClientClick() {
        Intent intent = new Intent(this,AddClientActivity.class);
        startActivityForResult(intent, IntentExtras.REQUEST_CODE);
    }

    private void initList(){
        clientList = new ArrayList<>();

        for(long i = 1; i <= 30; i++) {
            Client client = new Client();
         //   client.setId(i);
            client.setName(String.format("Client %s", i));
            client.setAddress("address");
            client.setEmail(String.format("Client%s@ks.com", i));
            client.setPhone("1234556778");
            new BaseDao.ClientDao().insertObject(client);
         //   clientList.add(client);
        }
        clientList = new BaseDao.ClientDao().getObjectList();
        clientsAdapter = new SimpleAdapter(clientList);
        listView.setAdapter(clientsAdapter);
    }

    private void initListAdapter() {
        clientList = new BaseDao.ClientDao().getObjectList();
        clientsAdapter = new SimpleAdapter(clientList);
        listView.setAdapter(clientsAdapter);
    }

    private void clearDatabase() {
        for(int i = 0;i < clientList.size(); i++) {
            new BaseDao.ClientDao().deleteObject(clientList.get(i));
        }
    }

 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(IntentExtras.REQUEST_CODE == requestCode && RESULT_OK == resultCode && data != null) {
            Client client = data.getExtras().getParcelable(IntentExtras.CLIENT);
            new BaseDao.ClientDao().insertObject(client);
            clientList.add(client);
            clientsAdapter.notifyDataSetChanged();
        }
    }


    @OnItemClick(R.id.list_view)
    protected void onListViewClick(int position) {
        Client client = (Client) clientsAdapter.getItem(position);
        Intent intent = new Intent(this, ClientDetailsActivity.class);
        intent.putExtra(IntentExtras.CLIENT, client);
        setResult(Activity.RESULT_OK, intent);
        startActivity(intent);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            username = bundle.getString(IntentExtras.USERNAME);
            password = bundle.getString(IntentExtras.PASSWORD);
        }
    }

    private void setUiData() {
        mTextViewUsername.setText(String.format("%s %s", getResources().getString(R.string.text_username), username));
        mTextViewPassword.setText(String.format("%s %s", getResources().getString(R.string.text_password), password));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_client, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                Intent intent = new Intent(this,AddClientActivity.class);
                startActivityForResult(intent, IntentExtras.REQUEST_CODE);
                break;
        }

        return true;
    }
}
