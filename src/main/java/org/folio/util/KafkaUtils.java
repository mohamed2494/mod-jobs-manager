package org.folio.util;

import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static org.apache.commons.lang3.StringUtils.firstNonBlank;

public class KafkaUtils {

  public static String getTenantTopicName(String name, String tenantId) {
    return String.format("%s.%s.%s", getFolioEnvName(), tenantId, name);
  }

  public static String getFolioEnvName() {
    return firstNonBlank(getenv("ENV"), getProperty("env"), "folio");
  }
}
