package com.farmaciasperuanas.digital.user.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Main class for running Spring Boot framework.<br/>
 * <b>Class</b>: Application<br/>
 * <b>Copyright</b>: 2022 Farmacias Peruanas.<br/>
 * <b>Company</b>: Farmacias Peruanas.<br/>
 *

 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>carlos lopez</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 * <li>02/06/2022 ClientApplication Class.</li>
 * </ul>
 * @version 1.0
 */

@SpringBootApplication
//@EntityScan("com.farmaciasperuanas.digital.user.client.entity")
public class ClientApplication {

  /**
   * Main method.
   */
  public static void main(String[] args) {
    new SpringApplication(ClientApplication.class).run(args);
  }
}
