package com.zendesk.marcie;

import com.zendesk.core.starter.BaseVerticleBuilder;
import com.zendesk.core.starter.TypedVerticleBuilder;
import io.vertx.core.Verticle;

public class MarcieVerticleBuilder extends BaseVerticleBuilder<MarcieVerticleBuilder> implements
    TypedVerticleBuilder {

  private MarcieVerticleBuilder(Object... modules) {
    with(modules);
  }

  public static MarcieVerticleBuilder from(Object... modules) {
    return new MarcieVerticleBuilder(modules);
  }

  @Override
  public Class<? extends Verticle> instanceType() {
    return MarcieVerticle.class;
  }

  @Override
  public Verticle get() {
    return new MarcieVerticle(extensions());
  }
}
