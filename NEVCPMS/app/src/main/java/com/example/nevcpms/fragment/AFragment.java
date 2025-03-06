package com.example.nevcpms.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nevcpms.MyDataBaseHelper;
import com.example.nevcpms.R;
import com.example.nevcpms.SearchActivity;
import com.example.nevcpms.adapter.StationAdapter;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.bean.Station;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AFragment extends Fragment {

    private MyDataBaseHelper myDataBaseHelper;
    public View view;
    private ArrayList<Station> stationList = new ArrayList<>();
    private ArrayList<Pile> pileList = new ArrayList<>();
    static boolean flag = true;
    private String phone;
    private LinearLayout searchLayout;

    private RecyclerView recyclerView;
    private int count = 0;

    private NiceSpinner niceSpinner;
    List<String> spinnerData = new LinkedList<>(Arrays.asList("全部行政区", "武昌区", "汉阳区", "江岸区",
            "硚口区", "青山区", "洪山区", "东西湖区", "汉南区", "蔡甸区", "江夏区", "黄陂区", "新洲区", "江汉区"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_a, container, false);
        }


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //获取当前登录的电话号码的信息
        Intent intent = getActivity().getIntent();
        phone = intent.getStringExtra("username");

        //获取到recyclerView实例
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);


        searchLayout = view.findViewById(R.id.go_to_search);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_search = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent_search);
            }
        });


        //初始化stationList
        //先初始化，然后才能传入adapter中，否则传入的是空的值，导致首页列表什么都没有
        //先从数据库里获取所有充电站数据的原始值stationList
        initStations();
        intSpinner(stationList);

        Log.d("测试stationListFinal", String.valueOf(stationList.size()));


        //创建FruitAdapter实例
        StationAdapter adapter = new StationAdapter(stationList, phone);
//        PileAdapter adapter1 = new PileAdapter(pileList);
        recyclerView.setAdapter(adapter);
