package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.{GuiceModule, OpenligaDbSOAPService, OpenligaDbService}

trait OpenligaDbTrait {

  val openligaDbService: OpenligaDbService = GuiceModule.injector.getInstance(classOf[OpenligaDbService])
  val openligaDbSOAPService: OpenligaDbSOAPService = GuiceModule.injector.getInstance(classOf[OpenligaDbSOAPService])

}
