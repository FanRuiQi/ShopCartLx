package fanruiqi.bwie.com.shopcartlx.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fanruiqi.bwie.com.shopcartlx.R;
import fanruiqi.bwie.com.shopcartlx.bean.ResponseBean;
import fanruiqi.bwie.com.shopcartlx.view.AddSubView;

public class MyAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private List<ResponseBean.DataBean> list;

    public MyAdapter(Context context, List<ResponseBean.DataBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getList().size();
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {

        GroupViewHolder groupViewHolder;
        if (view==null){

            groupViewHolder = new GroupViewHolder();
            view = View.inflate(viewGroup.getContext(), R.layout.item_car_group, null);
            groupViewHolder.mCheckBox = view.findViewById(R.id.group_check);
            groupViewHolder.mTextView = view.findViewById(R.id.group_text);
            view.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }

        ResponseBean.DataBean dataBean = list.get(i);
        groupViewHolder.mTextView.setText(dataBean.getSellerName());

        boolean sjAllSpSelected = getSjAllSpSelected(i);
        groupViewHolder.mCheckBox.setChecked(sjAllSpSelected);

        groupViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnListChangeListener.onSjCheckedChange(i);
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {

        ChildViewHolder childViewHolder;
        if (view==null){

            childViewHolder = new ChildViewHolder();
            view = View.inflate(viewGroup.getContext(), R.layout.item_car_chlid, null);
            childViewHolder.mCheckBox = view.findViewById(R.id.child_check);
            childViewHolder.mImageView = view.findViewById(R.id.child_img);
            childViewHolder.mTextView_title = view.findViewById(R.id.child_title);
            childViewHolder.mTextView_price = view.findViewById(R.id.child_price);
            childViewHolder.mAddSubView = view.findViewById(R.id.child_add);
            view.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        ResponseBean.DataBean dataBean = list.get(i);
        List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
        ResponseBean.DataBean.ListBean listBean = list1.get(i1);
        childViewHolder.mCheckBox.setChecked(listBean.getSelected()==1);
        childViewHolder.mTextView_title.setText(listBean.getTitle());
        childViewHolder.mTextView_price.setText(listBean.getPrice()+"");
        childViewHolder.mAddSubView.setNumber(listBean.getNum());

        childViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnListChangeListener.onSpCheckedChange(i,i1);
            }
        });

        childViewHolder.mAddSubView.setOnNumberChangeListener(new AddSubView.onNumberChangeListener() {
            @Override
            public void onNumberChange(int num) {
                mOnListChangeListener.onNumberCheckedChange(i,i1,num);
            }
        });
        return view;
    }

    public boolean getSjAllSpSelected(int i){     //获取复选框被点击的商家全部商品状态
        ResponseBean.DataBean dataBean = list.get(i);
        List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
        for (ResponseBean.DataBean.ListBean bean:list1){

            if (bean.getSelected()==0){
                return false;
            }
        }

        return true;
    }

    public boolean getAllSpSelected(){      //全选点击时获取所有商品状态
        for (int i=0;i<list.size();i++){
            ResponseBean.DataBean dataBean = list.get(i);
            List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
            for (int j=0;j<list1.size();j++){

                if (list1.get(j).getSelected()==0){
                    return false;
                }
            }
        }
        return true;
    }

    public int getTotalNum(){   //获取总数量
        int totalNumber=0;
        for (int i=0;i<list.size();i++){
            ResponseBean.DataBean dataBean = list.get(i);
            List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
            for (int j=0;j<list1.size();j++){

                if (list1.get(j).getSelected()==1){
                    int num = list1.get(j).getNum();
                    totalNumber+=num;
                }
            }
        }
        return totalNumber;
    }

    public float getTotalPrice(){   //获取总价格
        float totalPrice=0;
        for (int i=0;i<list.size();i++){
            ResponseBean.DataBean dataBean = list.get(i);
            List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
            for (int j=0;j<list1.size();j++){

                if (list1.get(j).getSelected()==1){
                    int num = list1.get(j).getNum();
                    float price = (float) list1.get(j).getPrice();
                    totalPrice+=num*price;
                }
            }
        }
        return totalPrice;
    }

    public void sjChangeSp(int i,boolean isSelected){  //商家改变旗下所有商品状态

        ResponseBean.DataBean dataBean = list.get(i);
        List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
        for (int j=0;j<list1.size();j++){

            ResponseBean.DataBean.ListBean listBean = list1.get(j);
            listBean.setSelected(isSelected?1:0);
        }
    }

     public void changeSp(int i,int i1){
         ResponseBean.DataBean dataBean = list.get(i);
         List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
         ResponseBean.DataBean.ListBean listBean = list1.get(i1);
         listBean.setSelected(listBean.getSelected()==0?1:0);
     }

     public void qxChangeAllSp(boolean isSelected){       //全选改变商品是否选中

         for (int i=0;i<list.size();i++){
             ResponseBean.DataBean dataBean = list.get(i);
             List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
             for (int j=0;j<list1.size();j++){
                 list1.get(j).setSelected(isSelected?1:0);
             }
         }
     }

    public void changeNumber(int i,int i1,int number){   //改变数量
        ResponseBean.DataBean dataBean = list.get(i);
        List<ResponseBean.DataBean.ListBean> list1 = dataBean.getList();
        ResponseBean.DataBean.ListBean listBean = list1.get(i1);
        listBean.setNum(number);
    }

    public interface onListChangeListener{
        void onSjCheckedChange(int i);
        void onSpCheckedChange(int i,int i1);
        void onNumberCheckedChange(int i,int i1,int number);
    }

    onListChangeListener mOnListChangeListener;

    public void setOnListChangeListener(onListChangeListener onListChangeListener){
        mOnListChangeListener=onListChangeListener;
    }


    class GroupViewHolder{
        CheckBox mCheckBox;
        TextView mTextView;

    }

    class ChildViewHolder{
        CheckBox mCheckBox;
        ImageView mImageView;
        TextView mTextView_title;
        TextView mTextView_price;
        AddSubView mAddSubView;
    }


    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
