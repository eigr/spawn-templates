// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: actors/postalcode.proto
// Protobuf Java Version: 4.27.2

package domain.actors;

/**
 * Protobuf enum {@code domain.actors.PostalCodeStatus}
 */
public enum PostalCodeStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>UNKNOWN = 0;</code>
   */
  UNKNOWN(0),
  /**
   * <code>CREATED = 1;</code>
   */
  CREATED(1),
  /**
   * <code>NOT_FOUND = 2;</code>
   */
  NOT_FOUND(2),
  /**
   * <code>FOUND = 3;</code>
   */
  FOUND(3),
  UNRECOGNIZED(-1),
  ;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 27,
      /* patch= */ 2,
      /* suffix= */ "",
      PostalCodeStatus.class.getName());
  }
  /**
   * <code>UNKNOWN = 0;</code>
   */
  public static final int UNKNOWN_VALUE = 0;
  /**
   * <code>CREATED = 1;</code>
   */
  public static final int CREATED_VALUE = 1;
  /**
   * <code>NOT_FOUND = 2;</code>
   */
  public static final int NOT_FOUND_VALUE = 2;
  /**
   * <code>FOUND = 3;</code>
   */
  public static final int FOUND_VALUE = 3;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static PostalCodeStatus valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static PostalCodeStatus forNumber(int value) {
    switch (value) {
      case 0: return UNKNOWN;
      case 1: return CREATED;
      case 2: return NOT_FOUND;
      case 3: return FOUND;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<PostalCodeStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      PostalCodeStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<PostalCodeStatus>() {
          public PostalCodeStatus findValueByNumber(int number) {
            return PostalCodeStatus.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return domain.actors.Postalcode.getDescriptor().getEnumTypes().get(0);
  }

  private static final PostalCodeStatus[] VALUES = values();

  public static PostalCodeStatus valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private PostalCodeStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:domain.actors.PostalCodeStatus)
}

