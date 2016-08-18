package com.example.slab.labrecycler.decor;

/**
 * 要使用VerticalStempItemDecoration， viewHoler需要集成这个接口
 * Created by hotstuNg on 2016/8/17.
 */
public interface Stepable {
    /**
     *
     * @return 完成度【0， 100】
     */
    float getCompleteFraction();
}
