// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: domain/actors/postalcode.proto

package io.eigr.spawn.java.domain.actors;

public final class PostalCodeProto {
  private PostalCodeProto() {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;
  static {
    java.lang.String[] descriptorData = {
        "\n\036domain/actors/postalcode.proto\022\rdomain" +
            ".actors\032\023domain/common.proto2J\n\nPostalCo" +
            "de\022<\n\021GetPostalCodeData\022\022.domain.GetRequ" +
            "est\032\023.domain.GetResponseB3\n io.eigr.spaw" +
            "n.java.domain.actorsB\017PostalCodeProtob\006p" +
            "roto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
        .internalBuildGeneratedFileFrom(descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
                io.eigr.spawn.java.domain.DomainProto.getDescriptor(),
            });
    io.eigr.spawn.java.domain.DomainProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
