package io.github.hotstu.graphicfun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenglActivity extends Activity {
    private FrameLayout mRootLayout;
    private SeekBar mSeek;
    private MainView mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_opgl);

        mView = new MainView(this);
        mView.setOnClickListener(mClick);
        mRootLayout = (FrameLayout)findViewById(R.id.layout_root);
        mRootLayout.addView(mView, 0);

        mSeek = (SeekBar)findViewById(R.id.seek_value);
        mSeek.setMax(255);
        mSeek.setProgress(128);
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mView.mRenderer.mTest = i - 128;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mView.mRenderer.takePicture(mPicture);
        }
    };

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            mView.mRenderer.restartPreview();
            String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + Environment.DIRECTORY_DCIM + "/Camera";
            File dir = new File(storageDir);

            if (!dir.exists()) dir.mkdir();

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = storageDir + "/IMG_" + timeStamp + ".jpg";

            File file = new File(path);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.flush();
                fos.close();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.parse("file://" + path);
                intent.setData(uri);
                sendBroadcast(intent);
            }
            catch (Exception e) {
                Log.e("CheckLog", e.getMessage());
                return;
            }
        }
    };
    class MainView extends GLSurfaceView {
        MainRenderer mRenderer;

        MainView(Context context) {
            super(context);
            mRenderer = new MainRenderer(this);
            setEGLContextClientVersion(2);
            setRenderer(mRenderer);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            super.surfaceCreated(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            super.surfaceDestroyed(holder);
            mRenderer.close();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            super.surfaceChanged(holder, format, w, h);
        }



        public class MainRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
            private final String vertexShaderCode =
                    "attribute vec2 vPosition;\n" +
                            "attribute vec2 vTexCoord;\n" +
                            "varying vec2 texCoord;\n" +
                            "void main() {\n" +
                            "   texCoord = vTexCoord;\n" +
                            "   gl_Position = vec4(vPosition.x, vPosition.y, 0.0, 1.0);\n" +
                            "}";

            private final String fragmentShaderCode =
                    "#extension GL_OES_EGL_image_external : require\n" +
                            "precision mediump float;\n" +
                            "uniform samplerExternalOES sTexture;\n" +
                            "uniform float changer;\n" +
                            "varying vec2 texCoord;\n" +
                            "void main() {\n" +
                            "   vec4 tex = texture2D(sTexture, texCoord);\n" +
                            "   float cr, cg, cb;\n" +
                            "   if (tex.r + changer > 255.0) cr = 255.0;\n" +
                            "   else if (tex.r + changer < 0.0) cr = 0.0;\n" +
                            "   else cr = tex.r + changer;\n" +
                            "   if (tex.g + changer > 255.0) cg = 255.0;\n" +
                            "   else if (tex.g + changer < 0.0) cg = 0.0;\n" +
                            "   else cg = tex.g + changer;\n" +
                            "   if (tex.b + changer > 255.0) cb = 255.0;\n" +
                            "   else if (tex.b + changer < 0.0) cb = 0.0;\n" +
                            "   else cb = tex.b + changer;\n" +
                            "   gl_FragColor = vec4(cr, cg, cb, tex.a);\n" +
                            "}";

            private int[] hTex;
            private FloatBuffer pVertex;
            private FloatBuffer pTexCoord;
            private int hProgram;

            private Camera mCamera;
            private SurfaceTexture mSTexture;

            private boolean mUpdateST = false;

            private MainView mView;

            int mTest = 0;

            MainRenderer(MainView view) {
                mView = view;
                float[] vtmp = {-1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f};
                float[] ttmp = {1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};

                pVertex = ByteBuffer.allocateDirect(8*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                pVertex.put(vtmp);
                pVertex.position(0);

                pTexCoord = ByteBuffer.allocateDirect(8*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                pTexCoord.put(ttmp);
                pTexCoord.position(0);
            }

            @Override
            public void onSurfaceCreated(GL10 unused, EGLConfig config) {
                // String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);
                // Log.i("CheckLog", "Gl extensions: " + extensions);
                // Assert.assertTrue(extensions.contains("OES_EGL_image_external"));

                initTex();
                mSTexture = new SurfaceTexture(hTex[0]);
                mSTexture.setOnFrameAvailableListener(this);

                mCamera = Camera.open();
                try {
                    mCamera.setPreviewTexture(mSTexture);

                } catch (IOException e) {}

                GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                hProgram = loadShader(vertexShaderCode, fragmentShaderCode);
            }


            public void onSurfaceChanged(GL10 unused, int width, int height) {
                GLES20.glViewport(0, 0, width, height);

                Camera.Parameters params = mCamera.getParameters();
                params.setPreviewSize(height, width);
                params.setPictureSize(height, width);

                mCamera.setParameters(params);
                mCamera.startPreview();
            }

            public void onDrawFrame(GL10 unused) {
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

                synchronized(this) {
                    if (mUpdateST) {
                        mSTexture.updateTexImage();
                        mUpdateST = false;
                    }
                }

                GLES20.glUseProgram(hProgram);

                int ph = GLES20.glGetAttribLocation(hProgram, "vPosition");
                int tch = GLES20.glGetAttribLocation(hProgram, "vTexCoord");
                int th = GLES20.glGetUniformLocation(hProgram, "sTexture");
                int mc = GLES20.glGetUniformLocation(hProgram, "changer");

                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, hTex[0]);
                GLES20.glUniform1i(th, 0);
                GLES20.glUniform1f(mc, mTest);

                GLES20.glVertexAttribPointer(ph, 2, GLES20.GL_FLOAT, false, 4*2, pVertex);
                GLES20.glVertexAttribPointer(tch, 2, GLES20.GL_FLOAT, false, 4*2, pTexCoord);
                GLES20.glEnableVertexAttribArray(ph);
                GLES20.glEnableVertexAttribArray(tch);

                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
                GLES20.glFlush();
            }

            public synchronized void onFrameAvailable(SurfaceTexture st) {
                mUpdateST = true;
                mView.requestRender();
            }

            private void initTex() {
                hTex = new int[1];
                GLES20.glGenTextures(1, hTex, 0);
                GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, hTex[0]);
                GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S,
                        GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T,
                        GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                        GLES20.GL_NEAREST);
                GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                        GLES20.GL_NEAREST);
            }

            public void close() {
                mUpdateST = false;
                mSTexture.release();
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                deleteTex();
            }

            private void deleteTex() {
                GLES20.glDeleteTextures(1, hTex, 0);
            }

            private int loadShader(String vertexShaderCode, String fragmentShaderCode) {
                int vshader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
                GLES20.glShaderSource(vshader, vertexShaderCode);
                GLES20.glCompileShader(vshader);

                int[] compiled = new int[1];
                GLES20.glGetShaderiv(vshader, GLES20.GL_COMPILE_STATUS, compiled, 0);

                if (compiled[0] == 0) {
                    Log.e("CheckLog", "Could not compile vshader");
                    Log.v("CheckLog", "Could not compile vshader:" + GLES20.glGetShaderInfoLog(vshader));
                    GLES20.glDeleteShader(vshader);
                    vshader = 0;
                }

                int fshader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
                GLES20.glShaderSource(fshader, fragmentShaderCode);
                GLES20.glCompileShader(fshader);
                GLES20.glGetShaderiv(fshader, GLES20.GL_COMPILE_STATUS, compiled, 0);
                if (compiled[0] == 0) {
                    Log.e("CheckLog", "Could not compile fshader");
                    Log.v("CheckLog", "Could not compile fshader:" + GLES20.glGetShaderInfoLog(fshader));
                    GLES20.glDeleteShader(fshader);
                    fshader = 0;
                }

                int program = GLES20.glCreateProgram();
                GLES20.glAttachShader(program, vshader);
                GLES20.glAttachShader(program, fshader);
                GLES20.glLinkProgram(program);

                return program;
            }

            public void takePicture(Camera.PictureCallback picture) {
                mCamera.takePicture(null, null, null, picture);
            }

            public void restartPreview() {
                mCamera.startPreview();
            }
        }
    }
}

