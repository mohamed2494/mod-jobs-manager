package org.folio.util;

import org.folio.spring.DefaultFolioExecutionContext;
import org.folio.spring.FolioExecutionContext;
import org.folio.spring.FolioModuleMetadata;
import org.folio.spring.integration.XOkapiHeaders;
import org.folio.spring.scope.FolioExecutionContextSetter;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class TenantContextUtils {

  private static class ExecutionContextBuilder {
    private String tenantId;
    private String url;
    private String token;
    private Map<String, Object> headers;
    private FolioModuleMetadata moduleMetadata;

    public ExecutionContextBuilder tenantId(String tenantId) {
      this.tenantId = tenantId;
      return this;
    }

    public ExecutionContextBuilder url(String url) {
      this.url = url;
      return this;
    }

    public ExecutionContextBuilder token(String token) {
      this.token = token;
      return this;
    }

    public ExecutionContextBuilder headers(Map<String, Object> headers) {
      this.headers = headers;
      return this;
    }

    public ExecutionContextBuilder moduleMetadata(FolioModuleMetadata moduleMetadata) {
      this.moduleMetadata = moduleMetadata;
      return this;
    }

    public FolioExecutionContext build() {
      Map<String, Collection<String>> headerMap = new HashMap<>();
      headerMap.put(XOkapiHeaders.TENANT, getHeaderValue(XOkapiHeaders.TENANT, tenantId));
      headerMap.put(XOkapiHeaders.URL, getHeaderValue(XOkapiHeaders.URL, url));
      headerMap.put(XOkapiHeaders.TOKEN, getHeaderValue(XOkapiHeaders.TOKEN, token));
      headerMap.put(XOkapiHeaders.USER_ID, getHeaderValue(XOkapiHeaders.USER_ID, null));

      return new DefaultFolioExecutionContext(moduleMetadata, headerMap);
    }

    private List<String> getHeaderValue(String headerName, String value) {
      Object headerValue = headers.get(headerName);
      String headerValueString = (headerValue instanceof byte[])
        ? new String((byte[]) headerValue, StandardCharsets.UTF_8)
        : value;

      return (headerValueString == null) ? Collections.emptyList() : Collections.singletonList(headerValueString);
    }
  }

  public static FolioExecutionContext getFolioExecutionContextFromTenant(String tenantId,
                                                                         FolioModuleMetadata moduleMetadata) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("tenant_id", tenantId);

    return new ExecutionContextBuilder()
      .tenantId(tenantId)
      .headers(headers)
      .moduleMetadata(moduleMetadata)
      .build();
  }

  public static FolioExecutionContext getFolioExecutionContextFromEvent(Map<String, Object> headers,
                                                                        FolioModuleMetadata moduleMetadata) {
    return new ExecutionContextBuilder()
      .headers(headers)
      .moduleMetadata(moduleMetadata)
      .build();
  }

  public static void runInFolioContext(FolioExecutionContext context, Runnable runnable) {
    try (var fec = new FolioExecutionContextSetter(context)) {
      runnable.run();
    }
  }
}
