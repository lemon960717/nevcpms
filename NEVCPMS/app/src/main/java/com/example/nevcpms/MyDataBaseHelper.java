package com.example.nevcpms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nevcpms.bean.Collection;
import com.example.nevcpms.bean.MyAppointment;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.bean.Station;
import com.example.nevcpms.bean.User;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;//数据库变量db

    public static final String CREATE_USER = "create table if not exists USER (" +
            "id integer primary key autoincrement," +
            "phone TEXT," +
            "password TEXT," +
            "UNIQUE(phone))";

    public static final String CREATE_STATION = "create table if not exists STATION(" +
            "id integer primary key autoincrement," +
            "name text," +
            "imageName text," +
            "district text," +
            "location text," +
            "charge_price text," +
            "parking_price text," +
            "open_time text," +
            "service_price text," +
            "introduction text," +
            "service_phone text," +
            "longitude real," +
            "latitude real," +
            "UNIQUE(name))";


    public static final String CREATE_PILE = "create table if not exists PILE (" +
            "id integer primary key autoincrement," +
            "name text," +
            "stationId integer," +
            "pile_power text," +
            "isUsed  int," +
            "UNIQUE(name))";

    public static final String CREATE_COLLECTION = "create table if not exists COLLECTION (" +
            "id integer primary key autoincrement," +
            "phone TEXT," +
            "station_id integer," +
            "isCollected integer)";

    public static final String CREATE_APPOINTMENT = "create table if not exists APPOINTMENT (" +
            "id integer primary key autoincrement," +
            "phone TEXT," +
            "pile_id integer," +
            "star_time TEXT," +
            "end_time  TEXT," +
            "isAppointment integer)";


    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //第二个参数是数据库名，第三个参数是cursor，一般传入null,第四个参数为当前数据库的版本号
        super(context, name, factory, version);
        db = getReadableDatabase();
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_STATION);
        db.execSQL(CREATE_COLLECTION);
        db.execSQL(CREATE_PILE);
        db.execSQL(CREATE_APPOINTMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists station");
        db.execSQL("drop table if exists collection");
        db.execSQL("drop table if exists pile");
        db.execSQL("drop table if exists appoint");

    }

    //自定义的CRUD操作

    //添加用户
    public void add(String phoneNumber, String password) {
        db.execSQL("INSERT INTO USER (phone,password) VALUES(?,?)", new Object[]{phoneNumber, password});
    }

    //添加充电站（初始化时使用）
    public void addStation(String stationName, String imageName, String district, String location, String chargePrice, String parkingPrice, String openTime, String servicePrice, String introduction, String service_phone, double longitude, double latitude) {
        db.execSQL("INSERT OR IGNORE INTO station (name,imageName,district,location,charge_price,parking_price,open_time,service_price,introduction,service_phone,longitude,latitude) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{stationName, imageName, district, location, chargePrice, parkingPrice, openTime, servicePrice, introduction, service_phone, longitude, latitude});
    }

    //添加充电桩
    public void addPile(int stationId, String pilePower, int isUsed, String pileName) {
        db.execSQL("INSERT OR IGNORE INTO pile(stationId,pile_power,isUsed,name) VALUES(?,?,?,?)", new Object[]{stationId, pilePower, isUsed, pileName});
    }

    public String findPasswordByPhone(String phoneNumber) {
        Cursor cursor = db.query("USER", new String[]{"password"}, "phone=?", new String[]{phoneNumber}, null, null, null);
        cursor.moveToFirst();//不能缺少这一步，否则会直接空指针报错
        String pW = cursor.getString(cursor.getColumnIndex("password"));
        return pW;
    }

    //修改密码
    public void updatePassword(String password, String phoneNumber) {
        db.execSQL("UPDATE user SET password = ? WHERE phone = ?", new Object[]{password, phoneNumber});
    }

    //得到数据库中所有用户信息
    public ArrayList<User> getAllUserData() {

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("USER", null, null, null, null, null, "phone DESC");
        while (cursor.moveToNext()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phone"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(phoneNumber, password));
        }
        return list;
    }


    //得到数据库中所有充电站信息
    public ArrayList<Station> getAllStationData() {
        ArrayList<Station> stationArrayList = new ArrayList<Station>();
        Cursor cursor = db.query("station", null, null, null, null, null, "name DESC");

        while (cursor.moveToNext()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("id"));
            String stationName = cursor.getString(cursor.getColumnIndex("name"));
            String imageName = cursor.getString(cursor.getColumnIndex("imageName"));
            Log.d("imageName", imageName);
            String district = cursor.getString(cursor.getColumnIndex("district"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String chargePrice = cursor.getString(cursor.getColumnIndex("charge_price"));
            String parkingPrice = cursor.getString(cursor.getColumnIndex("parking_price"));
            String openTime = cursor.getString(cursor.getColumnIndex("open_time"));
            String servicePrice = cursor.getString(cursor.getColumnIndex("service_price"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
            String servicePhone = cursor.getString(cursor.getColumnIndex("service_phone"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            stationArrayList.add(new Station(stationId, stationName, getDrawableId(imageName), district, location, chargePrice, parkingPrice, openTime, servicePrice, introduction, servicePhone, longitude, latitude));
        }

        cursor.close();
        return stationArrayList;
    }



    //模糊查找充电站信息
    //得到数据库中所有充电站信息
    public ArrayList<Station> getSpecialStationData(String station_name) {
        ArrayList<Station> stationArrayList = new ArrayList<Station>();
        Cursor cursor = db.query("station", null, "name like ?", new String[]{"%" + station_name + "%"}, null, null, "name DESC");

        while (cursor.moveToNext()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("id"));
            String stationName = cursor.getString(cursor.getColumnIndex("name"));
            String imageName = cursor.getString(cursor.getColumnIndex("imageName"));
            Log.d("imageName", imageName);
            String district = cursor.getString(cursor.getColumnIndex("district"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String chargePrice = cursor.getString(cursor.getColumnIndex("charge_price"));
            String parkingPrice = cursor.getString(cursor.getColumnIndex("parking_price"));
            String openTime = cursor.getString(cursor.getColumnIndex("open_time"));
            String servicePrice = cursor.getString(cursor.getColumnIndex("service_price"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
            String servicePhone = cursor.getString(cursor.getColumnIndex("service_phone"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));


            stationArrayList.add(new Station(stationId, stationName, getDrawableId(imageName), district, location, chargePrice, parkingPrice, openTime, servicePrice, introduction, servicePhone, longitude, latitude));
        }

        cursor.close();
        return stationArrayList;
    }

    //获取数据库充电桩信息
    public ArrayList<Pile> getAllPileData() {
        ArrayList<Pile> pileArrayList = new ArrayList<Pile>();
        Cursor cursor = db.query("pile", null, null, null, null, null, "name DESC");

        while (cursor.moveToNext()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("stationId"));
            int pileId = cursor.getInt(cursor.getColumnIndex("id"));
            String pilePower = cursor.getString(cursor.getColumnIndex("pile_power"));
            int isUsed = cursor.getInt(cursor.getColumnIndex("isUsed"));
            String pileName=cursor.getString(cursor.getColumnIndex("name"));
            pileArrayList.add(new Pile(pileId, stationId, pilePower, isUsed,pileName));
        }
        cursor.close();
        return pileArrayList;
    }

    //通过id得到数据库中的某个station充电站信息
    //未必会用到
    //因为上一个方法已经得到了全部station数据，只需要对结果筛选就可以
    public Station getStationById(int station_Id) {
        Station stationItem = null;
        Cursor cursor = db.query("station", null, "id=?", new String[]{String.valueOf(station_Id)}, null, null, "name DESC");
        if (cursor.moveToFirst()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("id"));
            String stationName = cursor.getString(cursor.getColumnIndex("name"));
            String imageName = cursor.getString(cursor.getColumnIndex("imageName"));
            Log.d("imageName", imageName);
            String district = cursor.getString(cursor.getColumnIndex("district"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String chargePrice = cursor.getString(cursor.getColumnIndex("charge_price"));
            String parkingPrice = cursor.getString(cursor.getColumnIndex("parking_price"));
            String openTime = cursor.getString(cursor.getColumnIndex("open_time"));
            String servicePrice = cursor.getString(cursor.getColumnIndex("service_price"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
            String servicePhone = cursor.getString(cursor.getColumnIndex("service_phone"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            stationItem = new Station(stationId, stationName, getDrawableId(imageName), district, location, chargePrice, parkingPrice, openTime, servicePrice, introduction, servicePhone, longitude, latitude);
        }
        cursor.close();
        return stationItem;
    }

    //通过stationId获取某个充电站所有的充电桩信息
    public ArrayList<Pile> getPileByStationId(int station_Id) {
        ArrayList<Pile> pileArrayList = new ArrayList<Pile>();
        Cursor cursor = db.query("pile", null, "stationId=? AND isUsed=?", new String[]{String.valueOf(station_Id), "0"}, null, null, "id DESC");
        while (cursor.moveToNext()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("stationId"));
            int pileId = cursor.getInt(cursor.getColumnIndex("id"));
            String pilePower = cursor.getString(cursor.getColumnIndex("pile_power"));
            int isUsed = cursor.getInt(cursor.getColumnIndex("isUsed"));
            String pileName=cursor.getString(cursor.getColumnIndex("name"));
            pileArrayList.add(new Pile(pileId, stationId, pilePower, isUsed,pileName));
        }
        cursor.close();
        return pileArrayList;
    }

    public Pile
    getPileById(int pile_id) {
        Pile pileItem = null;
        Cursor cursor = db.query("pile", null, "id=?", new String[]{String.valueOf(pile_id)}, null, null, "name DESC");
        if (cursor.moveToFirst()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("stationId"));
            int pileId = cursor.getInt(cursor.getColumnIndex("id"));
            String pilePower = cursor.getString(cursor.getColumnIndex("pile_power"));
            int isUsed = cursor.getInt(cursor.getColumnIndex("isUsed"));
            String pileName=cursor.getString(cursor.getColumnIndex("name"));
            pileItem = new Pile(pileId, stationId, pilePower, isUsed,pileName);
        }
        cursor.close();
        return pileItem;
    }

    //通过Java反射机制，通过文件名，动态获取到drawable文件夹资源的id
    public int getDrawableId(String var) {
        try {
            String variable = var;
            Field field = R.drawable.class.getField(variable);
            return field.getInt(field.getName());
        } catch (Exception e) {
            return 0;
        }
    }


    //收藏表-相关操作
    //添加收藏:用于此前从未收藏过的
    public void addCollection(String phoneNumber, int stationId, int isCollected) {
        db.execSQL("INSERT INTO COLLECTION (phone,station_id,isCollected) VALUES(?,?,?)", new Object[]{phoneNumber, stationId, isCollected});
    }

    //取消（删除）收藏
    public void deleteCollection(String phoneNumber, int stationId) {
        db.execSQL("DELETE FROM COLLECTION WHERE phone=? AND station_id=?", new Object[]{phoneNumber, stationId});
    }

    //更新收藏数据：用于此前收藏过，但是又点击了取消的
    public void updateCollection(int flag, String phone, int stationId) {
        db.execSQL("UPDATE COLLECTION SET isCollected = ? WHERE phone = ? AND station_id = ?", new Object[]{flag, phone, stationId});
    }

    //更新表数据
    public void updatePile(int pileId, int isUsed) {
        db.execSQL("UPDATE pile SET isUsed = ? WHERE id = ?", new Object[]{isUsed, pileId});
    }

    public void findPileByPileId(int PileId) {
        Cursor cursor = db.query("PILE", null, "id=?", new String[]{String.valueOf(PileId)}, null, null, null, "phone DESC");
    }


    //得到COLLECTED表中的所有数据
    public ArrayList<Collection> findAllCollection() {
        ArrayList<Collection> collectionArrayList = new ArrayList<Collection>();
        Cursor cursor = db.query("COLLECTION", null, null, null, null, null, "phone DESC");
        while (cursor.moveToNext()) {
            int stationId = cursor.getInt(cursor.getColumnIndex("station_id"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            int isCollected = cursor.getInt(cursor.getColumnIndex("isCollected"));
            collectionArrayList.add(new Collection(phone, stationId, isCollected));
        }

        cursor.close();
        return collectionArrayList;
    }


    //预约表--相关操作
    //添加预约：用于从未被预约过的
    public void addAppoint(String phoneNumber, int pileId, String starTime, String endTime, int isAppointment) {
        db.execSQL("INSERT INTO APPOINTMENT (phone,pile_id, star_time, end_time,isAppointment) VALUES(?,?,?,?,?)", new Object[]{phoneNumber, pileId, starTime, endTime, isAppointment});
    }

    //取消（删除）预约
    public void deleteAppointment(String phoneNumber, int pileId) {
        db.execSQL("DELETE FROM APPOINTMENT WHERE phone=? AND pile_id=?", new Object[]{phoneNumber, pileId});
    }


    public ArrayList<MyAppointment> findAllAppointment() {
        ArrayList<MyAppointment> appointmentArrayList = new ArrayList<MyAppointment>();
        Cursor cursor = db.query("APPOINTMENT", null, null, null, null, null, "phone DESC");
        while (cursor.moveToNext()) {
            int pileId = cursor.getInt(cursor.getColumnIndex("pile_id"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String starTime = cursor.getString(cursor.getColumnIndex("star_time"));
            String endTime = cursor.getString(cursor.getColumnIndex("end_time"));
            int isAppointment = cursor.getInt(cursor.getColumnIndex("isAppointment"));
            appointmentArrayList.add(new MyAppointment(phone, pileId, starTime, endTime, isAppointment));
        }

        cursor.close();
        return appointmentArrayList;
    }


    //更新预约数据：用于此前预约过，但是又点击了取消的
    public void updateAppointment(int flag, String phone, int pileId) {
        db.execSQL("UPDATE APPOINTMENT SET isAppointment = ? WHERE phone = ? AND pile_id = ?", new Object[]{flag, phone, pileId});
    }


    //获取某个phone收藏的所有充电站的id
    //未必会用到
    //因为上一个方法已经得到了全部collection数据，只需要对结果筛选就可以
    public ArrayList<Integer> getAllStationIdByPhone(String phoneNumber) {
        ArrayList<Integer> stationIds = new ArrayList<Integer>();
        Cursor cursor = db.query("COLLECTION", new String[]{"station_id"}, "phone=?", new String[]{phoneNumber}, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int stationId = cursor.getInt(cursor.getColumnIndex("station_id"));
                stationIds.add(stationId);
            }
        }
        cursor.close();
        return stationIds;
    }

}
