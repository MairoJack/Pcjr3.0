package com.pcjinrong.pcjr.api;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.data.DataManager;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mario on 2016/7/14.
 */
public class TokenAuthenticator implements Authenticator {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    public DataManager mDataManager = DataManager.getInstance();

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        this.mCompositeSubscription.add(this.mDataManager.refreshToken("123")
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(Token token) {
                        Logger.d(token);
                    }
                }));
    return null;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
