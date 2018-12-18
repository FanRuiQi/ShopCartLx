package fanruiqi.bwie.com.shopcartlx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import fanruiqi.bwie.com.shopcartlx.R;
import fanruiqi.bwie.com.shopcartlx.adapter.MyAdapter;
import fanruiqi.bwie.com.shopcartlx.bean.ResponseBean;
import fanruiqi.bwie.com.shopcartlx.util.OkUtils;

public class Afrag extends Fragment implements View.OnClickListener{

    private ExpandableListView mExpandableListView;
    private CheckBox mCheckBox;
    private TextView mText_price;
    private Button mButton;
    MyAdapter adapter;
    public String url="http://www.zhaoapi.cn/product/getCarts?uid=71";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.afrag, null);

        mExpandableListView = view.findViewById(R.id.af_elv);
        mCheckBox = view.findViewById(R.id.af_check);
        mText_price = view.findViewById(R.id.af_price);
        mButton = view.findViewById(R.id.af_btn);

        mCheckBox.setOnClickListener(this);

        initData();
        return view;
    }

    private void initData() {

        OkUtils.getInstance().doGet(url, new OkUtils.OnCallBack() {
            @Override
            public void onFail() {

            }

            @Override
            public void onResponse(String json) {

                ResponseBean responseBean = new Gson().fromJson(json, ResponseBean.class);
                List<ResponseBean.DataBean> data = responseBean.getData();
                adapter = new MyAdapter(getActivity(), data);

                mExpandableListView.setAdapter(adapter);

                for (int p=0;p<data.size();p++){
                    mExpandableListView.expandGroup(p);
                }

                adapter.setOnListChangeListener(new MyAdapter.onListChangeListener() {
                    @Override
                    public void onSjCheckedChange(int i) {
                        boolean selected = adapter.getSjAllSpSelected(i);
                        adapter.sjChangeSp(i,!selected);
                        adapter.notifyDataSetChanged();
                        refresh();
                    }

                    @Override
                    public void onSpCheckedChange(int i, int i1) {

                        adapter.changeSp(i,i1);
                        adapter.notifyDataSetChanged();
                        refresh();
                    }

                    @Override
                    public void onNumberCheckedChange(int i, int i1, int num) {

                        adapter.changeNumber(i,i1,num);
                        adapter.notifyDataSetChanged();
                        refresh();
                    }
                });
            }
        });
    }

    public void refresh(){

        boolean allSpSelected = adapter.getAllSpSelected();
        mCheckBox.setChecked(allSpSelected);

        int totalNum = adapter.getTotalNum();
        mButton.setText("结算("+totalNum+")");

        float totalPrice = adapter.getTotalPrice();
        mText_price.setText("总价￥:"+totalPrice);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.af_check:

                boolean spSelected = adapter.getAllSpSelected();
                adapter.qxChangeAllSp(!spSelected);
                adapter.notifyDataSetChanged();
                refresh();
                break;
        }
    }
}
