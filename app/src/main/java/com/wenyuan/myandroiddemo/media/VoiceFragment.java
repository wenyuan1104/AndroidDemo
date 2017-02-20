package com.wenyuan.myandroiddemo.media;


import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoiceFragment extends BaseFragment implements View.OnClickListener {

    private TextView mLocalTxt, mOnlineTxt, mVoiceTxt;
    private MediaPlayer mMediaPlayer;

    public VoiceFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_voice;
    }

    @Override
    protected void initView(View view) {
        mLocalTxt = (TextView) view.findViewById(R.id.text_show_music);
        mLocalTxt.setOnClickListener(this);
        mOnlineTxt = (TextView) view.findViewById(R.id.text_show_online_voice);
        mOnlineTxt.setOnClickListener(this);
        mVoiceTxt = (TextView) view.findViewById(R.id.text_show_sound);
        mVoiceTxt.setOnClickListener(this);
    }

    @Override
    protected void initObject() {
        //mMediaPlayer = new MediaPlayer();
        //try {
        //    mMediaPlayer.prepare();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_show_music:
                break;
            case R.id.text_show_online_voice:
                break;
            case R.id.text_show_sound:
                break;
        }
    }
}
