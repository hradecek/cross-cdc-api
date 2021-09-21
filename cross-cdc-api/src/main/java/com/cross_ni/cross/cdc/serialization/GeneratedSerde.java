package com.cross_ni.cross.cdc.serialization;

public @interface GeneratedSerde {

    SerdeType[] value() default { SerdeType.JSON };
}
