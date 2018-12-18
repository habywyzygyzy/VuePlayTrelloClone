lazy val root = Project("plugins", file(".")).aggregate(sbtVuefy).dependsOn(sbtVuefy)

lazy val sbtVuefy = RootProject(file("./..").getCanonicalFile.toURI)

resolvers += Classpaths.sbtPluginReleases
resolvers += Resolver.bintrayRepo("givers", "maven")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.20")
addSbtPlugin("givers.vuefy" % "sbt-vuefy" % "2.0.0")