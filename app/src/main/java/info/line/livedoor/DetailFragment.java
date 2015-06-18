package info.line.livedoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailFragment extends Fragment implements AdapterView.OnItemClickListener {

    private int fPos;
    RssAdapter fFeed;
    private ListView listView;
    int count = 0;
    int fPos1 = 0;
    int fPos2 = 0;
    int fPos3 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fFeed = (RssAdapter)getArguments().getSerializable("feed");
        fPos = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        // Initializer views
        TextView title = (TextView)view.findViewById(R.id.title);
        WebView desc = (WebView)view.findViewById(R.id.desc);
        // Enable the vertical fading edge (by default it is disabled)
        ScrollView sv = (ScrollView)view.findViewById(R.id.sv);
        sv.setVerticalFadingEdgeEnabled(true);
        // Set webview properties
        WebSettings ws = desc.getSettings();
        ws.setLightTouchEnabled(false);
        ws.setPluginState(PluginState.ON);
        ws.setJavaScriptEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        ws.setBuiltInZoomControls(true);
        ws.setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
        // Set the views
        title.setText(((RssItem)fFeed.getItem(fPos)).getTitle());
        desc.loadDataWithBaseURL(null, ((RssItem) fFeed.getItem(fPos)).getDescription(), "text/html", "UTF-8", null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        Random randomList = new Random();
        count = fPos1 = fPos2 = fPos3 = 0;
        List<RssItem> relatedItems = new ArrayList<>();
        while(fFeed.getCount()>3 && count<3)
        {
            int relatedPos = randomList.nextInt(fFeed.getCount());
            switch (count){
                case 0:
                    if(relatedPos == fPos)
                        continue;
                    else {
                        fPos1 = relatedPos;
                        count++;
                    }
                    break;
                case 1:
                    if(relatedPos == fPos || relatedPos == fPos1)
                        continue;
                    else{
                        fPos2 = relatedPos;
                        count++;
                    }
                    break;
                case 2:
                    if(relatedPos == fPos || relatedPos == fPos1 || relatedPos == fPos2)
                        continue;
                    else{
                        fPos3 = relatedPos;
                        count++;
                    }
                    break;
            }
            relatedItems.add((RssItem)fFeed.getItem(relatedPos));
        }
        RssAdapter adapter = new RssAdapter(getActivity(), relatedItems);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("feed", fFeed);
        Intent intent = new Intent(getActivity().getApplicationContext(),
                DetailActivity.class);
        intent.putExtras(bundle);
        if(position ==0)
            intent.putExtra("pos", fPos1);
        else if(position ==1)
            intent.putExtra("pos", fPos2);
        else
            intent.putExtra("pos", fPos3);
        startActivity(intent);
    }
}
