require "java"
require "jruby"
require "protobuf_java_helpers/version"

module ProtobufJavaHelpers
  # Your code goes here...
end

require "jars/protobuf_java_helpers.jar"
com.abrandoned.protobuf_java_helpers.VarinterService.new.basicLoad(::JRuby.runtime)
