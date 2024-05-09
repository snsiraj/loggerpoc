package org.loggerpoc.framework.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "LOG_DTL_REQ_RES")

public class LoggerMessage {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "CLASS_NAME")
    private String className;
    @Column(name = "TRANSACTION_ID")
    private String transactionId;
    @Column(name = "METHOD_NAME")
    private String methodName;
    @Column(name = "METHOD_ARGS")
    private String methodArgs;
    @Column(name = "EXECUTION_TIME_IN_MILLIS")
    private Long executionTimeInMillis;
    @Column(name = "RESULT")
    private String result;

    @Override
    public String toString() {
        try {
            return "{" +
                    "transactionId='" + transactionId + '\'' +
                    ",className='" + className + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", methodArgs='" + methodArgs + '\'' +
                    ", executionTimeInMillis=" + executionTimeInMillis +
                    ", result=" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result) +
                    '}';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
