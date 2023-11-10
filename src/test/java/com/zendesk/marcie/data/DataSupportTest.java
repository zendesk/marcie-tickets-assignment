package com.zendesk.marcie.data;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;

class DataSupportTest {

  @WeldSetup
  public WeldInitiator weld = WeldInitiator.of(DataSupport.class);

  @Test
  void testConfigure() {
    Weld weldMock = mock(Weld.class);
    DataSupport dataSupport = new DataSupport();
    dataSupport.configure(weldMock);
    verify(weldMock).addBeanClasses(CommentServiceImpl.class, TicketServiceImpl.class);
  }

}

