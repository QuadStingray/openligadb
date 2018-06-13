package com.quadstingray.openligadb.services

import com.google.inject.{AbstractModule, Guice, Injector}
import com.quadstingray.openligadb.services.cache.CacheService

private[openligadb] class GuiceModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CacheService]).asEagerSingleton()
    bind(classOf[HttpService]).to(classOf[AkkaHttpService]).asEagerSingleton()
    bind(classOf[OpenligaDbService]).to(classOf[OpenligaDbServiceImpementation])
    bind(classOf[OpenligaDbSOAPService]).to(classOf[OpenligaDbSOAPServiceImplementation])
  }
}

object GuiceModule {
  val injector: Injector = Guice.createInjector(new GuiceModule)
}
