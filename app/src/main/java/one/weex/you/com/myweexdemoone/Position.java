package one.weex.you.com.myweexdemoone;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class Position implements RecyclerViewAdapter.Item{
    private int TYPE = 2;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTYPE(int type){
        TYPE = type;
    }
    @Override
    public int getItemType() {
        return TYPE;
    }
}
