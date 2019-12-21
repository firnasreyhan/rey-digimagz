package com.project.digimagz.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.api.ApiClient;
import com.project.digimagz.api.ApiInterface;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.DefaultStructureUser;
import com.project.digimagz.model.UserModel;
import com.project.digimagz.view.activity.MainActivity;
import com.project.digimagz.view.activity.UpdateProfileActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private MaterialButton materialButtonSignOut;
    private TextView tvName, tvEmail, tvPhone, tvAge, tvGender, tvStatus;
    private CircleImageView imgProfile;
    private LinearLayout phoneLayout;
    private ImageButton btnCamera, btnEditName, btnEditGender, btnEditAge;
    private ProgressBar progressBar, progressBarEdit;
    private InitRetrofit initRetrofitUser;
    String email;
    int iend;
    protected static final int REQUEST_CAMERA = 1;
    protected static final int SELECT_FILE = 2;

    private Retrofit retrofit;
    private InitRetrofit initRetrofit;
    private ApiInterface apiInterface;

    private File profileFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        materialButtonSignOut = view.findViewById(R.id.materialButtonSignOut);
        tvName = view.findViewById(R.id.tvNameProfile);
        tvEmail = view.findViewById(R.id.tvEmailProfile);
        tvPhone = view.findViewById(R.id.tvPhoneProfile);
        tvAge = view.findViewById(R.id.tvAge);
        tvGender = view.findViewById(R.id.tvGender);
        tvStatus = view.findViewById(R.id.tvStatus);
        imgProfile = view.findViewById(R.id.imgProfile);
        phoneLayout = view.findViewById(R.id.phoneLayout);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnEditName = view.findViewById(R.id.btnEditNama);
        btnEditGender = view.findViewById(R.id.btnEditJenisKelamin);
        btnEditAge = view.findViewById(R.id.btnEditUsia);
        progressBar = view.findViewById(R.id.progressBar);
        progressBarEdit = view.findViewById(R.id.progressBarEditNama);

        initRetrofitUser = new InitRetrofit();
        initRetrofit = new InitRetrofit();
        retrofit = ApiClient.getRetrofit();
        apiInterface = retrofit.create(ApiInterface.class);

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int subindex = firebaseUser.getEmail().indexOf("@");
                    //apiInterface.getUser(firebaseUser.getEmail().substring(0, subindex)).enqueue(new Callback<DefaultStructureUser>() {
                    apiInterface.getUser(firebaseUser.getEmail()).enqueue(new Callback<DefaultStructureUser>() {
                        @Override
                        public void onResponse(Call<DefaultStructureUser> call, Response<DefaultStructureUser> response) {
                            Log.d("getUser", String.valueOf(response.body().getData().size()));
                            ArrayList<UserModel> userModel = response.body().getData();
                            tvName.setText(userModel.get(0).getUserName());
                            if (userModel.get(0).getGender() != null && userModel.get(0).getDateBirth() != null) {
                                if (userModel.get(0).getGender().equalsIgnoreCase("L")) {
                                    tvGender.setText("Laki-laki");
                                } else if (userModel.get(0).getGender().equalsIgnoreCase("P")) {
                                    tvGender.setText("Perempuan");
                                }
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
                                try {
                                    Date date = format.parse(userModel.get(0).getDateBirth());
                                    SimpleDateFormat dateFormatterText = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
                                    tvAge.setText(dateFormatterText.format(date.getTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            tvEmail.setText(userModel.get(0).getEmail());
                            tvStatus.setText(userModel.get(0).getUserType());
                            if (userModel.get(0).getUrlPic() != null) {
                                if(Patterns.WEB_URL.matcher(userModel.get(0).getUrlPic()).matches()) {
                                    Glide.with(getActivity())
                                            .load(userModel.get(0).getUrlPic())
                                            .placeholder(R.color.chef)
                                            .into(imgProfile);
                                }else{
                                    byte[] imageByteArray = Base64.decode(userModel.get(0).getUrlPic(), Base64.DEFAULT);
                                    Glide.with(getActivity())
                                            .asBitmap()
                                            .load(imageByteArray)
                                            .placeholder(R.color.chef)
                                            .into(imgProfile);

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultStructureUser> call, Throwable t) {
                            Log.e("ErrorProfile", t.getMessage());
                        }
                    });

                    if (firebaseUser.getPhoneNumber() != null) {
                        tvPhone.setText(firebaseUser.getPhoneNumber());
                    }
                }
            }, 2500);
        }


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
             //   Toast.makeText(getActivity(), "Soon!", Toast.LENGTH_SHORT);
            }
        });


        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditName();
            }
        });

        btnEditGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditGender();
            }
        });

        btnEditAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditAge();
            }
        });

        materialButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


        return view;
    }

    private void showDialogEditName() {
        final EditText etEditName;
        Button btnEditName, btnCancel;
        final AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_edit_name, null);
        alert.setView(dialogView);
        alert.setTitle("Edit Nama Profil");

        etEditName = dialogView.findViewById(R.id.etEditName);
        btnEditName = dialogView.findViewById(R.id.btnEditName);
        btnCancel = dialogView.findViewById(R.id.btnCancel);

        etEditName.setText(tvName.getText().toString());
        etEditName.setSelection(tvName.getText().length());

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        alert.show();

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarEdit.setVisibility(View.VISIBLE);
                int ind = tvEmail.getText().toString().indexOf("@");
                etEditName.clearFocus();
                alert.cancel();
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                apiInterface.putUserName(tvEmail.getText().toString(), etEditName.getText().toString()).enqueue(new Callback<DefaultStructureUser>() {
                    @Override
                    public void onResponse(Call<DefaultStructureUser> call, Response<DefaultStructureUser> response) {
                        progressBarEdit.setVisibility(View.GONE);
//                        UserModel userModel = response.body().getData();
                        ArrayList<UserModel> userModels = response.body().getData();
                        tvName.setText(userModels.get(0).getUserName());
                    }

                    @Override
                    public void onFailure(Call<DefaultStructureUser> call, Throwable t) {
                        Log.e("PUT", t.getMessage());
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEditName.clearFocus();
                alert.cancel();
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

    }

    private void showDialogEditGender() {
        final RadioGroup radioGroup;
        final TextView textViewGender;
        final Button btnEditGender, btnCancel;
        final AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_edit_gender, null);
        alert.setView(dialogView);
        alert.setTitle("Edit Jenis Kelamin Profil");

        radioGroup = dialogView.findViewById(R.id.radioGroup);
        btnEditGender = dialogView.findViewById(R.id.btnEditGender);
        textViewGender = dialogView.findViewById(R.id.tvGender);
        btnCancel = dialogView.findViewById(R.id.btnCancel);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonMan) {
                    textViewGender.setText("Laki-laki");
                } else if (checkedId == R.id.radioButtonWoman) {
                    textViewGender.setText("Perempuan");
                }
            }
        });

        if (tvGender.getText().toString().equalsIgnoreCase("Laki-laki")) {
            RadioButton radioButton = dialogView.findViewById(R.id.radioButtonMan);
            radioButton.setChecked(true);
        }

        if (tvGender.getText().toString().equalsIgnoreCase("Perempuan")) {
            RadioButton radioButton = dialogView.findViewById(R.id.radioButtonWoman);
            radioButton.setChecked(true);
        }

