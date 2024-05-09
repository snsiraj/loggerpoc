package org.loggerpoc.framework.dao;


import org.loggerpoc.framework.entity.LoggerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDetailDao extends JpaRepository<LoggerMessage, Long> {

}
