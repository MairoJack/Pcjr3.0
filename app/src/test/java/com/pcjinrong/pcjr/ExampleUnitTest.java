package com.pcjinrong.pcjr;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Token;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        /*Token token1 = new Token();
        token1.setAccess_token("token1");

        Token token2 = new Token();
        token2.setAccess_token("token2");

        Product p1 = new Product();
        p1.setName("mario");

        Product p2 = new Product();
        p2.setName("zilong");

        Product[] products = {p1, p2};
        Token[] str = {token1, token2};
        Observable.from(str)
                .flatMap(token-> Observable.from(products))
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        //observable.zipWith()
                        return null;
                    }
                })
                .subscribe(s -> System.out.println(s.getName()));*/



        Observable.create((Subscriber<? super String> s) -> {
            System.out.println("subscribing");
            s.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts -> {
            return attempts.zipWith(Observable.range(1, 3), (n, i) -> i).flatMap(i -> {
                System.out.println("delay retry by " + i + " second(s)");
                return Observable.timer(i, TimeUnit.SECONDS);
            });
        }).toBlocking().forEach(System.out::println);

    }



}