package com.example.agapayph;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AgapayPH.db";
    private static final int DATABASE_VERSION = 3;

    /*
    * NAMING SCHEME:
    * for Tables = TABLE_(NAME OF TABLE)
    * for Columns = PK(if primary key FK if foreign)_(table name)_(column name)
    * */

    //=====================================================================
    //USERS TABLE
    public static final String TABLE_USERS = "users";
    public static final String PK_USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_FULL_NAME = "full_name";
    public static final String USERS_CONTACT_NUMBER = "contact_number";
    public static final String USERS_ADDRESS = "address";
    public static final String USERS_ROLE = "role";
    //=====================================================================

    //=====================================================================
    //INCIDENTS TABLE
    public static final String TABLE_INCIDENTS = "incidents";
    public static final String PK_INCIDENTS_ID = "incident_id";
    public static final String INCIDENTS_TITLE = "incident_title";
    public static final String INCIDENTS_CATEGORY = "category";
    public static final String INCIDENTS_DESCRIPTION = "description";
    public static final String INCIDENTS_NO_OF_AFFECTED_INDIV = "num_of_affected_indiv";
    public static final String INCIDENTS_BARANGAY = "barangay";
    public static final String INCIDENTS_DATE_AND_TIME = "date_and_time";
    public static final String INCIDENTS_SEVERITY_LEVEL = "severity_level";
    public static final String INCIDENTS_PHOTO_PLACEHOLDER = "photo";
    public static final String INCIDENTS_PRIORITY_CATEGORY = "priority";
    public static final String INCIDENTS_COORDINATE_LATITUDE = "coordinate_latitude";//first coordinate (x),z
    public static final String INCIDENTS_COORDINATE_LONGITUDE = "coordinate_longitude";//second coordinate x,(z)
    //=====================================================================

    //=====================================================================
    //EVACUATION CENTERS TABLE
    public static final String TABLE_EVACUATION_CENTERS = "evacuation_centers";
    public static final String PK_EVACUATION_CENTER_NAME = "evacuation_name";//ex. elementary school etc.
    public static final String EVACUATION_CENTER_ADDRESS = "address";
    public static final String EVACUATION_CENTER_CAPACITY = "capacity";
    public static final String EVACUATION_CENTER_CURRENT_OCCUPANCY = "current_occupancy";
    //=====================================================================

    //=====================================================================
    //RELIEF RECORDS TABLE
    public static final String TABLE_RELIEF_RECORDS = "relief_records";
    public static final String PK_RELIEF_RECORD_ID = "relief_id";
    public static final String RELIEF_RECORD_BENEFICIARY_NAME = "beneficiary_name";
    public static final String RELIEF_RECORD_BARANGAY = "barangay";
    public static final String RELIEF_RECORD_RELIEF_TYPE = "relief_type";
    public static final String RELIEF_RECORD_QUANTITY = "quantity";
    public static final String RELIEF_RECORD_DISTRIBUTION_DATE = "distribution_date";
    public static final String FK_RELIEF_RECORD_VOLUNTEER_ID = "volunteer_id";
    //=====================================================================

    //=====================================================================
    //VOLUNTEERS TABLE
    public static final String TABLE_VOLUNTEERS = "volunteers";
    public static final String PK_VOLUNTEER_ID = "volunteer_id";
    public static final String FK_VOLUNTEER_USERNAME = "username";
    //=====================================================================

    //=====================================================================
    //ASSIGNMENTS TABLE
    public static final String TABLE_ASSIGNMENTS = "assignments";
    public static final String PK_ASSIGNMENT_ID = "assignment_id";
    public static final String FK_ASSIGNMENT_VOLUNTEER_ID = "volunteer_id";//possible to update? possible need reassignment to other volunteer?
    public static final String ASSIGNMENT_TITLE = "assignment_title";
    public static final String ASSIGNMENT_COMPLETION_STATUS = "assignment_status"; //can be null? since volunteer can decline the assignment
    public static final String ASSIGNMENT_IS_ACCEPTED = "isAccepted";//boolean if volunteer accept or no
    //=====================================================================

    //=====================================================================
    //MISSING PERSONS TABLE
    public static final String TABLE_MISSING_PERSONS = "missing_persons";
    public static final String PK_MISSING_PERSON_ID = "missing_person_id";
    public static final String MISSING_PERSON_NAME = "full_name";
    public static final String MISSING_PERSON_AGE = "age";
    public static final String MISSING_PERSON_DESCRIPTION = "description";
    public static final String MISSING_PERSON_LAST_LOCATION = "last_location";
    public static final String MISSING_PERSON_DATE_MISSING = "date_missing";
    public static final String MISSING_PERSON_STATUS = "status";//if still missing or not
    //=====================================================================

    //=====================================================================
    //NOTIFICATIONS TABLE
    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String PK_NOTIFICATION_ID = "notification_id";
    public static final String FK_NOTIFICATION_INCIDENT_ID = "incident_id";//can be null
    public static final String FK_NOTIFICATION_MISSING_ID = "missing_id";//can be null
    public static final String FK_NOTIFICATION_INVENTORY_ID = "inventory_id";//can be null
    public static final String FK_NOTIFICATION_ASSIGNMENT_ID = "assignment_id";//can be null
    public static final String NOTIFICATION_DESCRIPTION = "description";//message of notification
    public static final String NOTIFICATION_IS_SHOWN = "isShown";//boolean -- remove or change if needed
    //=====================================================================

    //=====================================================================
    //INVENTORY TABLE
    public static final String TABLE_INVENTORY = "inventory";
    public static final String PK_INVENTORY_ID = "inventory_id";
    public static final String INVENTORY_ITEM_NAME = "item_name";
    public static final String INVENTORY_QUANTITY = "quantity";
    public static final String FK_INVENTORY_EVACUATION_CENTER_NAME = "evacuation_name";
    //=====================================================================

    //=====================================================================
    //ACTIVITY LOGS TABLE
    public static final String TABLE_ACTIVITY_LOGS = "activity_logs";
    public static final String PK_ACTIVITY_LOG_ID = "activity_id";
    public static final String FK_ACTIVITY_USERNAME = "username";//user who did the activity
    public static final String ACTIVITY_LOG_DESCRIPTION = "description";
    public static final String ACTIVITY_LOG_DATE_AND_TIME = "date_and_time";
    //=====================================================================

    SQLiteDatabase database;



    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS = "CREATE TABLE "+ TABLE_USERS + "(" +
                PK_USERS_USERNAME + " TEXT PRIMARY KEY, " +
                USERS_PASSWORD + " TEXT, " +
                USERS_FULL_NAME + " TEXT UNIQUE, " + //your final call if unique is needed for full name and contact number
                USERS_CONTACT_NUMBER + " TEXT UNIQUE, " + //for +63, 09 etc. to be possible --add validation pls to validate that only number is typed
                USERS_ADDRESS + " TEXT, " +
                USERS_ROLE + " TEXT" +
                ")";

        String CREATE_INCIDENTS = "CREATE TABLE "+ TABLE_INCIDENTS + "(" +
                PK_INCIDENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INCIDENTS_TITLE + " TEXT, " +
                INCIDENTS_CATEGORY + " TEXT, " +
                INCIDENTS_DESCRIPTION + " TEXT, " +
                INCIDENTS_NO_OF_AFFECTED_INDIV + " INTEGER, " +
                INCIDENTS_BARANGAY + " TEXT, " +
                INCIDENTS_DATE_AND_TIME + " TEXT, " + //no built-in date datatype
                INCIDENTS_SEVERITY_LEVEL + " TEXT, " +//low moderate high critical
                INCIDENTS_PHOTO_PLACEHOLDER + " BLOB, " +
                INCIDENTS_PRIORITY_CATEGORY + " TEXT, " +
                INCIDENTS_COORDINATE_LATITUDE + " REAL, " +//float data type
                INCIDENTS_COORDINATE_LONGITUDE + " REAL" +//float data type
                ")";

        String CREATE_EVACUATION_CENTERS = "CREATE TABLE "+ TABLE_EVACUATION_CENTERS + "(" +
                PK_EVACUATION_CENTER_NAME + " TEXT PRIMARY KEY, " +
                EVACUATION_CENTER_CAPACITY + " INTEGER, " +
                EVACUATION_CENTER_CURRENT_OCCUPANCY + " INTEGER, " +
                EVACUATION_CENTER_ADDRESS + " TEXT" +
                ")";

        String CREATE_VOLUNTEERS = "CREATE TABLE "+ TABLE_VOLUNTEERS + "(" +
                PK_VOLUNTEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FK_VOLUNTEER_USERNAME + " TEXT REFERENCES " + TABLE_USERS +
                    "(" + PK_USERS_USERNAME + ")" +
                ")";

        String CREATE_RELIEF_RECORDS = "CREATE TABLE "+ TABLE_RELIEF_RECORDS + "(" +
                PK_RELIEF_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RELIEF_RECORD_BENEFICIARY_NAME + " TEXT, " +
                RELIEF_RECORD_BARANGAY + " TEXT, " +
                RELIEF_RECORD_RELIEF_TYPE + " TEXT, " +
                RELIEF_RECORD_QUANTITY + " INTEGER, " +
                RELIEF_RECORD_DISTRIBUTION_DATE + " TEXT, " +//no built-in date datatype
                FK_RELIEF_RECORD_VOLUNTEER_ID + " TEXT REFERENCES " +
                    TABLE_VOLUNTEERS + "(" + PK_VOLUNTEER_ID + ")" +
                ")";

        String CREATE_ASSIGNMENT = "CREATE TABLE "+ TABLE_ASSIGNMENTS + "(" +
                PK_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FK_ASSIGNMENT_VOLUNTEER_ID + " INTEGER REFERENCES " + TABLE_VOLUNTEERS +
                    "(" + PK_VOLUNTEER_ID + "), " +
                ASSIGNMENT_TITLE + " TEXT, " +
                ASSIGNMENT_COMPLETION_STATUS + " TEXT, " +
                ASSIGNMENT_IS_ACCEPTED + " INTEGER" +//no built-in boolean 0=false 1=true
                ")";

        String CREATE_MISSING_PERSON = "CREATE TABLE "+ TABLE_MISSING_PERSONS + "(" +
                PK_MISSING_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MISSING_PERSON_NAME + " TEXT, " + //should this one be unique? or no? since there is a possibility of being missing again?
                MISSING_PERSON_AGE + " INTEGER, " +
                MISSING_PERSON_DESCRIPTION + " TEXT, " +
                MISSING_PERSON_LAST_LOCATION + " TEXT, " +
                MISSING_PERSON_DATE_MISSING + " TEXT, " + //no built-in date datatype
                MISSING_PERSON_STATUS + " TEXT" +
                ")";

        String CREATE_INVENTORY = "CREATE TABLE "+ TABLE_INVENTORY + "(" +
                PK_INVENTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INVENTORY_ITEM_NAME + " TEXT, " +
                INVENTORY_QUANTITY + " INTEGER, " +
                FK_INVENTORY_EVACUATION_CENTER_NAME + " TEXT REFERENCES " +
                    TABLE_EVACUATION_CENTERS +
                "(" + PK_EVACUATION_CENTER_NAME + ")" +
                ")";

        String CREATE_NOTIFICATIONS = "CREATE TABLE "+ TABLE_NOTIFICATIONS + "(" +
                PK_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FK_NOTIFICATION_INCIDENT_ID + " INTEGER REFERENCES " +
                    TABLE_INCIDENTS + "(" + PK_INCIDENTS_ID + "), " +
                FK_NOTIFICATION_MISSING_ID + " INTEGER REFERENCES " +
                    TABLE_MISSING_PERSONS + "(" + PK_MISSING_PERSON_ID + "), " +
                FK_NOTIFICATION_INVENTORY_ID + " INTEGER REFERENCES " +
                    TABLE_INVENTORY + "(" + PK_INVENTORY_ID + "), " +
                FK_NOTIFICATION_ASSIGNMENT_ID + " INTEGER REFERENCES " +
                    TABLE_ASSIGNMENTS + "(" + PK_ASSIGNMENT_ID + "), " +
                NOTIFICATION_DESCRIPTION + " TEXT, " +
                NOTIFICATION_IS_SHOWN + " INTEGER" +//no built-in boolean 0=false 1=true
                ")";

        String CREATE_ACTIVITY_LOG = "CREATE TABLE "+ TABLE_ACTIVITY_LOGS + "(" +
                PK_ACTIVITY_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FK_ACTIVITY_USERNAME + " TEXT REFERENCES " +
                    TABLE_USERS + "(" + PK_USERS_USERNAME + "), " +
                ACTIVITY_LOG_DESCRIPTION + " TEXT, " +
                ACTIVITY_LOG_DATE_AND_TIME + " TEXT" + //no built-in date datatype
                ")";

        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_INCIDENTS);
        db.execSQL(CREATE_EVACUATION_CENTERS);
        db.execSQL(CREATE_VOLUNTEERS);
        db.execSQL(CREATE_RELIEF_RECORDS);
        db.execSQL(CREATE_ASSIGNMENT);
        db.execSQL(CREATE_MISSING_PERSON);
        db.execSQL(CREATE_INVENTORY);
        db.execSQL(CREATE_NOTIFICATIONS);
        db.execSQL(CREATE_ACTIVITY_LOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ACTIVITY_LOGS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MISSING_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_RELIEF_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_VOLUNTEERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVACUATION_CENTERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_INCIDENTS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(String username, String password, String full_name,
                           String contact_number, String address, String role) {

        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PK_USERS_USERNAME, username);
        cv.put(USERS_PASSWORD, password);
        cv.put(USERS_FULL_NAME, full_name);
        cv.put(USERS_CONTACT_NUMBER, contact_number);
        cv.put(USERS_ADDRESS, address);
        cv.put(USERS_ROLE, role);

        long result = database.insert(TABLE_USERS, null, cv);

        return result > 0;
    }

    public boolean addIncident(String title, String category, String description, int affectedCount,
                               String barangay, String date_and_time, String severity_level,
                               ImageView photo, String priority, double coordinate1,
                               double coordinate2){

        BitmapDrawable drawable = (BitmapDrawable) photo.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(INCIDENTS_TITLE, title);
        cv.put(INCIDENTS_CATEGORY, category);
        cv.put(INCIDENTS_DESCRIPTION, description);
        cv.put(INCIDENTS_NO_OF_AFFECTED_INDIV, affectedCount);
        cv.put(INCIDENTS_BARANGAY, barangay);
        cv.put(INCIDENTS_DATE_AND_TIME, date_and_time);
        cv.put(INCIDENTS_SEVERITY_LEVEL, severity_level);
        cv.put(INCIDENTS_PHOTO_PLACEHOLDER, getBitmapByte(bitmap));
        cv.put(INCIDENTS_PRIORITY_CATEGORY, priority);
        cv.put(INCIDENTS_COORDINATE_LATITUDE, coordinate1);
        cv.put(INCIDENTS_COORDINATE_LONGITUDE, coordinate2);

        long result = database.insert(TABLE_INCIDENTS, null, cv);
        return result > 0;
    }
    public boolean addIncident(String title, String category, String description, int affectedCount,
                               String barangay, String date_and_time, String severity_level,
                               String priority, double coordinate1, double coordinate2){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(INCIDENTS_TITLE, title);
        cv.put(INCIDENTS_CATEGORY, category);
        cv.put(INCIDENTS_DESCRIPTION, description);
        cv.put(INCIDENTS_NO_OF_AFFECTED_INDIV, affectedCount);
        cv.put(INCIDENTS_BARANGAY, barangay);
        cv.put(INCIDENTS_DATE_AND_TIME, date_and_time);
        cv.put(INCIDENTS_SEVERITY_LEVEL, severity_level);
        cv.put(INCIDENTS_PHOTO_PLACEHOLDER, "null");
        cv.put(INCIDENTS_PRIORITY_CATEGORY, priority);
        cv.put(INCIDENTS_COORDINATE_LATITUDE, coordinate1);
        cv.put(INCIDENTS_COORDINATE_LONGITUDE, coordinate2);

        long result = database.insert(TABLE_INCIDENTS, null, cv);
        return result > 0;
    }
    public boolean addEvacuationCenter(String evacuation_name, int capacity, int currentOccupancy,
                                       String address){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PK_EVACUATION_CENTER_NAME, evacuation_name);
        cv.put(EVACUATION_CENTER_CAPACITY, capacity);
        cv.put(EVACUATION_CENTER_CURRENT_OCCUPANCY, currentOccupancy);
        cv.put(EVACUATION_CENTER_ADDRESS, address);

        long result = database.insert(TABLE_EVACUATION_CENTERS, null, cv);
        return result > 0;
    }
    public boolean addReliefRecords(String beneficiary_name, String barangay, String relief_type,
                                    int quantity, String distribution_date, int volunteer_id){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RELIEF_RECORD_BENEFICIARY_NAME, beneficiary_name);
        cv.put(RELIEF_RECORD_BARANGAY, barangay);
        cv.put(RELIEF_RECORD_RELIEF_TYPE, relief_type);
        cv.put(RELIEF_RECORD_QUANTITY, quantity);
        cv.put(RELIEF_RECORD_DISTRIBUTION_DATE, distribution_date);
        cv.put(FK_RELIEF_RECORD_VOLUNTEER_ID, volunteer_id);

        long result = database.insert(TABLE_RELIEF_RECORDS, null, cv);
        return result > 0;
    }
    //SI VOLUNTEER PAADD AGAD IF VOLUNTEER UNG ROLE SA SIGN UP
    public boolean addVolunteers(String username){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FK_VOLUNTEER_USERNAME, username);

        long result = database.insert(TABLE_VOLUNTEERS, null, cv);
        return result > 0;
    }
    public boolean addAssignments(int volunteer_id, String assignment, int isAccepted,
                                  String completion_status){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FK_ASSIGNMENT_VOLUNTEER_ID, volunteer_id);
        cv.put(ASSIGNMENT_TITLE, assignment);
        cv.put(ASSIGNMENT_IS_ACCEPTED, isAccepted);
        cv.put(ASSIGNMENT_COMPLETION_STATUS, completion_status);

        long result = database.insert(TABLE_ASSIGNMENTS, null, cv);
        return result > 0;
    }
    public boolean addMisingPerson(String full_name, int age, String description, String last_location,
                                   String date_missing, String status){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MISSING_PERSON_NAME, full_name);
        cv.put(MISSING_PERSON_AGE, age);
        cv.put(MISSING_PERSON_DESCRIPTION, description);
        cv.put(MISSING_PERSON_LAST_LOCATION, last_location);
        cv.put(MISSING_PERSON_DATE_MISSING, date_missing);
        cv.put(MISSING_PERSON_STATUS, status);

        long result = database.insert(TABLE_MISSING_PERSONS, null, cv);
        return result > 0;
    }
    public boolean addInventory(String item_name, int quantity, String name_evacuation){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(INVENTORY_ITEM_NAME, item_name);
        cv.put(INVENTORY_QUANTITY, quantity);
        cv.put(FK_INVENTORY_EVACUATION_CENTER_NAME, name_evacuation);

        long result = database.insert(TABLE_INVENTORY, null, cv);
        return result > 0;
    }
    public boolean addNotifaction(int incident_id, int missing_id, int inventory_id,
                                  int assignment_id, String description, int isShown){

        database = getWritableDatabase();
        ContentValues cv = new ContentValues();

        if(incident_id == 0){
            cv.put(FK_NOTIFICATION_INCIDENT_ID, "null");
        }else {
            cv.put(FK_NOTIFICATION_INCIDENT_ID, incident_id);
        }
        if(missing_id == 0){
            cv.put(FK_NOTIFICATION_MISSING_ID, "null");
        }else {
            cv.put(FK_NOTIFICATION_MISSING_ID, incident_id);
        }
        if(inventory_id == 0){
            cv.put(FK_NOTIFICATION_INVENTORY_ID, "null");
        }else {
            cv.put(FK_NOTIFICATION_INVENTORY_ID, inventory_id);
        }
        if(assignment_id == 0){
            cv.put(FK_NOTIFICATION_ASSIGNMENT_ID, "null");
        }else {
            cv.put(FK_NOTIFICATION_ASSIGNMENT_ID, assignment_id);
        }
        cv.put(NOTIFICATION_DESCRIPTION, description);
        cv.put(NOTIFICATION_IS_SHOWN, isShown);

        long result = database.insert(TABLE_NOTIFICATIONS, null, cv);
        return result > 0;
    }
    public boolean addActivityLogs(String username, String activity_desc, String date_and_time){
        database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FK_ACTIVITY_USERNAME, username);
        cv.put(ACTIVITY_LOG_DESCRIPTION, activity_desc);
        cv.put(ACTIVITY_LOG_DATE_AND_TIME, date_and_time);

        long result = database.insert(TABLE_ACTIVITY_LOGS, null, cv);
        return result > 0;
    }
    public Boolean checkUserLogin(String username, String password) {

        database = getReadableDatabase();

        String selection = PK_USERS_USERNAME + " = ? AND " + USERS_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = database.query(
                TABLE_USERS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {

            DataHolder.username = cursor.getString(cursor.getColumnIndexOrThrow(PK_USERS_USERNAME));
            DataHolder.role = cursor.getString(cursor.getColumnIndexOrThrow(USERS_ROLE));

            cursor.close();
            return true;

        }
        return false;
    }

    public byte[] getBitmapByte(Bitmap bitmap){//convert image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getImage(byte[] imageByte) {
        //imageView.setImageByBitmap(getImage(cursor.getBlob(cursor.getColumnIndexOrThrow(INCIDENTS_PHOTO))))
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        return bitmap;
    }
}
