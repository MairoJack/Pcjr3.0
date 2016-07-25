package com.pcjinrong.pcjr.data;


import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.model.impl.ApiModel;
import com.pcjinrong.pcjr.utils.RxUtils;

import java.util.List;

import rx.Observable;


/**
 * Created by Mario on 2016/7/8.
 */
public class DataManager {

    private static DataManager dataManager;

    private ApiModel apiModel;

    public synchronized static DataManager getInstance(){
        if(dataManager == null){
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public Observable<BaseBean<List<Product>>> getIndexProductList() {
        return this.apiModel.getIndexProductList()
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }
    private DataManager(){
        this.apiModel = ApiModel.getInstance();
    }

}
