
name := "scalajs-angulate-uirouter"

val commonSettings = Seq(
  organization := "biz.enef",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.6",
  scalacOptions ++= Seq("-deprecation","-feature","-Xlint"
//    , "-Xmacro-settings:de.surfice.smacrotools.debug"
    ),
  resolvers += Resolver.sonatypeRepo("snapshots")
)

val angulateDebugFlags = Seq(
  //"runtimeLogging",
  //"ModuleMacros.debug",
  //"ControllerMacros.debug"
  //"DirectiveMacros.debug"
  //"ServiceMacros.debug"
  //"HttpPromiseMacros.debug"
).map( f => s"-Xmacro-settings:biz.enef.angular.$f" )

lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(publishingSettings: _*).
  //settings(sonatypeSettings: _*).
  settings(
    name := "scalajs-angulate-uirouter",
    normalizedName := "scalajs-angulate-uirouter",
    scalacOptions ++= angulateDebugFlags,
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full),
    libraryDependencies ++= Seq(
      "biz.enef" %%% "scalajs-angulate" % "0.2.3-SNAPSHOT",
      "de.surfice" %%% "smacrotools-sjs" % "0.1-SNAPSHOT" % "provided"
    )
  )

lazy val tests = project.
  dependsOn(root).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    publish := {},
    publishLocal := {},
    scalacOptions ++= angulateDebugFlags,
    scalaJSStage := FastOptStage,
    //jsEnv in Test := PhantomJSEnv("phantomjs" , Seq("--remote-debugger-port=9000","--remote-debugger-autorun=true")).value,
    //jsEnv in Test := PhantomJSEnv("phantomjs" , Seq("--debug=true")).value,
    jsEnv in Test := PhantomJSEnv().value,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
    jsDependencies += RuntimeDOM,
    jsDependencies += "org.webjars" % "angularjs" % "1.3.8" / "angular.min.js" % "test",
    jsDependencies += ("org.webjars" % "angularjs" % "1.3.8" / "angular-locale_en.js"  dependsOn "angular.min.js") % "test",
    //jsDependencies += "org.webjars" % "angularjs" % "1.3.8" / "angular-mocks.js" dependsOn "angular.min.js",
    jsDependencies += ("org.webjars" % "angular-ui-router" % "0.2.13" / "angular-ui-router.min.js" dependsOn "angular.min.js") % "test"
  )

lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <url>https://github.com/jokade/scalajs-angulate-uirouter</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>http://opensource.org/licenses/Apache-2.0</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jokade/scalajs-angulate-uirouter</url>
      <connection>scm:git:git@github.com:jokade/scalajs-angulate-uirouter.git</connection>
    </scm>
    <developers>
      <developer>
        <id>ludovicc</id>
        <name>Ludovic Claude</name>
        <email>ludovic.claude@laposte.net</email>
      </developer>
    </developers>
  )
)

