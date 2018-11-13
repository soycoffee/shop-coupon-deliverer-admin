name := """shop-coupon-deliverer-admin"""
organization := "com.example"

version := "1.0-SNAPSHOT"

packageName in Universal := name.value
packageName in Universal := {
  new java.text.SimpleDateFormat("yyyyMMddHHmmss") format new java.util.Date()
}

mappings in Universal += file("Procfile") -> "Procfile"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  guice,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
