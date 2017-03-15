package cn.cs.scu.scalautils

import cn.cs.scu.conf.ConfigurationManager
import cn.cs.scu.constants.Constants
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 初始化spark环境工具对象
  *
  * Created by Wanghan on 2017/3/15.
  * Copyright © Wanghan SCU. All Rights Reserved
  */
object InitUnits {

    /**
      * 初始化spark、sql环境
      *
      * @return
      */
    def initSparkContext(): (SparkContext, SQLContext ,StreamingContext) = {
        // spark配置文件
        val conf = getSparkConf
        // spark上下文环境
        val sc = new SparkContext(conf)
        // SQL上下文环境
        val sqlContext = getSQLContext(sc)
        //streamingContext上下文环境
        val streamingContext = getStreamingContext(sc)
        // 设置Log等级
        Logger.getRootLogger.setLevel(Level.OFF)

        (sc, sqlContext, streamingContext)
    }

    /**
      * 加载spark配置，如果在本地使用单核，如果在集群，则提交作业时候指定
      *
      * @return
      */
    def getSparkConf: SparkConf = {
        val local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL)
        if (local)
            new SparkConf().setAppName(Constants.SPARK_APP_NAME_SESSION).setMaster(Constants.SPARK_MASTER)
        else
            new SparkConf().setAppName(Constants.SPARK_APP_NAME_SESSION)
    }

    /**
      * 加载SQL环境，如果在本地生成sql环境，如果在集群使用hive环境
      *
      * @param sc
      * @return
      */
    def getSQLContext(sc: SparkContext): SQLContext = {
        val local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL)
        if (local)
            new SQLContext(sc)
        else
            new HiveContext(sc)
    }

    /**
      * 加载streaming环境
      *
      * @param sparkContext
      * @return
      */
    def getStreamingContext(sparkContext: SparkContext): StreamingContext = {
        val local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL)
        if (local) {

        }

        new StreamingContext(sparkContext, Seconds(ConfigurationManager.getLong(Constants.SPARK_STREAMING_COLLECT_TIME)))
    }
}
