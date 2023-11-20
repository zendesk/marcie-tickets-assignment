package com.zendesk.marcie;

import com.zendesk.core.health.AsyncHealthCheck;
import com.zendesk.core.inject.IncludeBeans;
import com.zendesk.core.inject.StarterExtension;
import com.zendesk.core.inject.StarterExtensionBuilder;
import com.zendesk.marcie.data.DataSupport;
import com.zendesk.marcie.health.MarcieHealthCheck;
import com.zendesk.marcie.http.GreeterController;
import com.zendesk.marcie.http.MarcieConfigurator;
import com.zendesk.marcie.http.TicketController;
import com.zendesk.starter.weld.AbstractWeldModule;
import io.vertx.core.Vertx;
import jakarta.enterprise.inject.Produces;

@IncludeBeans(MarcieHealthCheck.class)
public class MarcieHttpModule extends AbstractWeldModule {

  @Override
  protected void configure(StarterExtensionBuilder<? extends StarterExtension> builder) {
    builder.add(DataSupport.class).addBean(GreeterController.class)
        .addBean(TicketController.class).addBean(MarcieConfigurator.class);
  }

  @Produces
  public AsyncHealthCheck exampleCheck(Vertx vertx) {
    return new MarcieHealthCheck(vertx);
  }
}
