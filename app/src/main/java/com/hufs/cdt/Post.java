package com.hufs.cdt;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatActivity {

    private static final String TAG = "blackjin";

    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    public String addresssearch;
    private TextView adsearch;
    public static final int LOAD_SUCCESS = 101;

    public static double xandyx=0;

    public static double xandyy=0;

    private TextView adresult;
    public String addr;

    private File tempFile;

    //firebase에서 DB를 받아오는 부분입니다.
    public static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference conditionRef = mRootRef.child("posting").push();
    EditText aaddress,price, floor, room, option, guan, parking,seol,speadd,date;
    Button send;
    public static final String id=Mypage.strEmail;
    public static String jibunadd, roadadd;
    public static String xx,yy;
    //이미지를 넣기위해 스토리지를 만드는 부분입니다.


    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();

    // Create a reference to "mountains.jpg"
    StorageReference mountainImagesRef ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);




        findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checked();
            }
        });

        findViewById(R.id.checkBox2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checked();
            }
        });

        findViewById(R.id.checkBox4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkedd();
            }
        });

        findViewById(R.id.checkBox5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkedd();
            }
        });

        findViewById(R.id.checkBox6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkedd();
            }
        });

        aaddress=(EditText)findViewById(R.id.editText);
        price=(EditText)findViewById(R.id.editText6);
        floor=(EditText)findViewById(R.id.editText2);
        room=(EditText)findViewById(R.id.editText3);
        option=(EditText)findViewById(R.id.editText4);
        guan=(EditText)findViewById(R.id.editText5);
        parking=(EditText)findViewById(R.id.editText1);
        seol=(EditText)findViewById(R.id.editText7);
        speadd=(EditText)findViewById(R.id.specefic);
        date=(EditText)findViewById(R.id.editText8);
        send=(Button)findViewById(R.id.post_btn);



        Button pick_picture = (Button)findViewById(R.id.pick_picture);
        Button adsearch=(Button)findViewById(R.id.adsearch);
        adresult=(TextView)findViewById(R.id.textView11);
        adsearch.setOnClickListener(new View.OnClickListener() {



            @Override

            public void onClick(View v) {

                thrun();
                getJSON();

            }

        });

        Button pbt=(Button)findViewById(R.id.post_btn);

        pbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Post.this);
                alertDialogBuilder.setMessage("올리시겠습니까?");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //DB넣는부분입니다
                        //
                        //이미지 넣는 부분입니다. 제목은 지번주소+ " " + 상세주소
                        String key = mRootRef.push().getKey();
                        String mykey=key;
                        //String id=myid;
                        Log.d("내아이디",id);
                        String naddresss=getjibunadd();
                        String roadaddress=getRoadadd();
                        String specefic=speadd.getText().toString();
                        String naddress=naddresss+" "+specefic;

                        //이미지 소스의 주소를 저장합니다.
                        mountainImagesRef = storageRef.child(naddress);
                        String img_source=naddress;

                        Log.d("내주소: ",naddress);
                        String nprice=price.getText().toString();
                        String nfloor=floor.getText().toString();
                        String nroom=room.getText().toString();
                        String noption=option.getText().toString();
                        String nguan=guan.getText().toString();
                        String nparking=parking.getText().toString();
                        String nseol=seol.getText().toString();
                        String ndate=date.getText().toString();
                        String ipju=Checked();
                        String roomkind=Checkedd();



                        //이미지 얻기
                        //
                        //
                        //
                        //
                        ImageView imageView = findViewById(R.id.room_img);
                        imageView.setDrawingCacheEnabled(true);
                        imageView.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = mountainImagesRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                            }
                        });




                        makepost mypost=new makepost(id,mykey,naddresss,roadaddress,specefic,nprice,nfloor,nroom,noption,nguan,nparking,nseol,ndate,ipju,roomkind,xx,yy,img_source);

                        Map<String, Object> postValues = mypost.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        //만약에 글을 썼으면 id를 다르게 해서 넣는다.

                        childUpdates.put("postings"+"/"+naddress,postValues);
                        childUpdates.put("/user-post" +"/"+key,postValues);

                        //앱에 내용 추가하는거임
                        mRootRef.updateChildren(childUpdates);


                        Toast.makeText(getApplicationContext(), "게시글이 올라갔습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), FindView.class);
                        intent.putExtra("addressname",naddress);
                        startActivityForResult(intent,0);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Post.this, "게시글이 안올라갔습니다",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        tedPermission();

        findViewById(R.id.pick_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

        }
    }

    /**
     *  앨범에서 이미지 가져오기
     */
    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /**
     *  카메라에서 이미지 가져오기
     */
    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            Uri photoUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }


    /**
     *  폴더 및 파일 만들기
     */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    /**
     *  tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
     */
    private void setImage() {

        ImageView imageView = findViewById(R.id.room_img);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());

        imageView.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }

    /**
     *  권한 설정
     */
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private final MyHandler mHandler = new MyHandler(this);


    private static class MyHandler extends Handler {
        private final WeakReference<Post> weakReference;

        public MyHandler(Post Post) {
            weakReference = new WeakReference<Post>(Post);
        }

        @Override
        public void handleMessage(Message msg) {

            Post Post= weakReference.get();

            if (Post!= null) {
                switch (msg.what) {

                    case LOAD_SUCCESS:


                        String jsonString = (String)msg.obj;
                        Log.d("json message는",jsonString);
                        Post.adresult.setText(jsonString);



                        break;
                }
            }
        }
    }


    public void thrun(){





        Thread thread= new Thread(new Runnable() {

            private boolean positionFlag;

            private void getPoint(String... addr) {

                geo geo = new geo(Post.this, listener);

                geo.execute(addr);

            }



            private geo.OnGeoPointListener listener = new geo.OnGeoPointListener() {

                @Override

                public void onPoint(geo.Point[] p) {

                    int sCnt = 0;

                    for (geo.Point point : p) {



                        if (point.havePoint) sCnt++;



                        double k=  point.x;

                        double d=  point.y;



                        xandyx=point.x;

                        xandyy=point.y;


                    }



                }



                @Override

                public void onProgress(int progress, int max) {



                }

            };

            @Override

            public void run() {

                final EditText editText=(EditText)findViewById(R.id.editText);

                final String c=editText.getText().toString();



                getPoint(

                        c



                );
                addr=c;


            }

        });
        thread.start();

    }

    public void  getJSON() {

        Thread thread = new Thread(new Runnable() {

            public void run() {

                String result;
                String clientId = "u9zcc3txnl";// 애플리케이션 클라이언트 아이디값";

                String clientSecret = "u4Mi9iOJWcFa2qlUhAo15AsZ6iTwHTxuolzTLGcW";// 애플리케이션 클라이언트 시크릿값";
                try {

                    addr = URLEncoder.encode(addr, "UTF-8");

                    String apiURL = "https://naveropenapi.apigw.ntruss.com/map-place/v1/search?query="+addr+"&coordinate="+xandyx+","+xandyy; // json

                    // String apiURL =

                    // "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr; //

                    // xml

                    URL url = new URL(apiURL);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");

                    con.setRequestProperty("X-NCP-APIGW-API-KEY-ID",clientId );

                    con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

                    int responseCode = con.getResponseCode();
                    InputStream inputStream;
                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        inputStream = con.getInputStream();
                    } else {
                        inputStream = con.getErrorStream();

                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;


                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    con.disconnect();

                    result = sb.toString().trim();




                } catch (Exception e) {
                    result = e.toString();
                }

                String json=result;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String predictions = jsonObject.getString("places");
                    JSONArray jsonArray = new JSONArray(predictions);

                    JSONObject subJsonObject = jsonArray.getJSONObject(0);
                    String jibun = subJsonObject.getString("jibun_address");
                    String road = subJsonObject.getString("road_address");
                    setjibun(jibun,road);
                    double x=subJsonObject.getDouble("x");
                    double y=subJsonObject.getDouble("y");
                    String k= String.valueOf(x);
                    String w= String.valueOf(y);
                    setxy(k,w);
                    String aqw=jibun+"\n"+road+"\n"+k+"\n"+w;
                    Message message = mHandler.obtainMessage(LOAD_SUCCESS,aqw);
                    mHandler.sendMessage(message);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        });
        thread.start();

    }
    public static void setjibun(String jibun,String road){
        jibunadd=jibun;
        roadadd=road;
    }
    public String getjibunadd(){
        return jibunadd;
    }
    public String getRoadadd(){
        return roadadd;
    }


    public  String Checked(){
        CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox);
        CheckBox checkBox2=(CheckBox)findViewById(R.id.checkBox2);


        String resultText = "";

        if(checkBox.isChecked()){
            resultText=checkBox.getText().toString();
        }
        if(checkBox2.isChecked()){
            resultText=checkBox2.getText().toString();
        }

        return resultText;
    }

    public  String Checkedd(){

        CheckBox checkBox4=(CheckBox)findViewById(R.id.checkBox4);
        CheckBox checkBox5=(CheckBox)findViewById(R.id.checkBox5);
        CheckBox checkBox6=(CheckBox)findViewById(R.id.checkBox6);

        String resultText = "";

        if(checkBox4.isChecked()){

            resultText=checkBox4.getText().toString();
        }
        if(checkBox5.isChecked()){
            resultText=checkBox5.getText().toString();
        }
        if(checkBox6.isChecked()){
            resultText=checkBox6.getText().toString();
        }
        return resultText;
    }

    public void setxy(String ax,String ay){
        xx=ax;
        yy=ay;
    }
    public String getx(){
        return xx;
    }
    public String gety(){
        return yy;
    }


}