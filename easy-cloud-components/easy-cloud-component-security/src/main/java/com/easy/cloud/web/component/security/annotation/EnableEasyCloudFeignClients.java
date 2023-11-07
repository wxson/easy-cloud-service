package com.easy.cloud.web.component.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * EnableEasyCloudFeignClients注解类
 *
 * @author GR
 * @date 2021-3-26 16:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableEasyCloudFeignClients {

  /**
   * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation
   * declarations e.g.: {@code @ComponentScan("org.my.pkg")} instead of {@code
   * @ComponentScan(basePackages="org.my.pkg")}.
   *
   * @return the array of 'basePackages'.
   */
  String[] value() default {};

  /**
   * Base packages to scan for annotated components.
   * <p>
   * {@link #value()} is an alias for (and mutually exclusive with) this attribute.
   * <p>
   * Use {@link #basePackageClasses()} for a type-safe alternative to String-based package names.
   *
   * @return the array of 'basePackages'.
   */
  String[] basePackages() default {"com.easy.cloud.web"};

  /**
   * Type-safe alternative to {@link #basePackages()} for specifying the packages to scan for
   * annotated components. The package of each class specified will be scanned.
   * <p>
   * Consider creating a special no-op marker class or interface in each package that serves no
   * purpose other than being referenced by this attribute.
   *
   * @return the array of 'basePackageClasses'.
   */
  Class<?>[] basePackageClasses() default {};

  /**
   * A custom <code>@Configuration</code> for all feign clients. Can contain override
   * <code>@Bean</code> definition for the pieces that make up the client, for instance
   * {@link feign.codec.Decoder}, {@link feign.codec.Encoder}, {@link feign.Contract}.
   */
  Class<?>[] defaultConfiguration() default {};

  /**
   * List of classes annotated with @FeignClient. If not empty, disables classpath scanning.
   *
   * @return
   */
  Class<?>[] clients() default {};
}