//        etEditName.setText(tvName.getText().toString());
//        etEditName.setSelection(tvName.getText().length());

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        alert.show();

        btnEditGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGender.setText(textViewGender.getText());
                alert.dismiss();
//                progressBarEdit.setVisibility(View.VISIBLE);
//                int ind = tvEmail.getText().toString().indexOf("@");
//                etEditName.clearFocus();
//                alert.cancel();
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//                apiInterface.putUserName(tvEmail.getText().toString(), etEditName.getText().toString()).enqueue(new Callback<DefaultStructureUser>() {
//                    @Override
//                    public void onResponse(Call<DefaultStructureUser> call, Response<DefaultStructureUser> response) {
//                        progressBarEdit.setVisibility(View.GONE);
////                        UserModel userModel = response.body().getData();
//                        ArrayList<UserModel> userModels = response.body().getData();
//                        tvName.setText(userModels.get(0).getUserName());
//                    }
//
//                    @Override
//                    public void onFailure(Call<DefaultStructureUser> call, Throwable t) {
//                        Log.e("PUT", t.getMessage());
//                    }
//                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

    }

    private void showDialogEditAge() {
        final EditText etEditAge;
        Button btnEditAge, btnCancel;
        final AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_edit_age, null);
        alert.setView(dialogView);
        alert.setTitle("Edit Nama Profil");

        etEditAge = dialogView.findViewById(R.id.etEditAge);
        btnEditAge = dialogView.findViewById(R.id.btnEditAge);
        btnCancel = dialogView.findViewById(R.id.btnCancel);

        if (!tvAge.getText().toString().equalsIgnoreCase("-")) {
            etEditAge.setText(tvAge.getText().toString());
            etEditAge.setSelection(tvAge.getText().length());
        } else {
            etEditAge.setText("0");
            etEditAge.setSelection(tvAge.getText().length());
        }

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        alert.show();

        btnEditAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAge.setText(etEditAge.getText());
                alert.dismiss();
