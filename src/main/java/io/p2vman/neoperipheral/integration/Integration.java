package io.p2vman.neoperipheral.integration;

import io.p2vman.myservice.Metadata;
import io.p2vman.myservice.MyService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@MyService("neo_integration")
public @interface Integration {
    @Metadata
    String modid();
}
