import com.typesafe.sbt.packager.docker.DockerChmodType
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

name := """firstPlayApp"""
organization := "com.aphiwe"

version := "1.0.37-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
                  guice,
                  jdbc,
                  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
                  "org.mockito" % "mockito-all" % "1.10.19" % Test,
                  "org.playframework.anorm" %% "anorm" % "2.6.7",
                  "mysql" % "mysql-connector-java" % "8.0.28"
                )


dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
Docker / packageName := "first-play-app"
Docker / version := version.value
Docker / daemonUserUid := None
Docker / daemonUser := "daemon"
dockerExposedPorts := Seq(9000)
dockerBaseImage := "anapsix/alpine-java:8_jdk_unlimited"
dockerRepository := None
dockerUpdateLatest := true


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.aphiwe.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.aphiwe.binders._"
