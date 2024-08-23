// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: actors/postalcode.proto
// Protobuf Java Version: 4.27.2

package domain.actors;

public interface PostalCodeStateOrBuilder extends
    // @@protoc_insertion_point(interface_extends:domain.actors.PostalCodeState)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string code = 1;</code>
   * @return The code.
   */
  java.lang.String getCode();
  /**
   * <code>string code = 1;</code>
   * @return The bytes for code.
   */
  com.google.protobuf.ByteString
      getCodeBytes();

  /**
   * <code>.domain.actors.PostalCodeStatus status = 2;</code>
   * @return The enum numeric value on the wire for status.
   */
  int getStatusValue();
  /**
   * <code>.domain.actors.PostalCodeStatus status = 2;</code>
   * @return The status.
   */
  domain.actors.PostalCodeStatus getStatus();

  /**
   * <code>string country = 3;</code>
   * @return The country.
   */
  java.lang.String getCountry();
  /**
   * <code>string country = 3;</code>
   * @return The bytes for country.
   */
  com.google.protobuf.ByteString
      getCountryBytes();

  /**
   * <code>string street = 4;</code>
   * @return The street.
   */
  java.lang.String getStreet();
  /**
   * <code>string street = 4;</code>
   * @return The bytes for street.
   */
  com.google.protobuf.ByteString
      getStreetBytes();

  /**
   * <code>string state = 5;</code>
   * @return The state.
   */
  java.lang.String getState();
  /**
   * <code>string state = 5;</code>
   * @return The bytes for state.
   */
  com.google.protobuf.ByteString
      getStateBytes();

  /**
   * <code>string city = 6;</code>
   * @return The city.
   */
  java.lang.String getCity();
  /**
   * <code>string city = 6;</code>
   * @return The bytes for city.
   */
  com.google.protobuf.ByteString
      getCityBytes();
}
