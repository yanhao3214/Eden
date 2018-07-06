package com.gogo.haobutler.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gogo.haobutler.R;
import com.gogo.haobutler.callback.EdenNetCallback;
import com.gogo.haobutler.model.user.User;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.activity.AttributionActivity;
import com.gogo.haobutler.activity.DistributionActivity;
import com.gogo.haobutler.activity.LoginActivity;
import com.gogo.haobutler.utils.Consts;
import com.gogo.haobutler.utils.HttpCenter;
import com.gogo.haobutler.view.HaoDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function:
 * @problem: App启动时屏幕旋转，则会另外生成MainActivity
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final int TAKE_PHOTO = 101;
    private static final int CROP_PHOTO = 102;
    private static final int CHOOSE_PHOTO = 103;
    private static final int PERMISSION_REQUEST_ALBUM = 104;
    private View view;
    private CircleImageView civProfile;
    private EditText edtName;
    private EditText edtGender;
    private EditText edtAge;
    private EditText edtDesc;
    private Button btnModify;
    private ImageView ivBg;

    private User currentUser;
    private HaoDialog dialog;
    private Uri imageUri;
    private Bitmap mBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        initData();
        initView();
        return view;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        civProfile = view.findViewById(R.id.civ_profile);
        edtName = view.findViewById(R.id.edt_name);
        edtGender = view.findViewById(R.id.edt_gender);
        edtAge = view.findViewById(R.id.edt_age);
        edtDesc = view.findViewById(R.id.edt_desc);
        btnModify = view.findViewById(R.id.btn_do_modify);
        TextView tvDistribution = view.findViewById(R.id.tv_distribution);
        TextView tvAttribution = view.findViewById(R.id.tv_attribution);
        Button btnEdit = view.findViewById(R.id.btn_edit_mine);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        ivBg = view.findViewById(R.id.iv_mine_bg);

        dialog = new HaoDialog(getActivity(), 0, 0, R.layout.dialog_mine_layout,
                R.style.theme_mine_dialog, Gravity.BOTTOM, 0);
        dialog.setCancelable(false);
        Button btnCamera = dialog.findViewById(R.id.btn_camera);
        Button btnAlbum = dialog.findViewById(R.id.btn_album);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        civProfile.setOnClickListener(this);
        tvDistribution.setOnClickListener(this);
        tvAttribution.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        setProfileBackground();

        setEnabled(false);
        currentUser = BmobUser.getCurrentUser(User.class);
        setInfo(currentUser);

        String imagePath = context.getExternalCacheDir() + "/profile.jpg";
        mBitmap = BitmapFactory.decodeFile(imagePath);
        if (mBitmap != null) {
            civProfile.setImageBitmap(mBitmap);
        } else {
            civProfile.setImageResource(R.drawable.mine_add_pic);
        }
    }

    private void setProfileBackground() {
        HttpCenter.getBingPic(new EdenNetCallback() {
            @Override
            public void ok(String json) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> Glide.with(context).load(json).into(ivBg));
                }
            }

            @Override
            public void error(String eMsg) {
            }
        });
    }

    /**
     * 设置用户信息
     *
     * @param user
     */
    private void setInfo(User user) {
        if (user != null) {
            edtName.setText(user.getUsername());
            edtGender.setText(user.getGender() == 0 ?
                    getString(R.string.mine_male) : getString(R.string.mine_female));
            edtAge.setText(String.valueOf(user.getAge()));
            edtDesc.setText(user.getDescription());
        }
    }

    private void setEnabled(boolean flag) {
        edtName.setEnabled(flag);
        edtGender.setEnabled(flag);
        edtAge.setEnabled(flag);
        edtDesc.setEnabled(flag);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_profile:
                dialog.show();
                break;
            case R.id.btn_camera:
                openCamera();
                break;

            /**
             * 获取权限，打开相册
             */
            case R.id.btn_album:
                if (ContextCompat.checkSelfPermission(context, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_ALBUM);
                } else {
                    openAlbum();
                }
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_distribution:
                startActivity(new Intent(getActivity(), DistributionActivity.class));
                break;
            case R.id.tv_attribution:
                startActivity(new Intent(getContext(), AttributionActivity.class));
                break;
            case R.id.btn_edit_mine:
                setEnabled(true);
                btnModify.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_do_modify:
                updateUser();
                currentUser = BmobUser.getCurrentUser(User.class);
                break;
            case R.id.btn_logout:
                BmobUser.logOut();
                currentUser = BmobUser.getCurrentUser(User.class);
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void openCamera() {
        // TODO: 2018/5/22 0022 图片保存到本地，下次进入图片不变
        File file = new File(context.getExternalCacheDir(), "profile.jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(file);
        Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentCamera, TAKE_PHOTO);
    }

    private void openAlbum() {
        Intent intentAlbum = new Intent("android.intent.action.GET_CONTENT");
        intentAlbum.setType("image/*");
        startActivityForResult(intentAlbum, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //启动相机剪裁程序
                    Intent cropIntent = new Intent("com.android.camera.action.CROP");
                    cropIntent.setDataAndType(imageUri, "image/*");
                    cropIntent.putExtra("scale", true);
                    cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cropIntent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        InputStream is = context.getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    civProfile.setImageBitmap(bitmap);
                    dialog.dismiss();
                }
                break;
            case CHOOSE_PHOTO:
                // TODO: 2018/5/22 0022 版本适配和Uri解析
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        try {
                            bitmap = BitmapFactory.decodeStream(context.getContentResolver()
                                    .openInputStream(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        civProfile.setImageBitmap(bitmap);
                        dialog.dismiss();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(context, R.string.mine_album_no_permission, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void updateUser() {
        User newUser = new User();
        String username = edtName.getText().toString().trim();
        String gender = edtGender.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(age)) {
            Toast.makeText(context, getString(R.string.input_no_empty), Toast.LENGTH_SHORT).show();
        } else {
            newUser.setUsername(username);
            newUser.setGender(gender.equals(getString(R.string.mine_male))
                    ? Consts.USER_MALE : Consts.USER_FEMALE);
            newUser.setAge(Integer.parseInt(age));
            newUser.setDescription(TextUtils.isEmpty(desc)
                    ? getString(R.string.auto_user_desc) : desc);
        }
        newUser.update(currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    btnModify.setVisibility(View.GONE);
                    setEnabled(false);
                    Toast.makeText(context, R.string.mine_modify_ok, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.mine_modify_failed + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        ShareUtil.putImage(context, "profile_image", civProfile);
    }
}
