package com.abrandoned.protobuf_java_helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;;
import java.io.DataOutput;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.lang.Math;
import java.math.BigInteger;
import org.jcodings.specific.ASCIIEncoding;
import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyBignum;
import org.jruby.RubyComplex;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyInteger;
import org.jruby.RubyNumeric;
import org.jruby.RubyString;
import org.jruby.RubyIO;
import org.jruby.ext.stringio.StringIO;
import org.jruby.util.IOInputStream;
import org.jruby.util.ByteList;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class Varinter {
  @JRubyMethod
  public static IRubyObject to_varint( ThreadContext context, IRubyObject self, IRubyObject recv ) throws IOException {
    if (recv instanceof RubyBignum || !(recv instanceof RubyInteger || recv instanceof RubyFixnum || recv instanceof RubyNumeric || recv instanceof RubyFloat)) {
      return context.nil;
    }

    long value = ((RubyNumeric) recv).getLongValue();
    org.jruby.Ruby runtime = context.getRuntime();
    ByteArrayOutputStream backing = new ByteArrayOutputStream();
    DataOutput data_output = new DataOutputStream(backing);
    com.abrandoned.protobuf_java_helpers.Varint.writeUnsignedVarLong2(value, data_output);

    RubyString bit_string = runtime.newString(new ByteList(backing.toByteArray(), true));
    bit_string.force_encoding(context, runtime.getEncodingService().getEncoding(org.jcodings.specific.ASCIIEncoding.INSTANCE));
    return bit_string;
  }

  @JRubyMethod
  public static IRubyObject decode_varint( ThreadContext context, IRubyObject self, IRubyObject recv ) throws IOException {
    if (!(recv instanceof RubyIO || recv instanceof StringIO )) {
      return context.nil;
    }

    if (recv instanceof StringIO) {
      StringIO src = (StringIO) recv;

      long tmp;
      if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
        return context.getRuntime().newFixnum(tmp);
      }

      long result = tmp & 0x7f;
      if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
        result |= tmp << 7;
      } else {
        result |= (tmp & 0x7f) << 7;
        if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
          result |= tmp << 14;
        } else {
          result |= (tmp & 0x7f) << 14;
          if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
            result |= tmp << 21;
          } else {
            result |= (tmp & 0x7f) << 21;
            if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
              result |= tmp << 28;
            } else {
              result |= (tmp & 0x7f) << 28;
              if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                result |= tmp << 35;
              } else {
                result |= (tmp & 0x7f) << 35;
                if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                  result |= tmp << 42;
                } else {
                  result |= (tmp & 0x7f) << 42;
                  if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                    result |= tmp << 49;
                  } else {
                    result |= (tmp & 0x7f) << 49;
                    if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                      result |= tmp << 56;
                    } else {
                      return context.nil;
                    }
                  }
                }
              }
            }
          }
        }
      }

      return context.getRuntime().newFixnum(result);
    }

    if (recv instanceof RubyIO) {
      RubyIO src = (RubyIO) recv;

      long tmp;
      if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
        return context.getRuntime().newFixnum(tmp);
      }

      long result = tmp & 0x7f;
      if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
        result |= tmp << 7;
      } else {
        result |= (tmp & 0x7f) << 7;
        if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
          result |= tmp << 14;
        } else {
          result |= (tmp & 0x7f) << 14;
          if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
            result |= tmp << 21;
          } else {
            result |= (tmp & 0x7f) << 21;
            if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
              result |= tmp << 28;
            } else {
              result |= (tmp & 0x7f) << 28;
              if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                result |= tmp << 35;
              } else {
                result |= (tmp & 0x7f) << 35;
                if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                  result |= tmp << 42;
                } else {
                  result |= (tmp & 0x7f) << 42;
                  if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                    result |= tmp << 49;
                  } else {
                    result |= (tmp & 0x7f) << 49;
                    if ((tmp = ((RubyFixnum)(src.getbyte(context))).getLongValue()) >= 0) {
                      result |= tmp << 56;
                    } else {
                      return context.nil;
                    }
                  }
                }
              }
            }
          }
        }
      }

      return context.getRuntime().newFixnum(result);
    }

    return context.nil;
  }
}
