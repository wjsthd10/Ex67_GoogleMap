package kr.co.song1126.ex67_googlemap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;

    public InfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        //기본 윈도우창을 커스텀 하는 곳

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //내부만 커스텀
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.info_win, null);

        switch (marker.getTitle()){
            case "미래능력개발교육원":
                TextView tv=view.findViewById(R.id.tv);
                tv.setText(marker.getTitle());
                break;
            case "서울":
                tv=view.findViewById(R.id.tv);
                tv.setText(marker.getTitle());
                break;
        }

        return view;
    }
}
