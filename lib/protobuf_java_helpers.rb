require "java"
require "jruby"
require "protobuf_java_helpers/version"

module ProtobufJavaHelpers
end

require "jars/protobuf_java_helpers.jar"
com.abrandoned.protobuf_java_helpers.VarinterService.new.basicLoad(::JRuby.runtime)

module ProtobufJavaHelpers
  module EncodeDecode
    include ::ProtobufJavaHelpers::Varinter

    def pure_encode(value)
      bytes = []
      until value < 128
        bytes << (0x80 | (value & 0x7f))
        value >>= 7
      end
      (bytes << value).pack('C*')
    end

    def encode(value)
      to_varint(value) || pure_encode(value)
    end

    def decode(stream)
      value = index = 0
      begin
        byte = stream.readbyte
        value |= (byte & 0x7f) << (7 * index)
        index += 1
      end while (byte & 0x80).nonzero?
      value
    end
  end
end
