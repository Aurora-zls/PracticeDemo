//package com.minijoy.sochat.controller.faceAuth;
//
///**
// * author : created by zhaolansheng
// * time   : on 2020/7/29-[4:24 PM]
// */
//
//import com.minijoy.sochat.R;
//
//import org.opencv.android.CameraBridgeViewBase;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfRect;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.dnn.Dnn;
//import org.opencv.dnn.Net;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.widget.TextView;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class TestActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
//
//    protected static int LIMIT_TIME = 3;//临界的时间，超过5秒保存图片一次
//
//    protected static Bitmap mFaceBitmap;//包含有人脸的图像
//
//    private CameraBridgeViewBase openCvCameraView;
//
//    private static final String TAG = "yaoxuminTest";
//
//    private Handler mHandler;
//
//    private CascadeClassifier mFrontalFaceClassifier = null; //正脸 级联分类器
//
//    private CascadeClassifier mProfileFaceClassifier = null; //侧脸 级联分类器
//
//    private Rect[] mFrontalFacesArray;
//
//    private Rect[] mProfileFacesArray;
//
//    private Mat mRgba; //图像容器
//
//    private Mat mGray;
//
//    private TextView mFrontalFaceNumber;
//
//    private TextView mProfileFaceNumber;
//
//    private TextView mCurrentNumber;
//
//    private TextView mWaitTime;
//
//    private int mFrontFaces = 0;
//
//    private int mProfileFaces = 0;
//
//    //记录之前观看的人的数量
//    private int mRecodeFaceSize;
//
//    //当前观看的人的数量
//    private int mCurrentFaceSize;
//
//    //保存最大的观看人数峰值
//    private int mMaxFaceSize;
//
//    //记录的时间，秒级别
//    private long mRecodeTime;
//
//    //记录的时间，毫秒级别，空闲时间，用来计数，实现0.5秒一次检查
//    private long mRecodeFreeTime;
//
//    //当前的时间，秒级别
//    private long mCurrentTime = 0;
//
//    //当前的时间，毫秒级别
//    private long mMilliCurrentTime = 0;
//
//    //观看时间
//    private int mWatchTheTime = 0;
//
//    //离开的人数
//    private int mLeftFaces = 0;
//
//    //设置检测区域
//    private Size m55Size = new Size(55, 55);
//
//    private Size m65Size = new Size(65, 65);
//
//    private Size mDefault = new Size();
//
//    //性别和年龄识别区域
//    private Net mAgeNet;
//
//    private static final String[] AGES = new String[]{"0-2", "4-6", "8-13", "15-20", "25-32",
//            "38-43", "48-53", "60+"};
//
//    private Net mGenderNet;
//
//    private static final String[] GENDERS = new String[]{"男", "女"};
//
//    //保留正脸数据用于分析性别和年龄
//    private Rect[] mTempFrontalFacesArray;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_opencv);
//        mHandler = new Handler();
//        //初始化控件
//        initComponent();
//        //初始化摄像头
//        initCamera();
//        //opencv资源的初始化在父类的 onResume 方法中
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!OpenCVLoader.initDebug()) {
//            Log.e(TAG, "OpenCV init error");
//        }
//        //初始化opencv资源
//        initOpencv();
//    }
//
//    /**
//     * @Description 初始化组件
//     * @author 姚旭民
//     * @date 2019/7/24 12:12
//     */
//    protected void initComponent() {
//        openCvCameraView = findViewById(R.id.javaCameraView);
//        mFrontalFaceNumber = findViewById(R.id.tv_frontal_face_number);
//        mProfileFaceNumber = findViewById(R.id.tv_profile_face_number);
//        mCurrentNumber = findViewById(R.id.tv_current_number);
//        mWaitTime = findViewById(R.id.tv_wait_time);
//    }
//
//    /**
//     * @Description 初始化摄像头
//     * @author 姚旭民
//     * @date 2019/7/24 12:12
//     */
//    protected void initCamera() {
//        int camerId = 0;
//        Camera.CameraInfo info = new Camera.CameraInfo();
//        int numCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numCameras; i++) {
//            Camera.getCameraInfo(i, info);
//            Log.v("yaoxumin",
//                    "在 CameraRenderer 类的 openCamera 方法 中执行了开启摄像 Camera.open 方法,cameraId:" + i);
//            camerId = i;
//            break;
//        }
//        openCvCameraView.setCameraIndex(camerId); //摄像头索引        -1/0：后置双摄     1：前置
//        openCvCameraView.enableFpsMeter(); //显示FPS
//        openCvCameraView.setCvCameraViewListener(this);//监听
//        openCvCameraView.setMaxFrameSize(640, 480);//设置帧大小
//    }
//
//    /**
//     * @Description 初始化opencv资源
//     * @author 姚旭民
//     * @date 2019/7/24 12:12
//     */
//    protected void initOpencv() {
//        initFrontalFace();
//        initProfileFace();
//        // 显示
//        openCvCameraView.enableView();
//    }
//
//    /**
//     * @Description 初始化正脸分类器
//     * @author 姚旭民
//     * @date 2019/7/24 12:12
//     */
//    public void initFrontalFace() {
//        try {
//            //这个模型是我觉得来说相对不错的
//            InputStream is = getResources().openRawResource(
//                    R.raw.haarcascade_frontalface_alt); //OpenCV的人脸模型文件： lbpcascade_frontalface_improved
//            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
//            File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt.xml");
//            FileOutputStream os = new FileOutputStream(mCascadeFile);
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            is.close();
//            os.close();
//            // 加载 正脸分类器
//            mFrontalFaceClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//
//    /**
//     * @Description 初始化侧脸分类器
//     * @author 姚旭民
//     * @date 2019/7/24 12:12
//     */
//    public void initProfileFace() {
//        try {
//            //这个模型是我觉得来说相对不错的
//            InputStream is = getResources().openRawResource(
//                    R.raw.haarcascade_profileface); //OpenCV的人脸模型文件： lbpcascade_frontalface_improved
//            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
//            File mCascadeFile = new File(cascadeDir, "haarcascade_profileface.xml");
//            FileOutputStream os = new FileOutputStream(mCascadeFile);
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            is.close();
//            os.close();
//            // 加载 侧脸分类器
//            mProfileFaceClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//
//    @Override
//    public void onCameraViewStarted(int width, int height) {
//        mRgba = new Mat();
//        mGray = new Mat();
//
//        //加载年龄模块
//        String proto = getPath("deploy_age.prototxt");
//        String weights = getPath("age_net.caffemodel");
//        Log.i(TAG, "onCameraViewStarted| ageProto : " + proto + ",ageWeights : " + weights);
//        mAgeNet = Dnn.readNetFromCaffe(proto, weights);
//
//        //加载性别模块
//        proto = getPath("deploy_gender.prototxt");
//        weights = getPath("gender_net.caffemodel");
//        Log.i(TAG, "onCameraViewStarted| genderProto : " + proto + ",genderWeights : " + weights);
//        mGenderNet = Dnn.readNetFromCaffe(proto, weights);
//
//        if (mAgeNet.empty()) {
//            Log.i(TAG, "Network loading failed");
//        } else {
//            Log.i(TAG, "Network loading success");
//        }
//    }
//
//    @Override
//    public void onCameraViewStopped() {
//        mRgba.release();
//        mGray.release();
//    }
//
//    /**
//     * @Description 在这里实现人脸检测和性别年龄识别
//     * @author 姚旭民
//     * @date 2019/7/24 12:16
//     */
//    @Override
//    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        mRgba = inputFrame.rgba(); //RGBA
//        mGray = inputFrame.gray(); //单通道灰度图
//        //解决  前置摄像头旋转显示问题
//        Core.flip(mRgba, mRgba, 1); //旋转,变成镜像
//
//        mMilliCurrentTime = System.currentTimeMillis() / 100;//获取当前时间毫秒级别
//        mCurrentTime = mMilliCurrentTime / 10;//获取当前时间，秒级别
//
//        //每0.5秒检查一次，如果你一直开测，不做这个过滤，那么大概一秒可能是起码5次计算检测是否有人脸，这样的计算对于机器有一定的压力，
//        //而且这些计算起始可以不用那么多的，我个人觉得合适的是，
//        //没有检测到人的时候，1秒2次检测
//        //检测到人的时候，修改为1秒1次检测，这样你开一天甚至几天也不会出现过热导致app退出
//        if (mRecodeFreeTime + 5 <= mMilliCurrentTime) {
//            mRecodeFreeTime = mMilliCurrentTime;
//            if (mRecodeTime == 0 || mCurrentFaceSize == 0
//                    || mRecodeTime < mCurrentTime) {//识别到人之后，1秒做一次检测
//                mRecodeTime = mCurrentTime;//记录当前时间
//                //检测并显示
//                MatOfRect frontalFaces = new MatOfRect();
//                MatOfRect profileFaces = new MatOfRect();
//
//                if (mFrontalFaceClassifier
//                        != null) {//这里2个 Size 是用于检测人脸的，越小，检测距离越远，1.1, 5, 2, m65Size, mDefault着四个参数可以提高检测的准确率，5表示确认五次，具体百度 detectMultiScale 这个方法
//                    mFrontalFaceClassifier
//                            .detectMultiScale(mGray, frontalFaces, 1.1, 5, 2, m65Size, mDefault);
//                    mFrontalFacesArray = frontalFaces.toArray();
//                    if (mFrontalFacesArray.length > 0) {
//                        Log.i(TAG, "正脸人数为 : " + mFrontalFacesArray.length);
//                    }
//                    mCurrentFaceSize = mFrontFaces = mFrontalFacesArray.length;
//                }
//
//                if (mProfileFaceClassifier != null) {//这里2个 110 是用于检测人脸的，越小，检测距离越远
//                    mProfileFaceClassifier
//                            .detectMultiScale(mGray, profileFaces, 1.1, 6, 0, m55Size, mDefault);
//                    mProfileFacesArray = profileFaces.toArray();
//                    if (mProfileFacesArray.length > 0) {
//                        Log.i(TAG, "侧脸人数为 : " + mProfileFacesArray.length);
//                    }
//                    mProfileFaces = mProfileFacesArray.length;
//                }
//
//                mProfileFacesArray = profileFaces.toArray();
//                mCurrentFaceSize += mProfileFaces;
//
//           /* if (mProfileFacesArray.length > 0){
//                for (int i = 0; i < mProfileFacesArray.length; i++) {    //用框标记
//                    Imgproc.rectangle(mRgba, mProfileFacesArray[i].tl(), mProfileFacesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
//                }
//            }*/
//
//                if (mCurrentFaceSize > 0) {//检测到有人
//                    mRecodeFaceSize = mCurrentFaceSize;//记录有人看的时候的数量
//                    if (mRecodeFaceSize > mMaxFaceSize)//记录最大人数
//                    {
//                        mMaxFaceSize = mRecodeFaceSize;
//                    }
//                    mWatchTheTime++;//叠加观看时间
//                    //性别识别的需要
//                    mTempFrontalFacesArray = mFrontalFacesArray;
//                    if (mWatchTheTime == LIMIT_TIME) {//达到临界值，进行图片保存
//                        Log.v(TAG, "当前识别到的人脸数量为：" + mCurrentFaceSize);
//                   /* Bitmap bitmap = Bitmap.createBitmap(mRgba.width(), mRgba.height(), Bitmap.Config.RGB_565);
//                    Utils.matToBitmap(mRgba, bitmap);
//                    mFaceBitmap = bitmap;*/
//                    }
//                } else {//没有人观看
//                    if (mWatchTheTime > 0) {//之前是否有人观看
//                        if (mWatchTheTime < LIMIT_TIME) {//如果小于临界值，进行丢数据包操作,暂时没有接口
//                            Log.v(TAG, "没有超过临界值，当前观看人数为：" + mRecodeFaceSize + ",当前观看的海报时间为："
//                                    + mWatchTheTime);
//                        } else {//大于等于临界值，进行图片发送
//                            Log.v(TAG, "超过临界值，当前观看人数为：" + mRecodeFaceSize + ",当前观看的海报时间为："
//                                    + mWatchTheTime);
//                        }
//
//                        //数据重置
//                        mWatchTheTime = mMaxFaceSize = mRecodeFaceSize = 0;
//
//                        Log.i(TAG, "mTempFrontalFacesArray : " + mTempFrontalFacesArray);
//                        if (mTempFrontalFacesArray != null) {
//                            String age;
//                            Log.i(TAG, "mTempFrontalFacesArray.length : "
//                                    + mTempFrontalFacesArray.length);
//                            for (int i = 0; i < mTempFrontalFacesArray.length; i++) {
//                                Log.i(TAG, "年龄为 : " + analyseAge(mRgba, mTempFrontalFacesArray[i]));
//                                Log.i(TAG,
//                                        "性别为 : " + analyseGender(mRgba, mTempFrontalFacesArray[i]));
//                            }
//                            mTempFrontalFacesArray = null;
//                        }
//                    }
//                }
//
//                //显示检测到的人数
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mFrontalFaceNumber.setText(mFrontFaces + "");
//                        mProfileFaceNumber.setText(mProfileFaces + "");
//                        mCurrentNumber.setText(mCurrentFaceSize + "");
//                        mWaitTime.setText(mWatchTheTime + "");
//                    }
//                }, 0);
//            }
//        }
//        return mRgba;
//    }
//
//    /**
//     * @param mRgba 图片资源
//     * @param face  人脸资源
//     * @return 返回年龄区间
//     * @Description 分析年龄
//     * @author 姚旭民
//     * @date 2019/7/24 12:54
//     */
//    private String analyseAge(Mat mRgba, Rect face) {
//        try {
//            Mat capturedFace = new Mat(mRgba, face);
//            //Resizing pictures to resolution of Caffe model
//            Imgproc.resize(capturedFace, capturedFace, new Size(227, 227));
//            //Converting RGBA to BGR
//            Imgproc.cvtColor(capturedFace, capturedFace, Imgproc.COLOR_RGBA2BGR);
//
//            //Forwarding picture through Dnn
//            Mat inputBlob = Dnn.blobFromImage(capturedFace, 1.0f, new Size(227, 227),
//                    new Scalar(78.4263377603, 87.7689143744, 114.895847746), false, false);
//            mAgeNet.setInput(inputBlob, "data");
//            Mat probs = mAgeNet.forward("prob").reshape(1, 1);
//            Core.MinMaxLocResult mm = Core.minMaxLoc(probs); //Getting largest softmax output
//
//            double result = mm.maxLoc.x; //Result of age recognition prediction
//            Log.i(TAG, "Result is: " + result);
//            return AGES[(int) result];
//        } catch (Exception e) {
//            Log.e(TAG, "Error processing age", e);
//        }
//        return null;
//    }
//
//    /**
//     * @param mRgba 图片资源
//     * @param face  人脸资源
//     * @return 返回分析结果性别
//     * @Description 分析性别
//     * @author 姚旭民
//     * @date 2019/7/24 13:04
//     */
//    private String analyseGender(Mat mRgba, Rect face) {
//        try {
//            Mat capturedFace = new Mat(mRgba, face);
//            //Resizing pictures to resolution of Caffe model
//            Imgproc.resize(capturedFace, capturedFace, new Size(227, 227));
//            //Converting RGBA to BGR
//            Imgproc.cvtColor(capturedFace, capturedFace, Imgproc.COLOR_RGBA2BGR);
//
//            //Forwarding picture through Dnn
//            Mat inputBlob = Dnn.blobFromImage(capturedFace, 1.0f, new Size(227, 227),
//                    new Scalar(78.4263377603, 87.7689143744, 114.895847746), false, false);
//            mGenderNet.setInput(inputBlob, "data");
//            Mat probs = mGenderNet.forward("prob").reshape(1, 1);
//            Core.MinMaxLocResult mm = Core.minMaxLoc(probs); //Getting largest softmax output
//
//            double result
//                    = mm.maxLoc.x; //Result of gender recognition prediction. 1 = FEMALE, 0 = MALE
//            Log.i(TAG, "Result is: " + result);
//            return GENDERS[(int) result];
//        } catch (Exception e) {
//            Log.e(TAG, "Error processing gender", e);
//        }
//        return null;
//    }
//
//    /**
//     * @param file 文件名称
//     * @return 返回文件路径
//     * @Description 获取文件的路径
//     * @author 姚旭民
//     * @date 2019/7/24 13:05
//     */
//    private String getPath(String file) {
//        AssetManager assetManager = getApplicationContext().getAssets();
//        BufferedInputStream inputStream;
//
//        try {
//            //Reading data from app/src/main/assets
//            inputStream = new BufferedInputStream(assetManager.open(file));
//            byte[] data = new byte[inputStream.available()];
//            inputStream.read(data);
//            inputStream.close();
//
//            File outputFile = new File(getApplicationContext().getFilesDir(), file);
//            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
//            fileOutputStream.write(data);
//            fileOutputStream.close();
//            return outputFile.getAbsolutePath();
//        } catch (IOException ex) {
//            Log.e(TAG, ex.toString());
//        }
//        return "";
//    }
//}
