package com.wenyuan.myademo.encrypt;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;
import com.wenyuan.myademo.encrypt.utils.RSAUtil;
import com.wenyuan.myademo.utils.AlertDialogV7Factory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class EncyptActivity extends BaseActivity implements View.OnClickListener {

    private Button mButMd5;
    private EditText mEditMd5In;
    private TextView mTextMd5Show;
    private TextView mTextPublicKey;
    private TextView mTextPrivateKey;
    private EditText mEditRsaIn;
    private Button mButRsaEncode;
    private TextView mTextShowEncode;
    private Button mButRsaDecode;
    private TextView mTextShowRsaDecode;
    private EditText mEditRsaOut;
    private EditText mEditBase64In;
    private Button mButBase64Encode;
    private TextView mTextShowBase64Ecode;
    private EditText mEditBase64Out;
    private Button mButBase64Decode;
    private TextView mTextShowBase64Decode;

    private RSAUtil mRSAUtil;
    private AlertDialogV7Factory mDialogFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_encypt);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mButMd5 = (Button) findViewById(R.id.but_md5);
        mButMd5.setOnClickListener(this);
        mEditMd5In = (EditText) findViewById(R.id.edit_md5_in);
        mEditMd5In.setOnClickListener(this);
        mTextMd5Show = (TextView) findViewById(R.id.text_md5_show);
        mTextMd5Show.setOnClickListener(this);
        mTextPublicKey = (TextView) findViewById(R.id.text_public_key);
        mTextPublicKey.setOnClickListener(this);
        mTextPrivateKey = (TextView) findViewById(R.id.text_private_key);
        mTextPrivateKey.setOnClickListener(this);
        mEditRsaIn = (EditText) findViewById(R.id.edit_rsa_in);
        mEditRsaIn.setOnClickListener(this);
        mButRsaEncode = (Button) findViewById(R.id.but_rsa_encode);
        mButRsaEncode.setOnClickListener(this);
        mTextShowEncode = (TextView) findViewById(R.id.text_show_encode);
        mTextShowEncode.setOnClickListener(this);
        mButRsaDecode = (Button) findViewById(R.id.but_rsa_decode);
        mButRsaDecode.setOnClickListener(this);
        mTextShowRsaDecode = (TextView) findViewById(R.id.text_show_rsa_decode);
        mTextShowRsaDecode.setOnClickListener(this);
        mEditRsaOut = (EditText) findViewById(R.id.edit_rsa_out);
        mEditRsaOut.setOnClickListener(this);
        mEditBase64In = (EditText) findViewById(R.id.edit_base64_in);
        mEditBase64In.setOnClickListener(this);
        mButBase64Encode = (Button) findViewById(R.id.but_base64_encode);
        mButBase64Encode.setOnClickListener(this);
        mTextShowBase64Ecode = (TextView) findViewById(R.id.text_show_base64_ecode);
        mTextShowBase64Ecode.setOnClickListener(this);
        mEditBase64Out = (EditText) findViewById(R.id.edit_base64_out);
        mEditBase64Out.setOnClickListener(this);
        mButBase64Decode = (Button) findViewById(R.id.but_base64_decode);
        mButBase64Decode.setOnClickListener(this);
        mTextShowBase64Decode = (TextView) findViewById(R.id.text_show_base64_decode);
        mTextShowBase64Decode.setOnClickListener(this);
        mButRsaDecode.setEnabled(false);
        mButRsaEncode.setEnabled(false);
    }

    @Override
    protected void initData() {
        mDialogFactory = new AlertDialogV7Factory(mContext);
        // RSA算法比较耗时
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRSAUtil = new RSAUtil();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mTextPublicKey.append(mRSAUtil.getRSAPublicKey().toString());
                            KLog.d(mRSAUtil.getRSAPublicKey().toString());
                            mTextPrivateKey.append(mRSAUtil.getRSAPrivateKey().toString());
                            KLog.d(mRSAUtil.getRSAPrivateKey().toString());
                            mButRsaDecode.setEnabled(true);
                            mButRsaEncode.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_encryption, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_issue:
                mDialogFactory.showTextDialog("相关问题", getResources().getString(R.string.issue_encypt), true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_md5:
                decodeOfMD5();
                break;
            case R.id.but_rsa_encode:
                encodeOfRSA();
                break;
            case R.id.but_rsa_decode:
                decodeOfRSA();
                break;
            case R.id.but_base64_encode:
                encodeOfBase64();
                break;
            case R.id.but_base64_decode:
                decodeOfBase64();
                break;
        }
    }

    /**
     * 使用base64进行数据解密
     */
    private void decodeOfBase64() {
        String data = mEditBase64Out.getText().toString();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 使用android自带的base64 解密
        mTextShowBase64Decode.setText("解密后：" + new String(Base64.decode(data, Base64.DEFAULT)));
    }

    /**
     * 使用base64进行数据加密
     */
    private void encodeOfBase64() {
        String data = mEditBase64In.getText().toString();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        // android自带的base64
        String cilpherText = null;
        try {
            cilpherText = Base64.encodeToString(data.getBytes("utf-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //try {
        //    cilpherText = new String(Base64.encode(data.getBytes("utf-8"), Base64.DEFAULT), "utf-8");
        //} catch (UnsupportedEncodingException e) {
        //    e.printStackTrace();
        //}
        mTextShowBase64Ecode.setText("加密后：" + cilpherText);
        mEditBase64Out.setText(cilpherText);
    }

    /**
     * 使用RSA解密算法 解密
     */
    private void decodeOfRSA() {
        String data = mEditRsaOut.getText().toString();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "使用Base64进行传输避免byte[]和String转换不对的问题\n" + data, Toast.LENGTH_LONG).show();
        try {
            // 获得私钥
            RSAPrivateKey privateKey = mRSAUtil.getRSAPrivateKey();
            // 解密
            //String data = new String(cipherText);
            //mTextShowRsaDecode.setText(String.valueOf(cipherText.length) + "\n");
            //mTextShowRsaDecode.append(String.valueOf(data.getBytes(Charset.forName("utf-8")).length) + "\n");
            //for (int i = 0; i < cipherText.length; i++) {
            //    byte t = cipherText[i];
            //    byte d = (data.getBytes("ASCII"))[i];
            //    mTextShowRsaDecode.append(String.valueOf(t == d) + "\n");
            //}
            byte[] txt = mRSAUtil.decrypt(privateKey, Base64.decode(data, Base64.DEFAULT));
            mTextShowRsaDecode.setText("解密后：" + new String(txt));
            KLog.d("RSA解密后：" + new String(txt, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用RSA加密算法加密
     */
    private void encodeOfRSA() {
        String data = mEditRsaIn.getText().toString();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // 获得公钥
            RSAPublicKey publicKey = mRSAUtil.getRSAPublicKey();
            // 加密
            byte[] cipherText = mRSAUtil.encode(publicKey, data.getBytes());
            // 显示 TODO 在经过base64加密后传输 为解决在byte[]和String转换后不相同的问题
            mTextShowEncode.setText("加密后：" + new String(cipherText, "utf-8"));
            mEditRsaOut.setText(Base64.encodeToString(cipherText, Base64.DEFAULT));
            KLog.d("RSA加密后：" + new String(cipherText, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用MD5进行数据加密，此加密方式是不可逆的。
     */
    private void decodeOfMD5() {
        String data = mEditMd5In.getText().toString();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        //获得明文的字节数组
        byte byteIn[] = data.getBytes();
        try {
            //获得MD5摘要算法的 MessageDigest对象
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            digest.update(byteIn);
            //获得密文
            byte mds[] = digest.digest();
            //把密文转换成十六进制的字符串形式
            int j = mds.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte md = mds[i];
                str[k++] = hexDigits[md >>> 4 & 0xf];
                str[k++] = hexDigits[md & 0xf];
            }
            //显示
            mTextMd5Show.setText("32位密文：" + String.valueOf(str));
            mTextMd5Show.append("\n16位密文：" + String.valueOf(str).substring(8, 24));
            KLog.d("32位密文：" + String.valueOf(str));
            KLog.d("16位密文：" + String.valueOf(str).substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            mTextMd5Show.setText(e.getMessage());
        }
    }
}














