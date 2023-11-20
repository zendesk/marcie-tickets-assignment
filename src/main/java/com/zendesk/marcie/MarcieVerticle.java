package com.zendesk.marcie;

import com.zendesk.core.logging.ZendeskLogger;
import com.zendesk.core.starter.BaseStarterVerticle;
import com.zendesk.core.starter.FrameworkModuleProvider;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.util.Collections;
import java.util.Set;

public class MarcieVerticle extends BaseStarterVerticle {

  public static final String TYPE = "Example Custom Verticle";
  private static final ZendeskLogger log = ZendeskLogger.forEnclosingClass();

  MarcieVerticle(Set<Object> extensions) {
    super(extensions);
  }

  @Override
  protected FrameworkModuleProvider modules() {
    return Collections::emptyList;
  }

  @Override
  public Future<Void> start(Vertx vertx) {

    log.atInfo().log("Your custom verticle code runs here...");
    return Future.succeededFuture();
  }

  @Override
  public Future<Void> stop(Vertx vertx) {
    return Future.succeededFuture();
  }

  @Override
  protected String type() {
    return TYPE;
  }
}
