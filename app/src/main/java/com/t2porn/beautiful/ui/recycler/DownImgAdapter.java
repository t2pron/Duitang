package com.t2porn.beautiful.ui.recycler;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.t2porn.beautiful.R;
import com.t2porn.beautiful.activity.PhotoActivity;
import com.t2porn.beautiful.bean.StaggerItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DownImgAdapter extends RecyclerView.Adapter<DownImgAdapter.MyViewHolder>{

    private  List<StaggerItem> ListData;
    Map<String,Integer> heightMap = new HashMap<>();
    static Map<String,Integer> widthMap = new HashMap<>();

    public DownImgAdapter(List<StaggerItem> listData){
        this.ListData = listData;
    }

    @Override
    public DownImgAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stagger_recy_item,
                parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DownImgAdapter.MyViewHolder holder, int position) {
//        Log.e("wg_log", "Listdata:" + ListData.get(position));
        final String url = ListData.get(position).getStrurl();
        //final long imageId = ListData.get(position).getID();
        if(heightMap.containsKey(url)){
            int height = heightMap.get(url);
            FLog.i("kaede", url+ "'s height = " + height);
            if(height > 0){
                updateItemtHeight(height, holder.itemView);
                holder.mSimpleDraweeView.setImageURI(Uri.parse(url));
                return;
            }
        }

    //监听加载过程
    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            if (imageInfo == null) {
                return;
            }

            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            if (qualityInfo.isOfGoodEnoughQuality()){
                int heightTarget = (int) getTargetHeight(imageInfo.getWidth(),imageInfo.getHeight(),holder.itemView,url);
                FLog.i("kaede", "heightTarget = " + heightTarget);
                if (heightTarget<=0)return;
                heightMap.put(url,heightTarget);
                updateItemtHeight(heightTarget, holder.itemView);
            }
        }

        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            Log.e("wg_log", "图片加载失败：" + id);
        }
    };

    DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(Uri.parse(url))
            .setControllerListener(controllerListener)
            .setTapToRetryEnabled(true)   //点击重新加载图片
            .build();

    holder.mSimpleDraweeView.setController(controller);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*KLog.e("TianGouLayoutAdapter", "ID值=" + imageId);
//                Log.e("wg_log", "ID值=" + imageId );*/
            }
        });
        holder.mSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoActivity.class);
                intent.putExtra("url" ,url);
                v.getContext().startActivity(intent);
            }
        });
    }

    private float getTargetHeight(float width,float height,View view, String url){
        View child = view.findViewById(R.id.stagger_image);
        float widthTarget;
        if (widthMap.containsKey(url)) widthTarget = widthMap.get(url);
        else {
            widthTarget = child.getMeasuredWidth();
            if (widthTarget>0){
                widthMap.put(url, (int) widthTarget);
            }
        }

        FLog.i("kaede","child.getMeasuredWidth() = " + widthTarget);
        return height * (widthTarget /width);
    }


    private void updateItemtHeight(int height, View view) {
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        View child = view.findViewById(R.id.stagger_image);

        CardView.LayoutParams layoutParams = (CardView.LayoutParams) child.getLayoutParams();
        layoutParams.height = height;
        cardView.updateViewLayout(child,layoutParams);
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private  SimpleDraweeView mSimpleDraweeView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView =  (SimpleDraweeView) itemView.findViewById(R.id.stagger_image);

//            mSimpleDraweeView.setAspectRatio(0.9F);
//            mSimpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        }
    }
}