//                progressBarEdit.setVisibility(View.VISIBLE);
//                int ind = tvEmail.getText().toString().indexOf("@");
//                etEditName.clearFocus();
//                alert.cancel();
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//                apiInterface.putUserName(tvEmail.getText().toString(), etEditName.getText().toString()).enqueue(new Callback<DefaultStructureUser>() {
//                    @Override
//                    public void onResponse(Call<DefaultStructureUser> call, Response<DefaultStructureUser> response) {
//                        progressBarEdit.setVisibility(View.GONE);
////                        UserModel userModel = response.body().getData();
//                        ArrayList<UserModel> userModels = response.body().getData();
//                        tvName.setText(userModels.get(0).getUserName());
//                    }
//
//                    @Override
//                    public void onFailure(Call<DefaultStructureUser> call, Throwable t) {
//                        Log.e("PUT", t.getMessage());
//                    }
//                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEditAge.clearFocus();
                alert.cancel();
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Ambil Foto", "Ambil dari Galeri",
                "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Foto Profil");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil Foto")) {
                    try {
                        profileFile = new File(getActivity().getExternalCacheDir().toString()+"/Digimagz"
                                +System.currentTimeMillis());
                        Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext()
                                .getPackageName() + ".provider", profileFile);
                        //use standard intent to capture an image
                        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        captureIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivityForResult(captureIntent, REQUEST_CAMERA);
                    } catch (ActivityNotFoundException anfe) {
                        //display an error message
                        String errorMessage = "Whoops - your device doesn't support capturing images!";
                        Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (items[item].equals("Ambil dari Galeri")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public String saveBitmapToFile(Uri uri, boolean fromCamera) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);

            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(imageStream, null, o);

            imageStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            imageStream = getActivity().getContentResolver().openInputStream(uri);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(imageStream, null, o2);

            //Bitmap rotatedBMP = rotateImage(selectedBitmap, 90);

            String[] projection2 = new String[]{
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            int orientation = Constant.getOrientationFromURI(getContext(), Uri.parse(uri.getPath()));
            Bitmap rotatedBMP = rotateImage(selectedBitmap, orientation);

            imageStream.close();

            //FileOutputStream outputStream = new FileOutputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            rotatedBMP.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

            imgProfile.setImageBitmap(rotatedBMP);

            byte[] byteGambar = outputStream.toByteArray();

            if (fromCamera) {
                String[] projection = new String[]{
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.ORIENTATION,
                        MediaStore.Images.ImageColumns.DATE_TAKEN,
                        MediaStore.Images.ImageColumns.MIME_TYPE
                };
                final Cursor cursor2 = getActivity().getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                                null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

                String imageLocation = "";
                if (cursor2.moveToFirst()) {
                    imageLocation = cursor2.getString(1);

                    long id = cursor2.getLong(cursor2.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    getActivity().getContentResolver().delete(deleteUri, null, null);

                }
                cursor2.close();
            }

            return Base64.encodeToString(byteGambar,
                    Base64.NO_WRAP);
            //return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri mCropImageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);

                // For API >= 23 we need to check specifically that we have permissions to read external storage.
                if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)) {
                    // request permissions and handle the result in onRequestPermissionsResult()
                    mCropImageUri = imageUri;
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                } else {
                    // no permissions required or already grunted, can start crop image activity
                    startCropImageActivity(imageUri);
                }
            }else if(requestCode == REQUEST_CAMERA){
                Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext()
                        .getPackageName() + ".provider", profileFile);
                startCropImageActivity(uri);
            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    File compFile = null;
                    try {
                        compFile = new Compressor(getContext()).compressToFile(new File(result.getUri().getPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri comUri = Uri.fromFile(compFile);
                    final String base64 = saveBitmapToFile(comUri, requestCode == REQUEST_CAMERA ? true : false);

                    progressBar.setVisibility(View.VISIBLE);
                    String gender;
                    if (tvGender.getText().toString().equalsIgnoreCase("Laki-laki")){
                        gender = "L";
                    }else{
                        gender = "P";
                    }
                    String birthdate = tvAge.getText().toString();
                    SimpleDateFormat dateFormatterText = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
                    try {
                        Date date = dateFormatterText.parse(birthdate);
                        dateFormatterText = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
                        birthdate = dateFormatterText.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    apiInterface.putUser(tvEmail.getText().toString(), tvName.getText().toString(), base64, birthdate, gender).enqueue(new Callback<DefaultStructureUser>() {
                        @Override
                        public void onResponse(Call<DefaultStructureUser> call, Response<DefaultStructureUser> response) {
                            Log.e("PrifileUser", "Sukses");
                            ArrayList<UserModel> userModel = response.body().getData();
                            tvName.setText(userModel.get(0).getUserName());
                            if (userModel.get(0).getGender() != null && userModel.get(0).getDateBirth() != null) {
                                if (userModel.get(0).getGender().equalsIgnoreCase("L")) {
                                    tvGender.setText("Laki-laki");
                                } else if (userModel.get(0).getGender().equalsIgnoreCase("P")) {
                                    tvGender.setText("Perempuan");
                                }
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
                                try {
                                    Date date = format.parse(userModel.get(0).getDateBirth());
                                    SimpleDateFormat dateFormatterText = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
                                    tvAge.setText(dateFormatterText.format(date.getTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            tvEmail.setText(userModel.get(0).getEmail());
                            tvStatus.setText(userModel.get(0).getUserType());
                            if (userModel.get(0).getUrlPic() != null) {
                                if(Patterns.WEB_URL.matcher(userModel.get(0).getUrlPic()).matches()) {
                                    Glide.with(getActivity())
                                            .load(userModel.get(0).getUrlPic())
                                            .placeholder(R.color.chef)
                                            .into(imgProfile);
                                }else{
                                    byte[] imageByteArray = Base64.decode(userModel.get(0).getUrlPic(), Base64.DEFAULT);
                                    Glide.with(getActivity())
                                            .asBitmap()
                                            .load(imageByteArray)
                                            .placeholder(R.color.chef)
                                            .into(imgProfile);

                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<DefaultStructureUser> call, Throwable t) {
                            Log.e("ErrorProfile", t.getMessage());

                        }
                    });


                }
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(getContext(), this);
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        toMain();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ErrorSignOut", e.getMessage());
            }
        });
    }

    public void toMain() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        getActivity().finish();
    }
}