//        recyclerView.setAdapter(adapter1);


    }


    //初始化站点数据||从数据库中
    private void initStations() {
        myDataBaseHelper = new MyDataBaseHelper(getContext(), "LBS.db", null, 1);

        /**
         *
         * 要做筛选，只需要从初始化数据的地方开始
         * 反正次次都是从初始化数据的时候筛选的角度出发
         * 然后直接套adapter的模板
         * 就能简化很多步骤了
         */
        //充电站数据初始化
        //由于数据库设置，以下数据只会被插入一次，不会重复插入，所以可以放心作为本地数据库数据初始化使用
        myDataBaseHelper.addStation("湖北武大绿洲投资有限公司地下停车场充电站", "s1", "洪山区", "湖北省武汉市洪山区书城路路751号", "1.95000", "3.00", "周一到周五 0:00-23:59", "0.50", "对外开放、空闲多", "13036154805", 114.35045554601551, 30.50633395680806);
        myDataBaseHelper.addStation("黄鹤楼景区新能源汽车充电站", "s2", "武昌区", "湖北省武汉市武昌区黄鹤楼景区停车场内", "2.35000", "5.00", "周一到周五 0:00-23:59", "0.80", "对外开放、景区、交通便利", "13599351356", 114.31479102065836, 30.54793654682339);
        myDataBaseHelper.addStation("长江乐充充电站", "s3", "洪山区", "武汉市洪山区崇文路崇文广场", "0.97550", "5.00", "周一到周日 0:00-24:00", "0.26", "销量王，本地连锁，地上", "000000", 114.34909263689578, 30.516218789040277);
        myDataBaseHelper.addStation("长江乐崇文站新能源汽车融媒体充电站", "s4", "洪山区", "湖北省武汉市洪山区长江出版传媒广场内", "1.27550", "3.00", "周一到周日 6:00-22:00", "0.30", "百亿补贴站点", "000000", 114.3491369233574, 30.516252389609303);
        myDataBaseHelper.addStation("武汉南国大家装江南店充电站", "s6", "洪山区", "湖北省武汉市洪山区珞狮路411珞狮路南国大家装", "1.25000", "3.00", "周一到周日 0:00-24:00", "0.15", "精品站，充电险", "000000", 114.35710695933359, 30.519822927660172);
        myDataBaseHelper.addStation("武汉百瑞景一期充电站", "s7", "武昌区", "湖北省武汉市武昌区瑞景路宝通寺路百瑞景广场", "1.20000", "3.00", "周一到周日 0:00-24:00", "0.15", "对外开放，停车费45元/天封顶", "000000", 114.3410199245998, 30.52661317213556);
        myDataBaseHelper.addStation("武汉红旗欣居A区充电站", "s8", "洪山区", "湖北省武汉市洪山区红旗欣居A区", "0.60670", "3.00", "周一到周日 0:00-24:00", "0.26", "2小时内停车免费，超时正常计费", "000000", 114.31429461703355, 30.518198246995404);
        myDataBaseHelper.addStation("湖北省武汉市武昌区体育街首义充电站", "s10", "武昌区", "湖北省武汉市武昌区体育街南国首义汇地下停车场地下1层", "1.05990", "5.00", "周一到周日 0:00-24:00", "0.38", "大学附近", "000000", 114.3091269349263, 30.54358583111108);
        myDataBaseHelper.addStation("龙泰元汽车4S店内（马自达）充电站", "s11", "洪山区", "武汉市洪山区雄楚大道陈家湾龙泰元马自达", "0.65000", "5.00", "周一到周日 8:00-18:00", "0.25", "停车位紧张", "000000", 114.37042802157161, 30.51459988105874);
        myDataBaseHelper.addStation("武汉邓甲新城充电站", "s12", "汉阳区", "湖北省武汉市汉阳区马鹦路159号马鹦路159号", "1.07550", "以现场为准", "周一到周日 0:00-24:00", "0.26", "半小时内停车免费", "000000", 114.25892692811222, 30.543774106473126);
        myDataBaseHelper.addStation("万达嘉华酒店充电站", "s13", "武昌区", "湖北省武汉市武昌区湖北省武汉市武昌区水果湖街东湖路", "不详", "0.00", "周一到周日 0:00-24:00", "不详", "酒店，充电站数为4", "000000", 114.35688031685342, 30.559088078963626);
        myDataBaseHelper.addStation("湖北省武汉市洪山区徐东大街汪家墩2号充电站", "s14", "洪山区", "湖北省武汉市洪山区徐东大街汪家墩小区旁社会停车场", "0.77550", "5.00", "周一到周日 0:00-24:00", "0.16", "小区旁，桩位足", "000000", 114.35929729765857, 30.590738455357013);
        myDataBaseHelper.addStation("汉口体育文化中心充电站", "s15", "江汉区", "湖北省武汉市江汉区新华路247号（汉口体育文化中心内）", "1.93000", "8.00", "周一到周日 0:00-24:00", "0.00", "体育场", "000000", 114.27609121081355, 30.60749899698128);

        myDataBaseHelper.addStation("青山青少年宫充电站", "s5", "青山区", "湖北省武汉市青山区冶金街道青山青少年宫充电站沿港路与冶金一街交叉口东侧164米", "0.92000", "3.00", "周一到周日 0:00-23:59", "0.49", "快充数量30个", "13036154805", 114.4118055903111, 30.637826360804866);
        myDataBaseHelper.addStation("武汉红钢城街道办充电站", "s9", "青山区", "湖北省武汉市青山区武汉天兴洲长江大桥红钢城街道三环线", "0.88200", "3.00", "周一到周日 0:00-23:59", "0.53", "充电可免费停车2小时", "13036154805", 114.27627934563144, 30.65508680718425);
        myDataBaseHelper.addStation("武汉七星富利天城充电站", "s16", "青山区", "武汉市青山区建设十路", "1.40000", "2.00", "周一到周日 0:00-23:59", "0.12", "21个充电站", "13036154805", 114.41725902340109, 30.655331726705874);
        myDataBaseHelper.addStation("武汉市青山区长廊咖啡厅充电站", "s17", "青山区", "湖北省武汉市青山区工业路19号长廊咖啡厅门口", "1.30000", "未知", "周一到周日 0:00-23:59", "0.30", "停车收费标准见告示", "13036154805", 114.39424120233562, 30.627978822191675);
        myDataBaseHelper.addStation("武汉北洋桥鑫园充电站", "s18", "青山区", "湖北省武汉市洪山区友谊大道杨春湖路交叉口靠南", "1.00000", "5.00", "周一到周日 0:00-23:59", "0.35", "半小时内免费，两小时内5元/度，之后8元/度", "13036154805", 114.4204793749933, 30.61861785480276);
        myDataBaseHelper.addStation("青山武钢厂前充电站", "s19", "青山区", "武汉市青山站前路52号武钢厂前停车场内", "0.85000", "2.00", "周一到周日 0:00-23:59", "0.19", "厂区", "13036154805", 114.4451600332188, 30.623441853729705);
        myDataBaseHelper.addStation("武汉张公山寨风景区-直流桩", "s20", "青山区", "武汉市青山区白玉山街张公山寨严西湖畔", "1.11000", "1.00", "周一到周日 0:00-23:59", "0.44", "景区内", "13036154805", 114.51170596641465, 30.595005900690175);
        myDataBaseHelper.addStation("武汉华侨城欢乐谷充电站", "s21", "青山区", "湖北省武汉市欢乐谷欢乐大道196号", "1.95000", "3.00", "周一到周日 0:00-23:59", "0.50", "，游乐场，空闲多", "13036154805", 114.39808165242476, 30.60000994475479);
        myDataBaseHelper.addStation("武汉格林豪泰高铁充电站", "s22", "青山区", "湖北省武汉市洪山区和谐路", "0.65000", "5.00", "周一到周日 0:00-23:59", "0.80", "凭订单2小时内免费", "13036154805", 114.42994306755789, 30.607912192452336);
        myDataBaseHelper.addStation("团结路丽华苑小区充电站", "s23", "洪山区", "洪山区和平乡园林场鹤园小区内（进小区延主路直走，9栋楼附近）", "1.35000", "2.00", "周一到周日 0:00-23:59", "0.30", "小区，前半小时免费停车", "13036154805", 114.38220606443514, 30.614744892064607);
        myDataBaseHelper.addStation("湖北省武汉市洪山区徐东大街汪家墩2号充电站", "s24", "洪山区", "湖北省武汉市洪山区徐东大街汪家墩小区旁社会停车场", "1.05000", "5.00", "周一到周日 0:00-23:59", "0.14", "参照停车场实际停车费用", "13036154805", 114.35929729765857, 30.590738455357013);
        myDataBaseHelper.addStation("武汉骏业财富中心充电站", "s25", "洪山区", "湖北省武汉市洪山区徐东欢乐大道75号", "1.25000", "3.00", "周一到周日 0:00-23:59", "0.40", "13个慢充", "13036154805", 114.37164019502677, 30.588405595443895);
        myDataBaseHelper.addStation("武汉金谷国际酒店充电站", "s26", "洪山区", "湖北省武汉市洪山区民族大道名族大道307号", "1.00000", "6.00", "周一到周日 0:00-23:59", "0.35", "费用较高", "13036154805", 114.39950697940455, 30.478131928026116);
        myDataBaseHelper.addStation("武汉光谷总部国际充电站", "s27", "洪山区", "湖北省武汉市洪山区光谷大道58号高架桥下", "0.87500", "3.00", "周一到周日 0:00-23:59", "0.40", "车牌识别，1.5小时免费停车", "13036154805", 114.50535578637476, 30.482147287288903);
        myDataBaseHelper.addStation("武汉光谷金盾酒店充电站", "s28", "洪山区", "湖北省武汉市洪山区珞喻路588号珞喻路588号", "0.97000", "4.00", "周一到周日 0:00-23:59", "0.38", "特锐德充电站", "13036154805", 114.39716233195568, 30.517483557313724);
        myDataBaseHelper.addStation("未来之光地面停车场充电站", "s29", "洪山区", "湖北省武汉市东湖新技术开发区光谷大道原楚天激光工业园", "1.35000", "3.00", "周一到周日 0:00-23:59", "0.25", "15分钟内免费停车", "13036154805", 114.49124759740822, 30.50942049930688);
        myDataBaseHelper.addStation("星星充电东湖医院充电站", "s30", "洪山区", "武汉市洪山区东湖东路17号东湖医院停车场", "0.65000", "3.00", "周一到周日 0:00-23:59", "0.30", "绿牌二小时免费停车", "13036154805", 114.40313703197037, 30.542291868466084);
        myDataBaseHelper.addStation("武汉卓刀泉古玩城充电站", "s31", "洪山区", "湖北省武汉市洪山区珞喻路370号珞喻路370号", "0.95000", "3.00", "周一到周日 0:00-23:59", "0.50", "新能源汽车一小时内免费", "13036154805", 114.3781736608314, 30.524559925797888);
        myDataBaseHelper.addStation("武汉汉西美凯龙充电站", "s32", "硚口区", "湖北省武汉市硚口区汉西三路", "0.95000", "2.00", "周一到周日 0:00-23:59", "0.68", "充电免停2小时", "13036154805", 114.22752716206202, 30.603687042498745);
        myDataBaseHelper.addStation("武汉三新材料孵化器公司充电站", "s33", "硚口区", "湖北省武汉市硚口区古田五路17号", "1.25000", "3.00", "周一到周日 0:00-23:59", "0.15", "停车不免费", "13036154805", 114.22630527612768, 30.596436516573746);
        myDataBaseHelper.addStation("武汉创智园充电站", "s34", "硚口区", "湖北省武汉市硚口区长升路10号", "0.55000", "3.00", "周一到周日 0:00-23:59", "0.60", "凭充电订单免费停车2小时", "13036154805", 114.20439071346581, 30.611917686142114);
        myDataBaseHelper.addStation("湖北省武汉市硚口区丰茂路长升路2号充电站", "s35", "硚口区", "湖北省武汉市硚口区丰茂路湖北省电力装备有限公司后院停车场内", "0.65000", "2.50", "周一到周日 0:00-23:59", "0.40", "停车不免费", "13036154805", 114.19970811274072, 30.612977041064628);
        myDataBaseHelper.addStation("武汉长丰城充电站", "s36", "硚口区", "湖北省武汉市硚口区长丰街道长风路长丰城", "0.98900", "1.00", "周一到周日 0:00-23:59", "0.10", "充电费与服务费低", "13036154805", 114.21069097646513, 30.620697877230622);
        myDataBaseHelper.addStation("武汉常码头地铁C口充电站", "s37", "硚口区", "湖北省武汉市硚口区发展大道7号宗关街道淮海路", "0.65000", "1.50", "周一到周日 0:00-23:59", "0.20", "前半小时免费停车", "13036154805", 114.23979962149858, 30.612333805897673);
        myDataBaseHelper.addStation("硚口越秀星汇维港充电站（自营）", "s38", "硚口区", "硚口区中山大道1号星汇维港购物中心B馆负一层", "0.95000", "2.00", "周一到周日 0:00-23:59", "0.20", "按实际场地收费为准", "13036154805", 114.25715214591615, 30.576860601828614);

        myDataBaseHelper.addStation("睿驰达越秀充电站", "s39", "硚口区", "武汉硚口越秀地下停车场", "1.05000", "未知", "周一到周日 0:00-23:59", "0.60", "无", "13036154805", 114.25690601020641, 30.57542592748432);
        myDataBaseHelper.addStation("湖北省武汉市硚口区沿河大道汉正街充电站", "s40", "硚口区", "湖北省武汉市硚口区沿河大道既济电力商城旁地面停车场", "0.95000", "6.00", "周一到周日 0:00-23:59", "0.30", "评价不错", "13036154805", 114.23785785598616, 30.578884470429188);
        myDataBaseHelper.addStation("中山广场地下停车场充电站", "s41", "硚口区", "湖北省武汉市解放大道1171号中山广场地下一层左边", "1.35000", "6.00", "周一到周日 0:00-23:59", "0.60", "15分钟内免费停车", "13036154805", 114.27580177677825, 30.587766519497315);
        myDataBaseHelper.addStation("武汉速泊省交规院充电站", "s42", "汉阳区", "湖北省武汉市汉阳区龙阳大道13号龙阳大道13号", "0.35000", "4.00", "周一到周日 0:00-23:59", "0.46", "快充数量20个", "13036154805", 114.2189192061059, 30.572918628963702);
        myDataBaseHelper.addStation("湖北三环金通汽车有限公司充电站", "s43", "汉阳区", "武汉市汉阳区龙阳大道90号三环金通启辰汉阳专营售后厂房外", "1.15000", "不详", "周一到周日 0:00-23:59", "0.55", "对外开放、空闲多", "13036154805", 114.20528632136434, 30.55686238534636);
        myDataBaseHelper.addStation("湖北省武汉市汉阳区凤举路凤举1号充电站", "s44", "汉阳区", "湖北省武汉市汉阳区凤举路与杨泗港快速路交叉处桥下城投停车场内", "1.05000", "3.00", "周一到周日 0:00-23:59", "0.50", "1小时内免费停车", "13036154805", 114.22544555463595, 30.56005202744345);
        myDataBaseHelper.addStation("武汉汉阳向阳东路充电站", "s45", "汉阳区", "湖北省武汉市汉阳区向阳东路", "1.50000", "2.00", "周一到周日 0:00-23:59", "0.20", "1.5小时免费停车", "13036154805", 114.23544555463595, 30.55005202744345);
        myDataBaseHelper.addStation("武汉锦绣长江充电站", "s46", "汉阳区", "湖北省武汉市汉阳区鹦鹉街街道锦绣三路世茂锦绣长江4期", "0.65000", "2.00", "周一到周日 0:00-23:59", "0.45", "1.5小时免费停车", "13036154805", 114.27483502038841, 30.542085887227802);
        myDataBaseHelper.addStation("汉阳中医院充电站", "s47", "汉阳区", "武汉市汉阳区四新大道汉阳中医院地上停车场", "1.09000", "3.00", "周一到周日 0:00-23:59", "0.30", "1小时内免费停车", "13036154805", 114.22183203089152, 30.525511932626177);
        myDataBaseHelper.addStation("武汉汉阳巍鸿利停车场充电站", "s48", "汉阳区", "湖北省武汉市汉阳区动物园路7号动物园路7号", "0.95000", "8.00", "周一到周日 0:00-23:59", "0.50", "大车小车价格相差较大", "13036154805", 114.25154048439535, 30.558087315362577);
        myDataBaseHelper.addStation("星星充电顶琇西北湖充电站", "s49", "江汉区", "武汉市江汉区北湖横路与西北湖路交叉口西南150米", "1.15000", "5.00", "周一到周日 0:00-23:59", "0.15", "较贵", "13036154805", 114.27144722285252, 30.60603384031043);
        myDataBaseHelper.addStation("武汉南国北都充电站", "s50", "江汉区", "湖北省武汉市江汉区姑嫂树路", "1.20000", "3.00", "周一到周日 0:00-23:59", "0.15", "停车费以现场为准", "13036154805", 114.26757806471589, 30.63770855856802);
        myDataBaseHelper.addStation("武汉蓝天花园充电站", "s51", "江汉区", "湖北省武汉市江汉区振兴路与振兴四路交叉口处", "1.00000", "3.00", "周一到周日 0:00-23:59", "0.53", "停车费15元封顶", "13036154805", 114.24707594284126, 30.609886279423407);
        myDataBaseHelper.addStation("武汉贺家墩高新技术大楼充电站", "s52", "江汉区", "湖北省武汉市江汉区江达路14号江达路14号", "1.04000", "2.00", "周一到周日 0:00-23:59", "0.18", "空闲多", "13036154805", 114.24077980935239, 30.62629715918709);
        myDataBaseHelper.addStation("京山轻机工业园充电站（京山轻机二期）充电站", "s53", "江汉区", "武汉市江汉经济开发区江兴路22号", "0.65000", "不详", "周一到周日 0:00-23:59", "0.30", "对外开放", "13036154805", 114.23476242006325, 30.628255182961748);
        myDataBaseHelper.addStation("易达通充电站（香港路）充电站", "s54", "江汉区", "江汉区香港路248号", "0.95000", "不详", "周一到周日 0:00-23:59", "0.30", "无", "13036154805", 114.2876169942187, 30.613724092717078);
        myDataBaseHelper.addStation("星星充电职防中心充电站", "s55", "江汉区", "武汉市江岸江汉北路18-20号", "1.00000", "3.00", "周一到周日 13:00-23:59", "0.20", "上午不开放", "13036154805", 114.28764126373866, 30.59491038846492);
        myDataBaseHelper.addStation("湖北省武汉市江汉区常青一路先锋变充电站", "s56", "江汉区", "湖北省武汉市江汉区常青一路先锋变电站前", "1.05000", "0.00", "周一到周日 0:00-23:59", "0.14", "免费停车", "13036154805", 114.27033024283224, 30.630703673559296);
        myDataBaseHelper.addStation("市委党校交投充电站", "s57", "江汉区", "湖北省武汉市江汉区长港路305号", "1.25000", "0.00", "周一到周日 0:00-23:59", "0.00", "无停车费，无服务费", "13036154805", 114.25870567097358, 30.637579574183658);
        myDataBaseHelper.addStation("武汉惠园大公馆充电站", "s58", "江岸区", "湖北省武汉市江岸区建设大道959号", "1.25000", "3.50", "周一到周日 0:00-23:59", "0.25", "5个充电站", "13036154805", 114.2587424224486, 30.590218712159498);
        myDataBaseHelper.addStation("民生-武汉-滨江支行充电站", "s59", "江岸区", "湖北省武汉市江岸区湖北省武汉市江岸区芦沟桥路1附1中国民生银行-武汉-滨江支行", "0.00000", "0.00", "周一到周日 0:00-23:59", "0.00", "不对外开放", "13036154805", 114.29402300574264, 30.623792866970447);
        myDataBaseHelper.addStation("中原国际大酒店充电站", "s60", "江岸区", "湖北省武汉市江岸区湖北省武汉市江岸区黄浦大街27号中原国际大酒店", "1.00000", "0.00", "周一到周日 0:00-23:59", "0.00", "酒店", "13036154805", 114.31269338746202, 30.619880371788852);
        myDataBaseHelper.addStation("武汉炎黄图书传媒充电站", "s61", "江岸区", "湖北省武汉市江岸区后湖乡石桥村黄埔科技园特19号（图书城里面）", "1.25000", "2.00", "周一到周日 0:00-23:59", "0.40", "以实际场地费用为准", "13036154805", 114.28686342099756, 30.633832016092533);
        myDataBaseHelper.addStation("后湖东方广场充电站", "s62", "江岸区", "武汉市江岸区金桥大道18-1", "1.25000", "5.00", "周一到周日 0:00-23:59", "0.35", "停车费贵", "13036154805", 114.29361167164961, 30.63957314968806);
        myDataBaseHelper.addStation("武汉优安达科技园充电站", "s63", "东西湖区", "湖北省武汉市东西湖区田园街田园街", "0.95000", "2.00", "周一到周日 0:00-23:59", "0.50", "燃油车不接纳", "13036154805", 114.12464511414025, 30.626045198295976);
        myDataBaseHelper.addStation("九州舜天国际物流园充电站", "s64", "东西湖区", "长青街东吴大道南、十六支沟西新城十七路11号", "1.25000", "1.00", "周一到周日 0:00-23:59", "0.20", "2元停车2小时", "13036154805", 106.16117041350945, 38.642397922622536);
        myDataBaseHelper.addStation("武汉东西湖区额头湾停车场充电站", "s65", "东西湖区", "湖北省武汉市东西湖区东西湖大道6649吴家山街道东西湖大道", "0.95000", "3.00", "周一到周日 0:00-23:59", "0.50", "快充数量20个", "13036154805", 114.16215255547148, 30.624475007881337);
        myDataBaseHelper.addStation("武汉道和物流园充电站", "s66", "东西湖区", "湖北省武汉市东西湖区汇通路", "0.95000", "3.00", "周一到周日 0:00-23:59", "0.50", "快充数量30个", "13036154805", 114.04668528418544, 30.63842300600957);
        myDataBaseHelper.addStation("武汉中军速运走马岭充电站", "s67", "东西湖区", "湖北省武汉市东西湖区走马岭1号路与107国道交汇处", "0.65000", "5.00", "周一到周日 0:00-23:59", "0.50", "前2小时免费停车", "13036154805", 114.03622406197917, 30.648289224919427);
        myDataBaseHelper.addStation("湖北省武汉市东西湖区塔西路径河停车场充电站", "s68", "东西湖区", "湖北省武汉市东西湖区塔西路径河停车场", "1.00000", "0.00", "周一到周日 0:00-23:59", "0.30", "停车免费", "13036154805", 114.17527001946056, 30.685081077736392);
        myDataBaseHelper.addStation("武汉六顺路停车场充电站", "s69", "东西湖区", "湖北省武汉市东西湖区东西湖大道6019", "0.95000", "1.00", "周一到周日 0:00-23:59", "0.50", "前2小时免费停车", "13036154805", 114.13855614570838, 30.623896585776244);
        myDataBaseHelper.addStation("京港澳高速东西湖服务区充电站（北京方向）", "s70", "东西湖区", "湖北省武汉市东西湖区京港澳高速东西湖服务区南侧", "1.95000", "0.00", "周一到周日 0:00-23:59", "0.75", "停车免费", "13036154805", 114.14334447339877, 30.626241455440184);
        myDataBaseHelper.addStation("星星充电朝阳路华航充电站", "s71", "东西湖区", "武汉市东西湖区朝阳路华航友德堆场", "1.09000", "3.00", "周一到周日 0:00-23:59", "0.39", "前2小时免费停车", "13036154805", 114.16020090680186, 30.60928340347001);
        myDataBaseHelper.addStation("武汉自贸城充电站", "s72", "东西湖区", "湖北省武汉市东西湖区高桥二路保税物流管理处高桥二路", "0.95000", "2.00", "周一到周日 0:00-23:59", "0.50", "前1小时免费停车", "13036154805", 114.06629654031632, 30.635673184300984);
        myDataBaseHelper.addStation("武汉中核酒店快速充电站", "s73", "蔡甸区", "湖北省武汉市蔡甸区S104湖北中核国际酒店金荷至尊会所正前方停车场", "0.95000", "4.00", "周一到周日 0:00-23:59", "0.50", "酒店", "13036154805", 114.06588933842865, 30.570358340293858);
        myDataBaseHelper.addStation("武汉蔡甸医院充电站", "s74", "蔡甸区", "湖北省武汉市蔡甸区蔡甸街成功大道与汉江路交汇处", "0.93000", "0.00", "周一到周五 0:00-23:59", "0.60", "停车免费", "13036154805", 114.05055397071168, 30.56654675380772);
        myDataBaseHelper.addStation("湖北省武汉市蔡甸区五贤路后官湖湿地公园充电站", "s75", "蔡甸区", "湖北省武汉市蔡甸区五贤路后官湖湿地公园停车场", "1.50000", "5.00", "周一到周日 0:00-23:59", "0.10", "4个充电站", "13036154805", 114.07995689254545, 30.56651798328649);
        myDataBaseHelper.addStation("武汉蔡甸区城管委充电站", "s76", "蔡甸区", "湖北省武汉市蔡甸区树藩大街390号蔡甸区城管", "0.35000", "0.00", "周一到周五 0:00-23:59", "0.10", "10个慢充", "13036154805", 114.02588199999877, 30.58711889285633);
        myDataBaseHelper.addStation("武汉聚盛现代城充电站", "s77", "蔡甸区", "湖北省武汉市蔡甸区天鹅湖大道湖北省武汉市蔡甸区大集街道天鹅湖大道聚盛现代城", "0.65000", "0.00", "周一到周日 0:00-23:59", "0.40", "对外开放、空闲多", "13036154805", 114.05810004152106, 30.507687909483902);
        myDataBaseHelper.addStation("湖北省武汉市蔡甸区知音湖大道蔡甸区政府充电站", "s78", "蔡甸区", "湖北省武汉市蔡甸区知音湖大道18号蔡甸区政府院外停车场", "1.95000", "3.00", "周一到周六 0:00-23:59", "0.50", "不对外开放", "13036154805", 1114.09387299235325, 30.54240982732303);
        myDataBaseHelper.addStation("武汉花博汇充电站", "s79", "蔡甸区", "湖北省武汉市蔡甸区知音湖大道知音湖大道", "1.95000", "10.00", "周一到周五 0:00-23:59", "0.50", "2小时内免费停车", "13036154805", 114.09280860183247, 30.53463595124536);
        myDataBaseHelper.addStation("普洛斯武汉蔡甸物流园充电站", "s80", "蔡甸区", "湖北省武汉市蔡甸区普洛斯物流园", "0.95000", "3.00", "周一到周日 0:00-23:59", "0.25", "以实际场地费用为准", "13036154805", 114.02545768539115, 30.566487976248833);
        myDataBaseHelper.addStation("武汉蔡甸江滩充电站", "s81", "蔡甸区", "湖北省武汉市蔡甸区蔡甸江滩公园", "0.55000", "0.00", "周一到周日 0:00-23:59", "0.60", "停车免费", "13036154805", 114.03982329727616, 30.596057383751727);
        myDataBaseHelper.addStation("武汉全力北公寓充电站", "s82", "蔡甸区", "湖北省武汉市蔡甸区全力北路全力北路与大全路交叉口", "1.15000", "5.00", "周一到周日 0:00-23:59", "0.13", "2小时内免费停车", "13036154805", 114.08631191984188, 30.469030942307338);
        myDataBaseHelper.addStation("阳关大道普安新村站充电站", "s83", "江夏区", "湖北武汉市江夏区阳光大道鑫凯程汽车服务有限公司", "0.65000", "不详", "周一到周日 0:00-23:59", "0.41", "大学旁", "13036154805", 114.35041979345621, 30.377848279493328);
        myDataBaseHelper.addStation("武汉武威汽车充电站", "s84", "江夏区", "湖北省武汉市江夏区世纪大道13谭鑫培路19号", "1.25000", "0.00", "周一到周日 0:00-23:59", "0.15", "停车免费", "13036154805", 114.33031769241504, 30.375271698365015);
        myDataBaseHelper.addStation("武汉黄家湖大道充电站", "s85", "江夏区", "湖北省武汉市江夏区黄家湖大道", "0.75000", "2.00", "周一到周日 0:00-23:59", "0.50", "1小时内免费停车", "13036154805", 114.2992339089212, 30.415331286977995);
        myDataBaseHelper.addStation("武汉美加湖滨新城充电站", "s86", "江夏区", "湖北省武汉市江夏区经济开发区庙山街道江夏大道71号美加湖滨新城", "0.65000", "3.00", "周一到周日 0:00-23:59", "0.12", "对外开放、空闲多", "13036154805", 114.38358819729278, 30.397488843081298);
        myDataBaseHelper.addStation("七天酒店江夏纸坊店-直流充电站", "s87", "江夏区", "湖北省武汉市江夏区纸坊街熊延弼与武昌大道交汇处七天连锁酒店（纸坊火车站店）地面停车场", "1.15000", "3.00", "周一到周日 0:00-23:59", "0.20", "免费停车3小时", "13036154805", 114.31549628031426, 30.356955268814065);
        myDataBaseHelper.addStation("江夏区纸坊街谦平汽修充电站", "s88", "江夏区", "湖北省武汉市江夏区纸坊街齐心村1层", "0.69900", "不详", "周一到周日 0:00-23:59", "0.40", "对外开放、空闲多", "13036154805", 114.34979915417938, 30.36089551743209);
        myDataBaseHelper.addStation("湖北省武汉市江夏区五里界街小朱湾充电站", "s89", "江夏区", "湖北省武汉市江夏区五里界街小朱湾停车场", "1.15000", "5.00", "周一到周日 0:00-23:59", "0.30", "慢充4个", "13036154805", 114.44450166511054, 30.282888035336597);
        myDataBaseHelper.addStation("武汉风向保标绿地城充电站", "s90", "汉南区", "湖北省武汉市汉南区马影河大道", "0.95000", "0.00", "周一到周日 0:00-23:59", "0.20", "停车免费", "13036154805", 114.08442487460961, 30.35552237557797);
        myDataBaseHelper.addStation("武汉东城垸乘用车充电站", "s91", "汉南区", "湖北省武汉市汉南区东荆街46号", "0.76000", "0.00", "周一到周日 0:00-23:59", "0.60", "停车免费", "13036154805", 113.9261789034632, 30.348610533465816);
        myDataBaseHelper.addStation("大广高速新洲服务区快充站（大庆方向）", "s92", "新洲区", "湖北省武汉市新洲区大广高速新洲服务区东侧", "1.15000", "0.00", "周一到周日 0:00-23:59", "0.75", "服务区", "13036154805", 114.91160292351108, 30.958299582775098);
        myDataBaseHelper.addStation("武汉中慧安顺充电站", "s93", "新洲区", "湖北省武汉市新洲区商业街", "0.45000", "0.00", "周一到周日 0:00-23:59", "0.30", "停车免费", "13036154805", 114.5330040489542, 30.718824402941333);
        myDataBaseHelper.addStation("汉口北工业城充电站", "s94", "黄陂区", "武汉市黄陂区滠口街汉口北工业城12栋102号", "1.15000", "不详", "周一到周日 0:00-23:59", "0.35", "1个慢充", "13036154805", 114.3369766514994, 30.71562599542975);
        myDataBaseHelper.addStation("武汉滠水春晓充电站", "s95", "黄陂区", "湖北省武汉市黄陂区滠阳大街春晓小区", "0.95000", "2.00", "周一到周日 0:00-23:59", "0.50", "小区内", "13036154805", 114.35960047576819, 30.71505845473489);
        myDataBaseHelper.addStation("武汉汉口北萃元充电站", "s96", "黄陂区", "湖北省武汉市黄陂区中环路辅路", "0.45000", "0.00", "周一到周日 0:00-23:59", "0.20", "停车免费", "13036154805", 114.38245399675588, 30.88753376783457);
        myDataBaseHelper.addStation("武汉高新物流园充电站", "s97", "黄陂区", "湖北省武汉市黄陂区S5(武麻高速)", "0.93000", "0.00", "周一到周日 0:00-23:59", "0.30", "停车免费", "13036154805", 114.47676188207652, 30.892316326201037);
        myDataBaseHelper.addStation("黄陂区丽府名苑小区充电站", "s98", "黄陂区", "湖北省武汉市黄陂区盘龙一号丽府名苑小区（小区进门右手边停车场）", "1.25000", "不详", "周一到周日 0:00-23:59", "0.30", "小区内", "13036154805", 114.30825153620684, 30.7057274686209);
        myDataBaseHelper.addStation("创意天地充电站", "s99", "洪山区", "湖北省武汉市洪山区湖北省武汉市洪山区野芷湖西路16号创意天地体验馆", "不详", "不详", "周一到周日 0:00-23:59", "不详", "充电站少", "13036154805", 114.3304640263826, 30.47553794539533);
        myDataBaseHelper.addStation("中南财经政法大学武汉学院充电站", "s100", "洪山区", "湖北省武汉市洪山区湖北省武汉市洪山区雄楚大街中南财经政法大学武汉学院", "不详", "0.00", "周一到周五 0:00-23:59", "不详", "大学内", "13036154805", 114.38559950332963, 30.504530990610817);

         stationList = myDataBaseHelper.getAllStationData();
        for (Station station : stationList) {
            Random random = new Random();//创建Random对象
            int randomInt = random.nextInt(5) + 1;// 生成1到5的随机整数
            for (int i = 0; i < randomInt; i++) {
                // 给每个充电站随机生成1~5个充电桩
                myDataBaseHelper.addPile(station.getId(),  45 + "KW", 0, station.getName().replace("站","桩")+"A100" + i);
                Log.d("addPile", "initStations: " + myDataBaseHelper.getAllPileData());
            }
        }
    }

    public void intSpinner(ArrayList<Station> stationList) {
        ArrayList<Station> tempList = stationList;
        for (int i = 0; i < tempList.size(); i++) {
//            Log.d("TAG", "intSpinner: "+tempList.get(i).toString());
        }

        niceSpinner = view.findViewById(R.id.nice_spinner);
        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner.setTextColor(Color.WHITE);
        niceSpinner.setTextSize(14);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //全部行政区
                        Log.d("测试0", String.valueOf(tempList.size()));
                        Toast.makeText(getContext(), "全部", Toast.LENGTH_SHORT).show();

                        //创建StationAdapter实例
                        StationAdapter adapter0 = new StationAdapter(tempList, phone);
                        recyclerView.setAdapter(adapter0);
                        break;
                    case 1:
                        //武昌区
                        Toast.makeText(getContext(), "武昌区", Toast.LENGTH_SHORT).show();
                        Log.d("测试1", String.valueOf(tempList.size()));
                        ArrayList<Station> list1 = getListFinal("武昌区", tempList);
                        Log.d("测试1", String.valueOf(list1.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter1 = new StationAdapter(list1, phone);
                        recyclerView.setAdapter(adapter1);
                        break;
                    case 2:
                        //汉阳区
                        Toast.makeText(getContext(), "汉阳区", Toast.LENGTH_SHORT).show();
                        Log.d("测试2", String.valueOf(tempList.size()));
                        ArrayList<Station> list2 = getListFinal("汉阳区", tempList);
                        Log.d("测试2", String.valueOf(list2.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter2 = new StationAdapter(list2, phone);
                        recyclerView.setAdapter(adapter2);
                        break;
                    case 3:
                        //江岸区
                        Toast.makeText(getContext(), "江岸区", Toast.LENGTH_SHORT).show();
                        Log.d("测试3", String.valueOf(tempList.size()));
                        ArrayList<Station> list3 = getListFinal("江岸区", tempList);
                        Log.d("测试3", String.valueOf(list3.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter3 = new StationAdapter(list3, phone);
                        recyclerView.setAdapter(adapter3);
                        break;
                    case 4:
                        //硚口区
                        Toast.makeText(getContext(), "硚口区", Toast.LENGTH_SHORT).show();
                        Log.d("测试4", String.valueOf(tempList.size()));
                        ArrayList<Station> list4 = getListFinal("硚口区", tempList);
                        Log.d("测试4", String.valueOf(list4.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter4 = new StationAdapter(list4, phone);
                        recyclerView.setAdapter(adapter4);
                        break;
                    case 5:
                        //青山区
                        Toast.makeText(getContext(), "青山区", Toast.LENGTH_SHORT).show();
                        Log.d("测试5", String.valueOf(tempList.size()));
                        ArrayList<Station> list5 = getListFinal("青山区", tempList);
                        Log.d("测试5", String.valueOf(list5.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter5 = new StationAdapter(list5, phone);
                        recyclerView.setAdapter(adapter5);
                        break;
                    case 6:
                        //洪山区
                        Toast.makeText(getContext(), "洪山区", Toast.LENGTH_SHORT).show();
                        Log.d("测试6", String.valueOf(tempList.size()));
                        ArrayList<Station> list6 = getListFinal("洪山区", tempList);
                        Log.d("测试6", String.valueOf(list6.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter6 = new StationAdapter(list6, phone);
                        recyclerView.setAdapter(adapter6);
                        break;
                    case 7:
                        //东西湖区
                        Toast.makeText(getContext(), "东西湖区", Toast.LENGTH_SHORT).show();
                        Log.d("测试7", String.valueOf(tempList.size()));
                        ArrayList<Station> list7 = getListFinal("东西湖区", tempList);
                        Log.d("测试7", String.valueOf(list7.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter7 = new StationAdapter(list7, phone);
                        recyclerView.setAdapter(adapter7);
                        break;
                    case 8:
                        //汉南区
                        Toast.makeText(getContext(), "汉南区", Toast.LENGTH_SHORT).show();
                        Log.d("测试8", String.valueOf(tempList.size()));
                        ArrayList<Station> list8 = getListFinal("汉南区", tempList);
                        Log.d("测试8", String.valueOf(list8.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter8 = new StationAdapter(list8, phone);
                        recyclerView.setAdapter(adapter8);
                        break;
                    case 9:
                        //蔡甸区
                        Toast.makeText(getContext(), "蔡甸区", Toast.LENGTH_SHORT).show();
                        Log.d("测试9", String.valueOf(tempList.size()));
                        ArrayList<Station> list9 = getListFinal("蔡甸区", tempList);
                        Log.d("测试9", String.valueOf(list9.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter9 = new StationAdapter(list9, phone);
                        recyclerView.setAdapter(adapter9);
                        break;
                    case 10:
                        //江夏区
                        Toast.makeText(getContext(), "江夏区", Toast.LENGTH_SHORT).show();
                        Log.d("测试10", String.valueOf(tempList.size()));
                        ArrayList<Station> list10 = getListFinal("江夏区", tempList);
                        Log.d("测试10", String.valueOf(list10.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter10 = new StationAdapter(list10, phone);
                        recyclerView.setAdapter(adapter10);
                        break;
                    case 11:
                        //黄陂区
                        Toast.makeText(getContext(), "黄陂区", Toast.LENGTH_SHORT).show();
                        Log.d("测试11", String.valueOf(tempList.size()));
                        ArrayList<Station> list11 = getListFinal("黄陂区", tempList);
                        Log.d("测试11", String.valueOf(list11.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter11 = new StationAdapter(list11, phone);
                        recyclerView.setAdapter(adapter11);
                        break;
                    case 12:
                        //新洲区
                        Toast.makeText(getContext(), "新洲区", Toast.LENGTH_SHORT).show();
                        Log.d("测试12", String.valueOf(tempList.size()));
                        ArrayList<Station> list12 = getListFinal("新洲区", tempList);
                        Log.d("测试12", String.valueOf(list12.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter12 = new StationAdapter(list12, phone);
                        recyclerView.setAdapter(adapter12);
                        break;
                    case 13:
                        //江汉区
                        Toast.makeText(getContext(), "江汉区", Toast.LENGTH_SHORT).show();
                        Log.d("测试13", String.valueOf(tempList.size()));
                        ArrayList<Station> list13 = getListFinal("江汉区", tempList);
                        Log.d("测试13", String.valueOf(list13.size()));

                        //创建StationAdapter实例
                        StationAdapter adapter13 = new StationAdapter(list13, phone);
                        recyclerView.setAdapter(adapter13);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //用于从所有充电站数据中选取位于指定行政区的充电站
    public ArrayList<Station> getListFinal(String districtName, ArrayList<Station> tmpList) {
        ArrayList<Station> listFinal = new ArrayList<Station>();
        //实参接收形参
        ArrayList<Station> tempList = tmpList;
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getDistrict().equals(districtName)) {
                listFinal.add(tempList.get(i));
            }
        }
        return listFinal;
    }

    //通过Java反射机制，通过文件名，动态获取到drawable文件夹资源的id
//    public int getDrawableId(String var) {
//        try {
//            String variable=var;
//            Field field = R.drawable.class.getField(variable);
//            int DrawableId = field.getInt(field.getName());
//            return DrawableId;
//        } catch (Exception e) {
//            return 0;
//        }
//    }

}