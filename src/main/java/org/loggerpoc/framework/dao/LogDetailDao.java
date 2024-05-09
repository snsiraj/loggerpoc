package org.loggerpoc.loggepoc.framework.dao;


import org.loggerpoc.loggepoc.framework.entity.LoggerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDetailDao extends JpaRepository<LoggerMessage, Long> {

}
