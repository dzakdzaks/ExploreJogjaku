package dzikirqu.smk.com.jogjaunity.FragmentKuliner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dzikirqu.smk.com.jogjaunity.Adapters.AdapterKuliner;
import dzikirqu.smk.com.jogjaunity.Gson.GsonKuliner;
import dzikirqu.smk.com.jogjaunity.R;
import dzikirqu.smk.com.jogjaunity.Server.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Minuman extends Fragment {


    @InjectView(R.id.rvMinuman)
    RecyclerView rvMinuman;
    @InjectView(R.id.swipeRefreshMinuman)
    SwipeRefreshLayout swipeRefreshMinuman;
    StringRequest stringRequestMinuman;
    GsonKuliner gsonKuliner;
    RequestQueue requestQueueMinuman;
    @InjectView(R.id.imgErrorMinuman)
    ImageView imgErrorMinuman;

    public Minuman() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minuman, container, false);
        ButterKnife.inject(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMinuman.setLayoutManager(llm);


        swipeRefreshMinuman.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                proses();
            }
        });

        swipeRefreshMinuman.setColorSchemeResources(R.color.colorPrimaryKuliner);


        proses();

        return view;

    }

    private void proses() {

        swipeRefreshMinuman.setRefreshing(true);

        String Url = URL.URL_KULINER_MINUMAN;

        imgErrorMinuman.setVisibility(View.GONE);

        rvMinuman.setVisibility(View.INVISIBLE);

        requestQueueMinuman = Volley.newRequestQueue(getActivity());


        stringRequestMinuman = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                gsonKuliner = gson.fromJson(response, GsonKuliner.class);
                AdapterKuliner adapter = new AdapterKuliner(getActivity(), gsonKuliner.dataKuliner);
                rvMinuman.setAdapter(adapter);


                swipeRefreshMinuman.setRefreshing(false);

                rvMinuman.setVisibility(View.VISIBLE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                imgErrorMinuman.setVisibility(View.VISIBLE);
                rvMinuman.setVisibility(View.GONE);
                swipeRefreshMinuman.setRefreshing(false);


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueueMinuman.add(stringRequestMinuman);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
