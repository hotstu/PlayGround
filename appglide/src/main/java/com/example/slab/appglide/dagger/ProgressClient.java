package com.example.slab.appglide.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by hotstuNg on 2016/7/26.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ProgressClient {
}
