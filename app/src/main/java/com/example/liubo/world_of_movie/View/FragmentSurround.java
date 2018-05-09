package com.example.liubo.world_of_movie.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.liubo.world_of_movie.Adapter.LocationAdapter;
import com.example.liubo.world_of_movie.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentSurround extends Fragment implements OnGetPoiSearchResultListener {
    private MapView mMapView = null;

    public LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private View view;
    private TextView tv;
    private double latitude;
    private double longitude;
    private float radius;
    private String coorType;
    private int errorCode;
    private List<Poi> poiList;
    private String name33;
    private ArrayList<PoiInfo> dataList;
    private BaiduMap map;
    private MainActivity context = null;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                //  handler.sendMessage(msg);
//              handler.handleMessage(msg);
                locationAdapter.notifyDataSetChanged();
                if (dataList!=null) {
                }
            }
        }
    };
    private ListView lv_location;
    private LocationAdapter locationAdapter;
    private MainActivity activity;
    private Marker markerA;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;

    //导入布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            context = (MainActivity) getContext();
        }
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.fragment_surround, container, false);
        right_add = (ImageView)view.findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)view.findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("周边");
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        map = mMapView.getMap();
        map.setMyLocationEnabled(true);
        lv_location = (ListView) view.findViewById(R.id.lv_location);
        mLocationClient = new LocationClient(getActivity().getApplication());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        showMap(latitude,longitude);
        //注册监听函数
        return view;
    }

    //更新UI
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedLocationPoiList(true);
        mLocationClient.setLocOption(option);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        //option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        searchNeayBy();
        locationAdapter = new LocationAdapter(dataList, activity);
        lv_location.setAdapter(locationAdapter);
    }

    private void searchNeayBy() {
        PoiSearch poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword("电影");
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.location(new LatLng(latitude, longitude));
        if (radius != 0) {
            option.radius(1000);
        } else {
            option.radius(1000);
        }
        option.pageCapacity(20);
        poiSearch.searchNearby(option);
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(getActivity(), "未找到结果",Toast.LENGTH_SHORT).show();
            return;
        }
        List<PoiAddrInfo> allAddr = result.getAllAddr();
        if (allAddr!=null) {
            name33 = allAddr.get(0).name;
        }
        Log.d("wwww", "onGetPoiResult: "+result.getAllAddr());
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
//          mBaiduMap.clear();
            dataList = new ArrayList<>();
            if (result != null) {
                if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                    dataList.addAll(result.getAllPoi());
//                  adapter.notifyDataSetChanged();
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                    locationAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
    }


    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取周边POI信息相关的结果
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            poiList = location.getPoiList();
            if (poiList!=null){
                for (Poi p : poiList) {
                    Log.d("ccc", "周围: "+p.getId() +"  "+p.getName()
                            +"  "+p.getRank());
                }
            }
            //获取周边POI信息
            //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明
            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            radius = location.getRadius();
            coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            //设置缩放中心点；缩放比例；
            builder.target(ll).zoom(18.0f);
            //给地图设置状态
            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            Log.d("cccc", "onReceiveLocation: "+ latitude +
                    "  "+ longitude +"   "+ radius +"   "+ coorType +"   "+ errorCode);
            Log.d("ccc", "onReceiveLocation: "+ poiList.toString());
           // Log.d("ccc", "   : "+location.getCity().toString()+"   "+location.getAddress().toString());

            //画标志
            CoordinateConverter converter = new CoordinateConverter();
            converter.coord(ll);
            converter.from(CoordinateConverter.CoordType.COMMON);
            LatLng convertLatLng = converter.convert();
            OverlayOptions ooA = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.main_xinxiaoxi));
            map.addOverlay(ooA);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
            map.animateMapStatus(u);
            MapStatusUpdate uc = MapStatusUpdateFactory.newLatLng(ll);
            map.animateMapStatus(uc);
            //  mMapView.showZoomControls(false);
            //poi 搜索周边
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Looper.prepare();
                    searchNeayBy();
                    Looper.loop();
                }
            }).start();


        }
    }
    /*
  * 显示经纬度的位置
  * by:hankkin at:2015-05-04
  * */
    private void showMap(double latitude, double longtitude) {
//        sendButton.setVisibility(View.GONE);
        LatLng llA = new LatLng(latitude, longtitude);
        CoordinateConverter converter = new CoordinateConverter();
        converter.coord(llA);
        converter.from(CoordinateConverter.CoordType.COMMON);
        LatLng convertLatLng = converter.convert();
        OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.main_xinxiaoxi))
                .zIndex(4).draggable(true);
        markerA = (Marker) (map.addOverlay(ooA));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 16.0f);
        map.animateMapStatus(u);
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchNeayBy();
            }
        }).start();
    }


}