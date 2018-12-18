name := """VuePlayTrelloClone"""
organization := "none"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb, SbtVuefy, PlayEbean, PlayEnhancer)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  guice,
  evolutions,
  cache
)

libraryDependencies += guice

Assets / VueKeys.vuefy / VueKeys.prodCommands := Set("stage")
Assets / VueKeys.vuefy / VueKeys.webpackBinary := {
  // Detect windows
  if (sys.props.getOrElse("os.name", "").toLowerCase.contains("win")) {
    (new File(".") / "node_modules" / ".bin" / "webpack.cmd").getAbsolutePath
  } else {
    (new File(".") / "node_modules" / ".bin" / "webpack").getAbsolutePath
  }
}
Assets / VueKeys.vuefy / VueKeys.webpackConfig := (new File(".") / "webpack.config.js").getAbsolutePath

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.194"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test


// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

libraryDependencies += "de.svenkubiak" % "jBCrypt" % "0.4"
libraryDependencies += "io.ebean" % "ebean" % "4.0.1"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
// Options: InjectedRoutesGenerator / StaticRoutesGenerator
// Used legacy static style - injected is not yet supported in IntelliJ
routesGenerator := StaticRoutesGenerator

// fix sbt resolve warn when import IDEA project:
// Multiple dependencies with the same organization/name but different versions
// To avoid conflict, pick one version
dependencyOverrides ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler"  % scalaVersion.value,

  "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",

  "org.apache.httpcomponents" % "httpclient" % "4.5.6",
  "org.apache.httpcomponents" % "httpcore" % "4.4.10",

  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.akka" % "akka-actor_2.12" % "2.5.18",

  "commons-logging" % "commons-logging" % "1.2",
  "com.google.guava" % "guava" % "27.0.1-jre",
  "junit" % "junit" % "4.12"
)

//update selenium, as bundled	2.44.0 was released 23-Oct-2014
dependencyOverrides ++= Seq(
  "org.seleniumhq.selenium" % "selenium-api" % "2.48.2",
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.141.59",
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.48.2",
  "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.52.0",
  "org.seleniumhq.selenium" % "selenium-ie-driver" % "2.48.2",
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
  "org.seleniumhq.selenium" % "selenium-remote-driver" % "2.48.2",
  "org.seleniumhq.selenium" % "selenium-safari-driver" % "2.48.2",
  "org.seleniumhq.selenium" % "selenium-support" % "2.48.2"
)

