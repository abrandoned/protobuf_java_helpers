package com.abrandoned.protobuf_java_helpers;

import org.jruby.Ruby;
import org.jruby.RubyModule;
import org.jruby.runtime.load.BasicLibraryService;
import com.abrandoned.protobuf_java_helpers.*;

import java.io.IOException;

public class VarinterService implements BasicLibraryService {
    @Override
    public boolean basicLoad(final Ruby runtime) throws IOException {
        RubyModule protobuf_java_helpers = runtime.getModule("ProtobufJavaHelpers");

        RubyModule varinter = protobuf_java_helpers.defineModuleUnder(Varinter.class.getSimpleName());
        varinter.defineAnnotatedMethods(Varinter.class);

        RubyModule uint64_protobuf = protobuf_java_helpers.defineModuleUnder(Uint64ProtobufField.class.getSimpleName());
        uint64_protobuf.defineAnnotatedMethods(Uint64ProtobufField.class);

        RubyModule uint32_protobuf = protobuf_java_helpers.defineModuleUnder(Uint32ProtobufField.class.getSimpleName());
        uint32_protobuf.defineAnnotatedMethods(Uint32ProtobufField.class);

        RubyModule sint64_protobuf = protobuf_java_helpers.defineModuleUnder(Sint64ProtobufField.class.getSimpleName());
        sint64_protobuf.defineAnnotatedMethods(Sint64ProtobufField.class);

        RubyModule sint32_protobuf = protobuf_java_helpers.defineModuleUnder(Sint32ProtobufField.class.getSimpleName());
        sint32_protobuf.defineAnnotatedMethods(Sint32ProtobufField.class);

        RubyModule int64_protobuf = protobuf_java_helpers.defineModuleUnder(Int64ProtobufField.class.getSimpleName());
        int64_protobuf.defineAnnotatedMethods(Int64ProtobufField.class);

        RubyModule int32_protobuf = protobuf_java_helpers.defineModuleUnder(Int32ProtobufField.class.getSimpleName());
        int32_protobuf.defineAnnotatedMethods(Int32ProtobufField.class);

        RubyModule varint_protobuf_field = protobuf_java_helpers.defineModuleUnder(VarintProtobufField.class.getSimpleName());
        varint_protobuf_field.defineAnnotatedMethods(VarintProtobufField.class);

        return true;
    }
}
