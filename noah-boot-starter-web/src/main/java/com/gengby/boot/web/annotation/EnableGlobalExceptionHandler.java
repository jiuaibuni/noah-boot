/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gengby.boot.web.annotation;

import org.springframework.context.annotation.Import;
import top.continew.starter.web.autoconfigure.exception.GlobalExceptionHandlerAutoConfiguration;

import java.lang.annotation.*;

/**
 * 全局异常、错误处理器启用注解
 *
 * @author Noah
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({GlobalExceptionHandlerAutoConfiguration.class})
public @interface EnableGlobalExceptionHandler {
}
