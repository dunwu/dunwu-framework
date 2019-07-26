//package io.github.dunwu.data.handler;
//
//import io.github.dunwu.util.base.ByteUtil;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.io.ByteArrayInputStream;
//import java.sql.*;
//
//@MappedJdbcTypes(JdbcType.BLOB)
//@MappedTypes({Byte[].class})
//public class BlobAndStringTypeHandler extends BaseTypeHandler<Byte[]> {
//
//    private static final String DEFAULT_CHARSET = "UTF-8"; //感觉没屌用
//
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Byte[] bytes, JdbcType jdbcType)
//        throws SQLException {
//        ByteArrayInputStream bis = new ByteArrayInputStream(ByteUtil.convertToPrimitiveArray(bytes));
//        preparedStatement.setBinaryStream(i, bis, bytes.length);
//    }
//
//    @Override
//    public Byte[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        Blob blob = rs.getBlob(columnName);
//        return bolbTransToBytes(blob);
//    }
//
//    @Override
//    public Byte[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        Blob blob = rs.getBlob(columnIndex);
//        return bolbTransToBytes(blob);
//    }
//
//    @Override
//    public Byte[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        Blob blob = cs.getBlob(columnIndex);
//        return bolbTransToBytes(blob);
//    }
//
//    private Byte[] bolbTransToBytes(Blob blob) throws SQLException {
//        byte[] bytes = null;
//        if (null != blob) {
//            bytes = blob.getBytes(1, (int) blob.length());
//        }
//        return ByteUtil.convertToObjectArray(bytes);
//    }
//}
