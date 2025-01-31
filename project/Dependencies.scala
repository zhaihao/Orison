/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

import sbt.*

/** Dependencies
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2019-02-18 13:29
  */
object Dependencies extends AutoPlugin {
  override def requires = empty
  override def trigger  = allRequirements

  object autoImport {
    lazy val scalatest       = "org.scalatest"     %% "scalatest-freespec"     % "3.2.19" % Provided
    lazy val scalatest_must  = "org.scalatest"     %% "scalatest-mustmatchers" % "3.2.19" % Provided
    lazy val play_json       = "org.playframework" %% "play-json"              % "3.0.4"  % Provided
    lazy val os_lib          = "com.lihaoyi"       %% "os-lib"                 % "0.11.3" % Provided
    lazy val fastparse       = "com.lihaoyi"       %% "fastparse"              % "2.3.3"  % Provided
    lazy val json4s          = "org.json4s"        %% "json4s-jackson"         % "4.0.7"  % Provided
    lazy val typesafe_config = "com.typesafe"       % "config"                 % "1.4.3"  % Provided
    lazy val squants         = "org.typelevel"     %% "squants"                % "1.8.3"  % Provided
    lazy val argon2          = "de.mkammerer"       % "argon2-jvm"             % "2.11"   % Provided
    lazy val jbcrypt         = "org.mindrot"        % "jbcrypt"                % "0.4"    % Provided
    lazy val oshi            = "com.github.oshi"    % "oshi-core-java11"       % "6.6.5"  % Provided
    lazy val eval            = "com.eed3si9n.eval" %% "eval"                   % "0.3.0"  % Provided

    lazy val JAVA_MAIL = Seq(
      "javax.mail"   % "javax.mail-api" % "1.6.2" % Provided,
      "com.sun.mail" % "javax.mail"     % "1.6.2" % Provided
    )

    lazy val SLICK = Seq(
      "com.typesafe.slick"  %% "slick"              % "3.5.2"  % Provided,
      "com.github.tminglei" %% "slick-pg"           % "0.22.2" % Provided,
      "com.github.tminglei" %% "slick-pg_play-json" % "0.22.2" % Provided,
      "com.github.tminglei" %% "slick-pg_jts_lt"    % "0.22.2" % Provided
    )

    lazy val LOG = Seq(
      "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.5"  % Provided,
      "ch.qos.logback"              % "logback-classic" % "1.5.12" % Provided
    )

    val excludes = Seq()

    val overrides = Seq()
  }

}
