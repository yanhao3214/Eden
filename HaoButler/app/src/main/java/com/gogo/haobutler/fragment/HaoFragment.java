package com.gogo.haobutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.adapter.ChatAdapter;
import com.gogo.haobutler.model.chat.ChatMsg;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;

import java.util.ArrayList;
import java.util.List;

import static com.gogo.haobutler.model.chat.ChatMsg.CHAT_ITEM_LEFT;
import static com.gogo.haobutler.model.chat.ChatMsg.CHAT_ITEM_RIGHT;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function: 小浩陪聊
 * 1.头像随性别变化
 */

public class HaoFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private ListView mListView;
    private TitanicTextView titanicEden;
    private EditText edtInput;
    private Button btnSend;
    private ChatAdapter mAdapter;
    private List<ChatMsg> mMsgList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_butler_layout, container, false);
        initView();
        initData();
        return view;
    }

    @Override
    public void initView() {
        mListView = view.findViewById(R.id.lv_chat);
        titanicEden = view.findViewById(R.id.titanic_eden);
        edtInput = view.findViewById(R.id.edt_input);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        //初始化动态背景TitanicTextView
        com.yh.sdk.utils.TextUtils.setFont(context, titanicEden);
        new Titanic().start(titanicEden);
    }

    @Override
    public void initData() {
        mAdapter = new ChatAdapter(getContext(), mMsgList);
        mListView.setAdapter(mAdapter);
        addLeftChatItem(getString(R.string.hao_hello));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                /**
                 * 1.获取edtInput内容
                 * 2.清除edtInput内容
                 * 3.往mListView添加数据，列表刷新、置底
                 * 4.发送网络请求，获取并解析数据
                 * 5.往mListView添加数据，列表刷新、置底
                 */
                String userText = edtInput.getText().toString();
                if (!TextUtils.isEmpty(userText)) {
                    addRightChatItem(userText);
                    // TODO: 2018/6/15 0015 接入聚合问答机器人api
//                    String url = "";
//                    HttpUtil.getRequest(url, new HttpCallback() {
//                        @Override
//                        public void onSuccess(String response) {
//                            if (TextUtils.isEmpty(response)) {
//                                Toast.makeText(context, R.string.hao_no_answer, Toast.LENGTH_SHORT).show();
//                            } else {
//                                addLeftChatItem("系统维护中。。。");
//                            }
//                        }
//
//                        @Override
//                        public void onFail(Exception e) {
//                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    addLeftChatItem("系统维护中。。。");
                }
                break;
            default:
                break;
        }
    }

    private void addLeftChatItem(String haoText) {
        ChatMsg haoMsg = new ChatMsg(CHAT_ITEM_LEFT, haoText);
        mMsgList.add(haoMsg);
        mAdapter.notifyDataSetChanged();
        mListView.smoothScrollToPosition(mMsgList.size() - 1);
    }

    private void addRightChatItem(String userText) {
        edtInput.setText("");
        ChatMsg userMsg = new ChatMsg(CHAT_ITEM_RIGHT, userText);
        mMsgList.add(userMsg);
        mAdapter.notifyDataSetChanged();
        mListView.smoothScrollToPosition(mMsgList.size() - 1);
    }

    @Override
    public void onStop() {
        super.onStop();
//        new Titanic().cancel();
    }

    private void animTest() {
//        TextView tvAlpha = view.findViewById(R.id.tv_alpha);
//        Animation alpha = AnimationUtils.loadAnimation(getActivity(), R.anim.test_anim_set);
//        tvAlpha.setAnimation(alpha);
//        tvAlpha.startAnimation(alpha);
//
//        final ImageView ivCombine = view.findViewById(R.id.iv_combine);
//        final Animation animCombine = AnimationUtils.loadAnimation(getContext(), R.anim.test_anim_alpha);
//        ivCombine.startAnimation(animCombine);
//
//        Button btn3 = view.findViewById(R.id.btn_translate);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ivCombine.startAnimation(animCombine);
//            }
//        });
//        Animation animBtn = new TranslateAnimation(0, 1.0f, 0, 1.0f);
//        animBtn.setDuration(2000);
//        btn3.startAnimation(animBtn);
//        EditText edt = view.findViewById(R.id.edt_scale);
//        edt.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.test_anim_edit));
    }
}
