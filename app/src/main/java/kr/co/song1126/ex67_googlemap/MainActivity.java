package kr.co.song1126.ex67_googlemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    //1. GoogleMap API Livrary 추가하기 [play-services-maps]
    //2. 구글지도 사용에 대한 API 키 발급받기

    //3. 구글지도를 제어하는 객체 참조변수
    GoogleMap GMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SupportMapFragment 안에 있는 GoogleMap객체를 얻어오기
        //우선 xml에 만든 SupportMapFragment를 참조하기
        FragmentManager fragmentManager=getSupportFragmentManager();
        SupportMapFragment mapFragment= (SupportMapFragment)fragmentManager.findFragmentById(R.id.fragment_map);

        //비동기(별도 Thread처럼) 방식으로 지도를 불러오기
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //내 멤버변수에 얻어온 google Map대입
                GMap=googleMap;

                //원하는 좌표 객체 생성
                LatLng seoul=new LatLng(37.56, 126.97);

                //지도에 마커옵션 객체 생성 및 설정
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(seoul);//마커가 생성되는 위치
                markerOptions.title("서울");
                markerOptions.snippet("대한민국의 수도");

                //지도에 마커 추가
                GMap.addMarker(markerOptions);

                //원하는 좌표 위치로 카메라를 이동
                //GMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

                //카메라 이동을 부드럽게 이동하면서 zoom까지 적용
                GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seoul, 20));//zoom은 1~25까지

                //마커 여러개 추가도 가능하다.
                LatLng mrhi=new LatLng(37.5608, 127.0346);
                MarkerOptions markerOptions1=new MarkerOptions();
                markerOptions1.position(mrhi);
                markerOptions1.title("미래능력개발교육원");
                markerOptions1.snippet("http://www.mrhi.or.kr");
                markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.alert));
                markerOptions1.anchor(0.5f,1.0f);

                Marker marker =GMap.addMarker(markerOptions1);//추가된 마커객체를 리턴해준다.

                //마커를 클릭하지 않아도 이미 InfoWindow(정보창)가 보이도록...
                marker.showInfoWindow();

                //지도의 정보창을 클릭했을때 만응하기(GMap에서 제어)
                GMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String title=marker.getTitle();
                        if (title.equals("미래능력개발교육원")){
                            //연결된 교육원 홈페이지로 이동(웹브라우저 실행)
                            Intent intent=new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            Uri uri=Uri.parse("http://www.mrhi.or.kr");
                            intent.setData(uri);

                            startActivity(intent);

                        }else if(title.equals("서울")){

                        }
                    }
                });

                //정보창의 커스텀모양을 만들고 싶다면.
                //정보창을 만들어주는 Adapter객체를 생성해야한다.
                InfoAdapter adapter=new InfoAdapter(MainActivity.this);
                GMap.setInfoWindowAdapter(adapter);


                //카메라 위치 변경
                GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mrhi, 14));

                //줌컨트롤이 보이도록 설정할 수 있다.
                UiSettings settings=GMap.getUiSettings();
                settings.setZoomControlsEnabled(true);

                //내위치 보여주기
                //위치정보제공 퍼미션 작업 필요[동적퍼미션]

                GMap.setMyLocationEnabled(true);


            }
        });

    }

}
