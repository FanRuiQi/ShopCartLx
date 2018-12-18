package fanruiqi.bwie.com.shopcartlx.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import fanruiqi.bwie.com.shopcartlx.R;

public class AddSubView extends LinearLayout implements View.OnClickListener{

    private int number=1;
    private TextView mText_jian;
    private TextView mText_num;
    private TextView mText_jia;

    public AddSubView(Context context) {
        this(context,null);
    }

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(context, R.layout.add_remove, this);
        mText_jian = view.findViewById(R.id.sub_jian);
        mText_num = view.findViewById(R.id.sub_num);
        mText_jia = view.findViewById(R.id.sub_jia);

        mText_jian.setOnClickListener(this);
        mText_jia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.sub_jian:

                if (number>1){
                    --number;
                    mText_num.setText(number+"");

                    mOnNumberChangeListener.onNumberChange(number);

                }else {
                    Toast.makeText(getContext(),"不能再少了",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sub_jia:

                    ++number;
                    mText_num.setText(number+"");
                    mOnNumberChangeListener.onNumberChange(number);

                break;
        }
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number=number;
        mText_num.setText(number+"");
    }

    public interface onNumberChangeListener{
        void onNumberChange(int num);
    }

    onNumberChangeListener mOnNumberChangeListener;

    public void setOnNumberChangeListener(onNumberChangeListener onNumberChangeListener){
        mOnNumberChangeListener=onNumberChangeListener;
    }
}
