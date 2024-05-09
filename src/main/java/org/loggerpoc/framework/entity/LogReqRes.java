package org.loggerpoc.loggepoc.framework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Entity
@Table(name = "LOG_REQ_RES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogReqRes {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private long id;

  @Column(name = "TRANSACTION_ID")
  private String transactionId;

  @Column(name = "ENDPOINT")
  private String uri;

  @Column(name = "HTTP_METHOD")
  private String httpMethod;

  @Column(name = "REQUEST")
  private String request;

  @Column(name = "RESPONSE")
  private String response;

  @Column(name = "STATUS")
  private int status;

  @CreationTimestamp
  @Column(name = "TIMESTAMP_REQ_RES")
  private String createdon;

  @Override
  public String toString() {

    return "{" +
        "id='" + id + '\'' +
        ", transactionId='" + transactionId + '\'' +
        ", uri='" + uri + '\'' +
        ", httpMethod='" + httpMethod + '\'' +
        ", request='" + request + '\'' +
        ", response='" + response + '\'' +
        ", status='" + status + '\'' +
        ", timestamp='" + createdon + '\'' +
        "}";
  }


}
