package io.github.hotstu.rxdemo.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * @author hglf
 * @since 2018/1/9
 */
public class DownloadProgressFlowable {
    public static Flowable<Long> create() {
        return Flowable.create(new FlowableOnSubscribe<Long>() {
            @Override
            public void subscribe(FlowableEmitter<Long> e) throws Exception {
                e.onNext(1L);
                e.onNext(2L);
                e.onNext(3L);
                e.onComplete();
            }
        }, BackpressureStrategy.LATEST);
    }
}
