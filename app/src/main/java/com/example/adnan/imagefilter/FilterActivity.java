package com.example.adnan.imagefilter;

import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class FilterActivity extends AppCompatActivity {
    final static int CAMERA_CAPTURE = 1;
    //keep track of cropping intent
    final int PIC_CROP = 2;
    private Uri picUri;
    ImageView InvertImv,ContrastImv,RoundedCornerImv,SaturationImv,HueFilterImv,GrayScaleImv,RotateImv,CropImv,BoostRedImv,BoostGreenImv,BoostBlueImv,imv;
    CropImageView Cropper;
    TextView CropText;
    SeekBar contrastbar,saturationbar;
    ArrayList<Bitmap> prevBitmap = new ArrayList<Bitmap>();
    private int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Cropper = (CropImageView) findViewById(R.id.Cropper);
        imv = (ImageView) findViewById(R.id.ResultingImage);
        InvertImv = (ImageView) findViewById(R.id.InvertImv);
        ContrastImv = (ImageView) findViewById(R.id.ContrastImv);
        RoundedCornerImv = (ImageView) findViewById(R.id.RoundedCornerImv);
        SaturationImv = (ImageView) findViewById(R.id.SaturationImv);
        HueFilterImv = (ImageView) findViewById(R.id.HueFilterImv);
        GrayScaleImv = (ImageView) findViewById(R.id.GrayScaleImv);
        BoostRedImv = (ImageView) findViewById(R.id.BoostRedImv);
        BoostGreenImv = (ImageView) findViewById(R.id.BoostGreenImv);
        BoostBlueImv = (ImageView) findViewById(R.id.BoostBlueImv);
        RotateImv = (ImageView) findViewById(R.id.RotateImv);
        CropImv = (ImageView) findViewById(R.id.CropImv);
        CropText= (TextView) findViewById(R.id.CropText);

        contrastbar=(SeekBar)findViewById(R.id.contrastBar);
        contrastbar.setVisibility(View.GONE);
        contrastbar.setMax(50);
        contrastbar.setProgress(25);
        saturationbar=(SeekBar)findViewById(R.id.saturationBar);
        saturationbar.setVisibility(View.GONE);
        saturationbar.setMax(9);
        saturationbar.setProgress(0);

        Intent capture_intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(capture_intent, CAMERA_CAPTURE);

        InvertImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.doInvert(bitmap);
                imv.setImageBitmap(bitmap);
            }
        });
        ContrastImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(saturationbar.getVisibility()==View.VISIBLE)
                    saturationbar.setVisibility(View.GONE);
                contrastbar.setVisibility(View.VISIBLE);
            }
        });
        RoundedCornerImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.roundCorner((bitmap), 10);
                imv.setImageBitmap(bitmap);
            }
        });
        SaturationImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(contrastbar.getVisibility()==View.VISIBLE)
                    contrastbar.setVisibility(View.GONE);
                saturationbar.setVisibility(View.VISIBLE);
            }
        });
        HueFilterImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.applyHueFilter((bitmap), 3);
                imv.setImageBitmap(bitmap);
            }
        });
        RotateImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.rotate(bitmap, 90);
                imv.setImageBitmap(bitmap);
            }
        });
        GrayScaleImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.doGreyscale(bitmap);
                imv.setImageBitmap(bitmap);
            }
        });
        BoostRedImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.boost(bitmap, 1, 2);
                imv.setImageBitmap(bitmap);
            }
        });
        BoostGreenImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.boost(bitmap,2,2);
                imv.setImageBitmap(bitmap);
            }
        });
        BoostBlueImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bitmap);
                counter++;
                bitmap = Filter.boost(bitmap,3,2);
                imv.setImageBitmap(bitmap);
            }
        });
        CropImv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                if(Cropper.getVisibility() == View.GONE)
                {
                    prevBitmap.add(bitmap);
                    counter++;
                    imv.setVisibility(View.GONE);
                    Cropper.setImageBitmap(bitmap);
                    Cropper.setVisibility(View.VISIBLE);
                    CropText.setText("Click again");
                }
                else
                {
                    Bitmap croppedImage = Cropper.getCroppedImage();
                    imv.setImageBitmap(croppedImage);
                    Cropper.setImageBitmap(croppedImage);
                    Cropper.setVisibility(View.GONE);
                    imv.setVisibility(View.VISIBLE);
                    CropText.setText("Crop");
                }
            }
        });
        Button undo_btn= (Button)findViewById(R.id.undo_btn);
        undo_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(counter!=0) {
                    imv.setImageBitmap(prevBitmap.get(counter-1));
                    prevBitmap.remove(counter);
                    counter--;
                }
            }
        });
        Button save_btn= (Button)findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                try {
                    save(bitmap, v);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        contrastbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Bitmap bmp;

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                bmp = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bmp);
                counter++;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap temp;
                {
                    //Log.e("progress", "progress of contrast=" + (progress - 25));
                    temp = Filter.createContrast(bmp, (progress - 25));
                }
                imv.setImageBitmap(temp);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                contrastbar.setVisibility(View.GONE);
            }
        });
        saturationbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Bitmap bmp;
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                bmp = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                prevBitmap.add(bmp);
                counter++;
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
            {
                Bitmap temp;
                {
                    //Log.e("progress", "progress of saturation=" + (progress+1));
                    temp = Filter.applySaturationFilter(bmp, (progress + 1));
                }
                imv.setImageBitmap(temp);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saturationbar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            //get the returned data
            Bundle extras = data.getExtras();
            //get the cropped bitmap
            Bitmap bitmap = extras.getParcelable("data");
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), extras.getParcelable("data"));
            //bitmap = Bitmap.createBitmap(bitmap, 0, 0, 400, 400);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            prevBitmap.add(bitmap);
            imv.setImageBitmap(bitmap);
            Cropper.setImageBitmap(bitmap);
            InvertImv.setImageBitmap(Filter.doInvert(bitmap));
            ContrastImv.setImageBitmap(Filter.createContrast((bitmap), 0));
            RoundedCornerImv.setImageBitmap(Filter.roundCorner((bitmap), 10));
            SaturationImv.setImageBitmap(Filter.applySaturationFilter((bitmap), 3));
            HueFilterImv.setImageBitmap(Filter.applyHueFilter((bitmap), 3));
            GrayScaleImv.setImageBitmap(Filter.doGreyscale(bitmap));
            BoostRedImv.setImageBitmap(Filter.boost(bitmap, 1, 2));
            BoostGreenImv.setImageBitmap(Filter.boost(bitmap,2,2));
            BoostBlueImv.setImageBitmap(Filter.boost(bitmap,3,2));
            RotateImv.setImageBitmap(Filter.rotate((bitmap), 0));
            CropImv.setImageBitmap(bitmap);
        }
        else
        {
            prevBitmap.clear();
            counter=0;
            Intent back = new Intent(FilterActivity.this,MainActivity.class);
            startActivity(back);
        }
    }
    private void save(Bitmap bm,View v)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/image_filter");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        //Log.i(TAG, "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            final String path = file.getPath();
            Snackbar.make(v, "Image Saved", Snackbar.LENGTH_LONG)
                    .setAction("Share", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/jpeg");
                            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
                            startActivity(Intent.createChooser(share, "Share Image"));
                        }
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        //Log.i("ExternalStorage", "Scanned " + path + ":");
                        //Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prevBitmap.clear();
        counter=0;
        Intent back = new Intent(FilterActivity.this,MainActivity.class);
        startActivity(back);
    }


}
