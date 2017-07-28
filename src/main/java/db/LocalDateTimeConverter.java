package db;

import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

import java.time.LocalDateTime;

public class LocalDateTimeConverter implements Converter<LocalDateTime> {
  @Override
  public LocalDateTime convert(Object val) throws ConverterException {
    if (val == null)
      return null;
    if (val instanceof java.sql.Timestamp) {
      return ((java.sql.Timestamp)val).toLocalDateTime();
    }
    throw new ConverterException("convert to java.time.LocalDateTime");
  }

  @Override
  public Object toDatabaseParam(LocalDateTime val) {
    if (val == null)
      return null;
    return java.sql.Timestamp.valueOf(val);
  }
}
