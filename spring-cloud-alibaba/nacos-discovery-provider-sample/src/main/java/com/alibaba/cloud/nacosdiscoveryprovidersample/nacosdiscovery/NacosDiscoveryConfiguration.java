/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.nacosdiscoveryprovidersample.nacosdiscovery;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 * Aliyun Java Initializr 默认不会自动激活 Nacos Discovery 服务注册与发现，需要在引导类（main 方法所在类。
 * 或者是@Configuration标注类）标注 Spring Cloud 服务注册与发现标准注解@EnableDiscoveryClient，代码如下所示： NacosDiscoveryConfiguration 文件
 */
@EnableDiscoveryClient
@Configuration
public class NacosDiscoveryConfiguration {
}
